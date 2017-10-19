package cm.ph.shopping.facade.order.request;

import java.io.Serializable;
/**
 * 
* @ClassName: UserDTO
* @Description: 用户请求DTO
* @author 王强
* @date 2017年4月25日 下午5:53:13
 */
public class UserDTO implements Serializable {
	private static final long serialVersionUID = -5170043359372472529L;
	private Long supplierId;//供应商id
	
	private Long agentId;//代理商id
	
	private String orderNo;//订单号
	
	private Integer roleCode;//角色编码
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

}
