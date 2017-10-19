package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "ph_dish_specification")
public class DishSpecification  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5254445373496036121L;
	@Id
	private Long id;
	@Column(name = "specificationName")
	private String specificationName;//规格名称
	@Column(name = "money")
	private Long money;//单价
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
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
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
}
