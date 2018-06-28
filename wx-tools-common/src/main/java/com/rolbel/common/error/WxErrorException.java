package com.rolbel.common.error;

public class WxErrorException extends Exception {
    private static final long serialVersionUID = 3715474237827781668L;

    private WxError error;

    public WxErrorException(WxError error) {
        super(error.toString());
        this.error = error;
    }

    public WxErrorException(WxError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public WxError getError() {
        return this.error;
    }
}
