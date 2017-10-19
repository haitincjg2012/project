package com.alqsoft.service.impl.industrytype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.industrytype.IndustryTypeDao;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.model.IndustryTypeVo;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.industrytype.IndustryTypeService;

/**
 * 行业分类
 * @author Administrator
 *
 */
@Service
@Transactional
public class IndustryTypeServiceImpl implements IndustryTypeService{
	private static Logger logger = LoggerFactory.getLogger(IndustryTypeServiceImpl.class);
	@Autowired
	private IndustryTypeDao industryTypeDao;
	@Autowired
	private AttachmentService attachmentService;

	@CacheEvict(key="'alq_industry_type'+#arg0",value="alq_industry_type")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Cacheable(key="'alq_industry_type'+#arg0",value="alq_industry_type")
	public IndustryType get(Long arg0) {
		// TODO Auto-generated method stub
		return industryTypeDao.findOne(arg0);
	}

	@Override
	@CacheEvict(key="'alq_industry_type'+#arg0.id",value="alq_industry_type")
	public IndustryType saveAndModify(IndustryType arg0) {
		// TODO Auto-generated method stub
		return industryTypeDao.save(arg0);
	}

	/**
	 * 行业列表
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyuiResult<List<IndustryType>> findIndustryTypeList(Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<IndustryType> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				IndustryType.class);
		Page<IndustryType> userPage = industryTypeDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(IndustryType.class, userPage);
	}

	/**
	 * 编辑和添加行业分类
	 */
	@Override
	public Result addIndustryType(IndustryTypeVo industryVo) {
		try {
			if(industryVo==null){
				return SpringMVCUtils.returnError("参数不能为空");
			}
			if(industryVo.getFirstType()==null||industryVo.getFirstType().getAttachment()==null||industryVo.getFirstType().getAttachment().getId()==null){
				return SpringMVCUtils.returnError("请上传行业分类图片");
			}
			if(industryVo.getFirstType().getName()==null||"".equals(industryVo.getFirstType().getName().trim())){
				return SpringMVCUtils.returnError("请填写一级行业分类名称");
			}
			List<IndustryType> secondtypeview = industryVo.getSecondType();//含空对象，要去除
			if(secondtypeview==null||secondtypeview.size()==0){
				return SpringMVCUtils.returnError("请填写二级分类");
			}
			List<IndustryType> secondtypes = new ArrayList<IndustryType>();
			for(IndustryType second : secondtypeview){
				if(second.getName()!=null&&"".equals(second.getName().trim())){
					return SpringMVCUtils.returnError("请填写二级分类名称");
				}
				if(second.getName()!=null){
					secondtypes.add(second);
				}
			}
			if(secondtypes.size()==0){
				return SpringMVCUtils.returnError("请填写二级分类名称");
			}
			if(attachmentService.get(industryVo.getFirstType().getAttachment().getId())==null){
				return SpringMVCUtils.returnError("行业图片信息错误，未找到该图片");
			}
			if(industryVo.getFirstType().getId()==null){
				//========================添加==================
				IndustryType first=industryVo.getFirstType();
				first.setIsDelete(0);
				IndustryType firsttype=this.saveAndModify(first);//保存一级分类
				for(IndustryType data : secondtypes){
					data.setIsDelete(0);
					data.setParentId(firsttype);
					this.saveAndModify(data);
				}
				return SpringMVCUtils.returnSuccess("添加成功");
			}else{//======================编辑==================
				IndustryType firstdb=this.get(industryVo.getFirstType().getId());
				if(firstdb==null){
					return SpringMVCUtils.returnError("该行业分类不存在，无法编辑");
				}
				firstdb.setName(industryVo.getFirstType().getName());
				firstdb.setAttachment(industryVo.getFirstType().getAttachment());
				this.saveAndModify(firstdb);
				for(IndustryType data : secondtypes){//这里既可能既有编辑又有新增的二级分类
					if(data.getId()==null){//新增的二级分类
						data.setParentId(firstdb);
						data.setSortnum(data.getSortnum()==null?0:data.getSortnum());
						if(data.getSortnum()>0){
							data.setSortnumtime(new Date());
						}else{
							data.setSortnumtime(null);
						}
						this.saveAndModify(data);
					}else{//编辑二级分类
						IndustryType seconddb = this.get(data.getId());
						seconddb.setSortnum(data.getSortnum()==null?0:data.getSortnum());
						if(data.getSortnum()>0){
							seconddb.setSortnumtime(new Date());
						}else{
							seconddb.setSortnumtime(null);
						}
						seconddb.setName(data.getName());
						this.saveAndModify(seconddb);
					}
					
				}
				return SpringMVCUtils.returnSuccess("编辑成功");
			}
		
			
		} catch (Exception e) {
			logger.error("后台行业分类添加"+e.getMessage());
			return SpringMVCUtils.returnError("添加失败");
		}
	}

	@Override
	public List<IndustryType> findSecondIndustryTypeByFirstId(Long firstid) {
		// TODO Auto-generated method stub
		return industryTypeDao.findSecondIndustryTypeByFirstId(firstid);
	}

	@Override
	public Result industryTypeDelete(Long secondid) {
		try {
			IndustryType industryTypedb = get(secondid);
			if(industryTypedb==null){
				return SpringMVCUtils.returnError("不存在此行业分类信息");
			}
			int count = industryTypeDao.getHunterByIndustryTypeById(secondid);//该分类下是否有批发商
			if(count>0){
				return SpringMVCUtils.returnError("该分类下有所属批发商，不能删除");
			}
			int c = industryTypeDao.getHunterByIndustyByFisrtId(secondid);
			if(c>0){
				return SpringMVCUtils.returnError("该分类下有所属批发商，不能删除");
			}

			industryTypedb.setIsDelete(1);
			this.saveAndModify(industryTypedb);
			return SpringMVCUtils.returnSuccess("删除成功");
		} catch (Exception e) {
			logger.error("后台行业分类删除"+e.getMessage());
			return SpringMVCUtils.returnError("删除失败");
		}
		
	}

	@Override
	public Result firstpAppPageTop(Long id,Integer top) {
		// TODO Auto-generated method stub
		Result reuslt = new Result();
		IndustryType industryTypedb = get(id);
		if(industryTypedb==null){
			return SpringMVCUtils.returnError("不存在此行业分类信息");
		}
		try {
			industryTypedb.setIsTop(top);// 0不置首页  1置于首页
			industryTypedb.setTopDate(new Date());
			IndustryType save = industryTypeDao.save(industryTypedb);
			if(save !=null ){
				reuslt.setCode(1);
				reuslt.setMsg("修改成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e+"置顶报错");
			return SpringMVCUtils.returnError("首页置顶报错");
		}
	
		return reuslt;
	}
		
	



}
