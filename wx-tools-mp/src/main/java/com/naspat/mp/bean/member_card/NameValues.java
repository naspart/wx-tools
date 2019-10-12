package com.naspat.mp.bean.member_card;

import lombok.Data;

import java.io.Serializable;

@Data
public class NameValues implements Serializable {
    private static final long serialVersionUID = 4071738833820103342L;

    private String name;

    private String value;

    private String[] valueList;
}
