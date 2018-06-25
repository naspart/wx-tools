package com.rolbel.mp.bean.member_card;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMemberCardUserInfo implements Serializable {
    private static final long serialVersionUID = -4259196162619282129L;

    private NameValues[] commonFieldList;

    private NameValues[] customFieldList;
}
