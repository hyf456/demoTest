package com.han.spel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -4779400389477526534L;
    private Long id;
    private List<Long> idList;
    private String userCode;
    private String userName;
    private Integer age;
    private String exp = "";
    private List<User> children;
    private BigDecimal money;
    private AdvanceSearchVO goodsSearchAdvance;
}