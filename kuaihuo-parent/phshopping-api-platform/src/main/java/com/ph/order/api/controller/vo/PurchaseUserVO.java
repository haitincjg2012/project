package com.ph.order.api.controller.vo;

import java.io.Serializable;

/**
 * 进货人信息
 *
 * @author 郑朋
 * @create 2017/5/23
 **/
public class PurchaseUserVO implements Serializable {

    private static final long serialVersionUID = 5176147660159337704L;

    /**
     * id
     */
    private Long id;

    /**
     * 进货商名称
     */
    private String name;

    /**
     * 1 商户 2市级代理
     */
    private Byte type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
