package com.naspat.mp.bean.member_card;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMemberCardUserInfo implements Serializable {
    private static final long serialVersionUID = 8981571843174575750L;

    private NameValues[] commonFieldList;

    private NameValues[] customFieldList;
}
