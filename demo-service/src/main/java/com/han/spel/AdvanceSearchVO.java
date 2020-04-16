package com.han.spel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 〈功能概述〉<br>
 *
 * @className: AdvanceSearchVO
 * @author: hanyf
 * @date: 2019-10-21 14:44
 */
@Data
@Accessors
public class AdvanceSearchVO implements Serializable {
    private static final long serialVersionUID = 4009711549845549041L;

    /**
     * 商品ID集合
     */
    private List<AdvanceSearchGoodsVO> goodsInfoList;
    /**
     * 商品查询操作符ID
     */
    private String goodsOptCode;

    public String getGoodsOptCode() {
        return goodsOptCode == null ? null : goodsOptCode.toLowerCase();
    }
}
