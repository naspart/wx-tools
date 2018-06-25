package com.rolbel.mp.bean.member_card;

import lombok.Data;

import java.io.Serializable;

@Data
public class NameValues implements Serializable {
    private static final long serialVersionUID = -8529369702944594330L;

    private String name;

    private String value;

    private String[] valueList;
}
