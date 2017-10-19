package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @Project: phshopping-parent
 * @Desc: 展示订单详情中的菜品和餐位通用实体VO
 * Created by wangxueyang on 2017/9/5 15:54.
 */
public class DishDetailsVO implements Serializable{
    private Long id;
    private String dishName;//名称
    private Long columnMoney;//数据库中展示的单价
    private String money;//单价
    private int dCount;//订购数量
    private String imgAddress;//图片;
    private String subscriptionMoney;//定金
    private int num;//用餐人数
    private long hopetime;//用餐时长
    private String merchantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Long getColumnMoney() {
        return columnMoney;
    }

    public void setColumnMoney(Long columnMoney) {
        this.columnMoney = columnMoney;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getdCount() {
        return dCount;
    }

    public void setdCount(int dCount) {
        this.dCount = dCount;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getSubscriptionMoney() {
        return subscriptionMoney;
    }

    public void setSubscriptionMoney(String subscriptionMoney) {
        this.subscriptionMoney = subscriptionMoney;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getHopetime() {
        return hopetime;
    }

    public void setHopetime(long hopetime) {
        this.hopetime = hopetime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

}
