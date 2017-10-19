package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class DishTypeVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1298680434001699603L;
	private Long id;
	private String typeName;//分类名称
	private int type;//类型  0 菜品   1 大厅 包间
	private Long merchantId;//商户id
	private Date created_time;
	private Date update_time;
	private Long dishTypeId;
	private String address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Long getDishTypeId() {
		return dishTypeId;
	}
	public void setDishTypeId(Long dishTypeId) {
		this.dishTypeId = dishTypeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
