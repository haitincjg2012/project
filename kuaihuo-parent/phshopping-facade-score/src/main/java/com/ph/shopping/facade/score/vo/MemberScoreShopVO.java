package com.ph.shopping.facade.score.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @项目：phshopping-facade-score
 * @描述：商城个人积分流水分页实体
 * @作者： Mr.zheng
 * @创建时间：2017-03-31
 * @Copyright @2017 by Mr.zheng
 */
public class MemberScoreShopVO implements Serializable{

    private static final long serialVersionUID = 2364505339547055226L;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 来源
     */
    private String source;

    /**
     * 积分 正数表示收入 负数表示支出
     */
    private BigDecimal score;

    /**
     * 手续费
     */
    private BigDecimal fee;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
