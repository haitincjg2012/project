package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "ph_dish_type")
public class DishType  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -324332703954212623L;
	@Id
	private Long id;
	@Column(name = "typeName")
	private String typeName;//分类名称
	@Column(name = "dishSpecificationId")
	private Long dishSpecificationId;//规格id
	@Column(name = "type")
	private int type;//类型  0 菜品   1 大厅 包间
	@Column(name = "merchantId")
	private Long merchantId;//商户id
	@Column(name = "created_time")
	private Date created_time;
	@Column(name = "update_time")
	private Date update_time;
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

	public Long getDishSpecificationId() {
		return dishSpecificationId;
	}

	public void setDishSpecificationId(Long dishSpecificationId) {
		this.dishSpecificationId = dishSpecificationId;
	}
}
