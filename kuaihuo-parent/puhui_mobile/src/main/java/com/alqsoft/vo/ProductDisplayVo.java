package com.alqsoft.vo;

import com.alqsoft.entity.attachment.Attachment;
import org.alqframework.orm.hibernate.IdEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by ywj on 2017/9/28.
 */
public class ProductDisplayVo  {

    private Long hunterId;

    private List<ProcudtTypeVO> procudtTypeVO;

    private String nickname;//店铺名称

    private String logoImage;

    private String agreeStartTime; //接单开始时间
    private String agreeEndTime; //接单关闭时间

    private String sendStartTime; //配送开始时间
    private String sendEndTime; //配送关闭时间

    private Attachment backgroundImage;// 店铺背景图片

    private Long startMoney; // 起配金额

    private String detail;// 详细地址

    private String phone;//电话

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public List<ProcudtTypeVO> getProcudtTypeVO() {
        return procudtTypeVO;
    }

    public void setProcudtTypeVO(List<ProcudtTypeVO> procudtTypeVO) {
        this.procudtTypeVO = procudtTypeVO;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAgreeStartTime() {
        return agreeStartTime;
    }

    public void setAgreeStartTime(String agreeStartTime) {
        this.agreeStartTime = agreeStartTime;
    }

    public String getAgreeEndTime() {
        return agreeEndTime;
    }

    public void setAgreeEndTime(String agreeEndTime) {
        this.agreeEndTime = agreeEndTime;
    }

    public String getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(String sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public String getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(String sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public Attachment getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Attachment backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Long getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Long startMoney) {
        this.startMoney = startMoney;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
