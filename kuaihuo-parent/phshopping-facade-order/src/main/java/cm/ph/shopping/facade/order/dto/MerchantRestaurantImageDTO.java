package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;


/**
 * 
 * <pre>项目名称：phshopping-facade-order    
 * 类名称：MerchantRestaurantImageDTO    
 * 类描述：菜品  餐位图片表    
 * 创建人：赵俊彪    
 * 创建时间：2017年8月30日 下午4:44:05         
 * @version </pre>
 */
public class MerchantRestaurantImageDTO implements Serializable{

	private static final long serialVersionUID = 4553475355303757119L;
	
    private Long id;

    /** 图片地址 */
    private String url;

    /** 图片类型 2 菜品图片  1餐位图片   */
    private Integer type;

    /** 餐位id 关联商户表主键id */
    private Long dishId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

}
