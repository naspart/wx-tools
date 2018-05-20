package com.rolbel.miniapp.message;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.rolbel.common.api.WxErrorExceptionHandler;
import com.rolbel.common.api.WxMessageDuplicateChecker;
import com.rolbel.common.api.WxMessageInMemoryDuplicateChecker;
import com.rolbel.common.session.InternalSession;
import com.rolbel.common.session.InternalSessionManager;
import com.rolbel.common.session.StandardSessionManager;
import com.rolbel.common.session.WxSessionManager;
import com.rolbel.common.util.LogExceptionHandler;
import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.bean.WxMaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WxMaMessageRouter {
    private static final int DEFAULT_THREAD_POOL_SIZE = 100;
    private final Logger log = LoggerFactory.getLogger(WxMaMessageRouter.class);
    private final List<WxMaMessageRouterRule> rules = new ArrayList<>();

    private final WxMaService wxMaService;

    private ExecutorService executorService;

    private WxMessageDuplicateChecker messageDuplicateChecker;

    private WxSessionManager sessionManager;

    private WxErrorExceptionHandler exceptionHandler;

    public WxMaMessageRouter(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("WxMaMessageRouter-pool-%d").build();
        this.executorService = new ThreadPoolExecutor(DEFAULT_THREAD_POOL_SIZE, DEFAULT_THREAD_POOL_SIZE,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
        this.sessionManager = new StandardSessionManager();
        this.exceptionHandler = new LogExceptionHandler();
    }

    /**
     * <pre>
     * 设置自定义的 {@link ExecutorService}
     * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
     * </pre>
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * <pre>
     * 设置自定义的 {@link com.rolbel.common.api.WxMessageDuplicateChecker}
     * 如果不调用该方法，默认使用 {@link com.rolbel.common.api.WxMessageInMemoryDuplicateChecker}
     * </pre>
     */
    public void setMessageDuplicateChecker(WxMessageDuplicateChecker messageDuplicateChecker) {
        this.messageDuplicateChecker = messageDuplicateChecker;
    }

    /**
     * <pre>
     * 设置自定义的{@link com.rolbel.common.session.WxSessionManager}
     * 如果不调用该方法，默认使用 {@link com.rolbel.common.session.StandardSessionManager}
     * </pre>
     */
    public void setSessionManager(WxSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * <pre>
     * 设置自定义的{@link com.rolbel.common.api.WxErrorExceptionHandler}
     * 如果不调用该方法，默认使用 {@link com.rolbel.common.util.LogExceptionHandler}
     * </pre>
     */
    public void setExceptionHandler(WxErrorExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    List<WxMaMessageRouterRule> getRules() {
        return this.rules;
    }

    /**
     * 开始一个新的Route规则
     */
    public WxMaMessageRouterRule rule() {
        return new WxMaMessageRouterRule(this);
    }

    /**
     * 处理微信消息
     */
    public void route(final WxMaMessage wxMessage, final Map<String, Object> context) {
        final List<WxMaMessageRouterRule> matchRules = new ArrayList<>();
        // 收集匹配的规则
        for (final WxMaMessageRouterRule rule : this.rules) {
            if (rule.test(wxMessage)) {
                matchRules.add(rule);
                if (!rule.isReEnter()) {
                    break;
                }
            }
        }

        if (matchRules.size() == 0) {
            return;
        }

        final List<Future<?>> futures = new ArrayList<>();
        for (final WxMaMessageRouterRule rule : matchRules) {
            // 返回最后一个非异步的rule的执行结果
            if (rule.isAsync()) {
                futures.add(
                        this.executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                rule.service(wxMessage, context, WxMaMessageRouter.this.wxMaService, WxMaMessageRouter.this.sessionManager, WxMaMessageRouter.this.exceptionHandler);
                            }
                        })
                );
            } else {
                rule.service(wxMessage, context, this.wxMaService, this.sessionManager, this.exceptionHandler);
                // 在同步操作结束，session访问结束
                this.log.debug("End session access: async=false, sessionId={}", wxMessage.getFromUser());
                sessionEndAccess(wxMessage);
            }
        }

        if (futures.size() > 0) {
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (Future<?> future : futures) {
                        try {
                            future.get();
                            WxMaMessageRouter.this.log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUser());
                            // 异步操作结束，session访问结束
                            sessionEndAccess(wxMessage);
                        } catch (InterruptedException | ExecutionException e) {
                            WxMaMessageRouter.this.log.error("Error happened when wait task finish", e);
                        }
                    }
                }
            });
        }

    }

    public void route(final WxMaMessage wxMessage) {
        this.route(wxMessage, new HashMap<String, Object>(2));
    }

    /**
     * 对session的访问结束
     */
    protected void sessionEndAccess(WxMaMessage wxMessage) {

        InternalSession session = ((InternalSessionManager) this.sessionManager).findSession(wxMessage.getFromUser());
        if (session != null) {
            session.endAccess();
        }

    }
}
