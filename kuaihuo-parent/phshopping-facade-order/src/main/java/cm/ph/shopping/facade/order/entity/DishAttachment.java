package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "ph_dish_attachment")
public class DishAttachment  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -379570693526231689L;
	@Id
	private Long id;
	@Column(name = "address")
	private String address;
	@Column(name = "detail_content")
	private String detail_content;
	@Column(name = "header")
	private String header;
	@Column(name = "name")
	private String name;
	@Column(name = "dishId")
	private Long dishId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetail_content() {
		return detail_content;
	}
	public void setDetail_content(String detail_content) {
		this.detail_content = detail_content;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
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
