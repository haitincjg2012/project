package com.ph.shopping.common.core.other.message.jpush;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.customenum.PushTypeEnum;
/**
 * 
 * @ClassName:  JPushData   
 * @Description:极光推送传输数据   
 * @author: 李杰
 * @date:   2017年5月25日 下午2:56:06     
 * @Copyright: 2017
 */
public class JPushData implements Serializable{
	/**   
	 * @Fields serialVersionUID :   
	 */
	private static final long serialVersionUID = -1733697514177581362L;
	/**
	 * 推送标题
	 */
	private String title;
	/**
	 * 推荐内容
	 */
    private String content;
    /**
     * 弹出提示内容
     */
    private String alter;
    /**
     * 推送分类标签:具体指定哪一个推送设备对象
     */
    private List<String> tags;
    /**
     * 设备注册ID:具体指定哪一个推送设备对象
     */
    private List<String> registrationIds;
    /**
     * 别名alias(即：客户端可以给每个用户注册一个别名alias，和多个标签tag)
     */
    private List<String> alias;
    /**
     * 扩展信息
     */
    private Map<String, String> extras = new HashMap<String, String>(); 
    /**
     * 内容类型
     */
    private String contentType;
    /**
     * 推送方式
     */
    private PushTypeEnum pushType;
    
	public String getAlter() {
		return alter;
	}
	public void setAlter(String alter) {
		this.alter = alter;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getRegistrationIds() {
		return registrationIds;
	}
	public void setRegistrationIds(List<String> registrationIds) {
		this.registrationIds = registrationIds;
	}
	public List<String> getAlias() {
		return alias;
	}
	public void setAlias(List<String> alias) {
		this.alias = alias;
	}
	public Map<String, String> getExtras() {
		return extras;
	}
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public PushTypeEnum getPushType() {
		return pushType;
	}
	public void setPushType(PushTypeEnum pushType) {
		this.pushType = pushType;
	}
	
}
