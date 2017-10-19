package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName:  BankCardInfoPageDTO   
 * @Description:银行卡分页查询数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:14:53     
 * @Copyright: 2017
 */
public class BankCardInfoPageDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -5912171137882167566L;
	/**
	 * 当前页码
	 */
	private Integer pageNum;
	/**
	 * 每页条数
	 */
	private Integer pageSize;

	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 银行所绑定的手机号
	 */
	private String bindPhone;
	/**
	 * 开户银行
	 */
	private String bankName;
	/**
	 * 开户人姓名
	 */
	private String ownName;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 创建人
	 */
	private Long createrId;
	/**
	 * 创建ip
	 */
	private String createIp;
	/**
	 * 是否删除
	 */
	private Byte isDelete;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getBindPhone() {
		return bindPhone;
	}
	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOwnName() {
		return ownName;
	}
	public void setOwnName(String ownName) {
		this.ownName = ownName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
}
