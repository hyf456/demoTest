package com.han.spel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 〈功能概述〉<br>
 *
 * @className: AdvanceSearchGoodsVO
 * @author: hanyf
 * @date: 2019-10-21 14:45
 */
@Data
@Accessors
public class AdvanceSearchGoodsVO implements Serializable {
    private static final long serialVersionUID = -8994478761718213140L;
    //商品ID
    private Long goodsId;
    //条件符号
    private String optCode;
    //商品数量
    private Integer goodsQty;
    //商品数量-起
    private Integer goodsQtyMin;
    //商品数量-止
    private Integer goodsQtyMax;
    //商品JSON查询时ID
    private String goodsIdJson;

    public String getGoodsIdJson() {
        return "$.id_"+(this.goodsId == null ? "" : this.goodsId.toString());
    }
}
