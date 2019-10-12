package com.naspat.open.bean.auth;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WxOpenMiniProgramInfo implements Serializable {
    private static final long serialVersionUID = 233423490687097006L;

    private Map<String, List<String>> network;
    private List<Pair<String, String>> categories;
    private Integer visitStatus;
}
