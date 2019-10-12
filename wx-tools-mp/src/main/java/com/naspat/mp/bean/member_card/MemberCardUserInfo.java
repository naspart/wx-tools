package com.naspat.mp.bean.member_card;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberCardUserInfo implements Serializable {
    private static final long serialVersionUID = -666935105696457353L;

    private NameValues[] commonFieldList;

    private NameValues[] customFieldList;
}
