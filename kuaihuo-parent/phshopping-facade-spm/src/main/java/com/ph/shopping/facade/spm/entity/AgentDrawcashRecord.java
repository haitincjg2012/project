package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：代理商提现记录表
 *
 * @author：liuy
 *
 * @createTime：2017年4月1日
 *
 * @Copyright @2017 by liuy
 */
@Table(name = "ph_agent_drawcash_record")
public class AgentDrawcashRecord extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 7175742670113579504L;

    @Column(name = "agentId")
    private Long agentId;//代理商Id

    @Column(name = "score")
    private BigDecimal score;//积分

    @Column(name = "handingCharge")
    private BigDecimal handingCharge;//提现手续费

    @Column(name = "status")
    private Integer status;//状态

    @Column(name = "expectedDate")
    private Date expectedDate;//预计到账日期

    @Column(name = "createDate")
    private Date createDate;//申请提现日期

    @Column(name = "drawcashNo")
    private String drawcashNo;//提现编号

    @Column(name = "bankName")
    private String bankName;//银行名称

    @Column(name = "auditState")
    private Integer auditState;//审核状态:-1提现失败，0待审核，1，提现中，2提现成功

    @Column(name = "drawcashIp")
    private String drawcashIp;//提现Ip地址

    @Column(name = "receiver")
    private String receiver;//收款人姓名

    @Column(name = "bankNo")
    private String bankNo;//收款人姓名

    @Column(name = "creater")
    private Long creater;//创建人

    @Column(name = "operater")
    private Long operater;//更新人

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getHandingCharge() {
        return handingCharge;
    }

    public void setHandingCharge(BigDecimal handingCharge) {
        this.handingCharge = handingCharge;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDrawcashNo() {
        return drawcashNo;
    }

    public void setDrawcashNo(String drawcashNo) {
        this.drawcashNo = drawcashNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getDrawcashIp() {
        return drawcashIp;
    }

    public void setDrawcashIp(String drawcashIp) {
        this.drawcashIp = drawcashIp;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Long getOperater() {
        return operater;
    }

    public void setOperater(Long operater) {
        this.operater = operater;
    }


}
