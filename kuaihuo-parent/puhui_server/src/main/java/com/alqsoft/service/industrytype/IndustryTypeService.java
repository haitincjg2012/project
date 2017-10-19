package com.alqsoft.service.industrytype;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;
import org.alqframework.webmvc.springmvc.Result;

import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.user.User;
import com.alqsoft.model.IndustryTypeVo;
/**
 * 行业分类
 * @author Administrator
 *
 */
public interface IndustryTypeService extends BaseService<IndustryType>{

	/**
	 * 分页查询数据
	 * 
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyuiResult<List<IndustryType>> findIndustryTypeList(Map<String, Object> map, Integer page, Integer rows);
	
	/**
	 * 添加行业分类
	 * @param industryVo
	 * @return
	 */
	public Result addIndustryType(IndustryTypeVo industryVo);
	
	
	/**
	 * 查询一级分类下的二级分类
	 * @param firstid
	 * @return
	 */
	public List<IndustryType> findSecondIndustryTypeByFirstId(Long firstid);
	
	
	/**
	 * 删除商品分类
	 */
	public Result industryTypeDelete(Long secondid);
	/**
	 * 首页置顶
	 * @param id
	 * @return
	 */
	public Result firstpAppPageTop(Long id,Integer top);
	

	
}
