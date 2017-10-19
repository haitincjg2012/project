package com.alqsoft.dto;

import java.io.Serializable;
import java.util.Date;

public class HunterTime  implements Serializable{
    private Long id;
    private String agreeStartTime; //接单开始时间
    private String agreeEndTime; //接单关闭时间
    private String sendStartTime; //发货开始时间
    private String sendEndTime; //发货关闭时间
    private Long beiHuoTime; // 备货时间
    private Long startMoney; // 起配金额
    private String  closeStartTime;//打烊开始时间
    private String closeEndTime;// 打烊结束时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBeiHuoTime() {
        return beiHuoTime;
    }

    public void setBeiHuoTime(Long beiHuoTime) {
        this.beiHuoTime = beiHuoTime;
    }

    public Long getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Long startMoney) {
        this.startMoney = startMoney;
    }

    public String getCloseStartTime() {
        return closeStartTime;
    }

    public void setCloseStartTime(String closeStartTime) {
        this.closeStartTime = closeStartTime;
    }

    public String getCloseEndTime() {
        return closeEndTime;
    }

    public void setCloseEndTime(String closeEndTime) {
        this.closeEndTime = closeEndTime;
    }




}
