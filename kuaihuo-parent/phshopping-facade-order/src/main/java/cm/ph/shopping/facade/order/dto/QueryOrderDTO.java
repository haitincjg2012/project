package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;

/**
 * 查询订单列表DTO
 * Created by wang on 2017/8/29.
 */
public class QueryOrderDTO implements Serializable{
    /**
     * 当前用户id
     */
    private String userId;
    /**
     * 订单状态 1未确认 2已确认 3已评价
     */
    private String status;
    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页显示的行数
     */
    private Integer pageSize;

    /**
     * 起始行
     */
    private Integer beginIndex;
    /**
     * 用户类型
     */
    private String userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBeginIndex() {
        return (pageNum - 1) * pageSize;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
