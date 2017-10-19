package com.ph.shopping.facade.pay.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * @项目：phshopping-facade-pay
 * @描述：代理商充值记录 实体
 * @作者： 张霞
 * @创建时间： 19:42 2017/3/22
 * @Copyright @2017 by zhangxia
 */
@Table(name="ph_agent_charge_record")
public class AgentChargeRecord extends BaseEntity {
    private static final long serialVersionUID = 273433423349452846L;
    /**
     * 代理商id
     */
    @Column(name = "agentId")
    @NotBlank(message="[代理商id]不可为空")
    private Long agentId;
    /**
     * 充值后转为的积分数值
     */
    @Column(name = "score")
    private Long score;
    /**
     * 交易订单号
     */
    @Column(name = "orderNum")
    private String orderNum;
    /**
     *充值方式:1,充值，2，商品支付
     */
    @Column(name = "chargeType")
    private byte chargeType;
    /**
     * 充值状态:0充值中，1充值成功，2充值失败
     */
    @Column(name = "chargeStatus")
    private byte chargeStatus;
    
    /**
     * MD5加密报文值
     */
    @Column(name = "md5Str")
    private String md5Str;
    
    @Column(name="creater")
    private  Long creater;//创建人
    
    @Column(name="operater")
    private  Long operater;//更新人
    
    /**
     * 返回报文值
     */
    @Column(name = "responseCode")
    private String responseCode;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public byte getChargeType() {
        return chargeType;
    }

    public void setChargeType(byte chargeType) {
        this.chargeType = chargeType;
    }

    public byte getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(byte chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
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
