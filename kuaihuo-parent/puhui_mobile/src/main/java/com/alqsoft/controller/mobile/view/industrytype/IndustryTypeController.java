package com.alqsoft.controller.mobile.view.industrytype;

import com.alqsoft.service.industrytype.IndustryTypeService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  行业分类
 * @author Xuejizheng
 * @date 2017-03-02 10:02
 * @see
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/view/industrytype")
public class IndustryTypeController {

    @Autowired
    private IndustryTypeService industryTypeService;

    /**
     * 获取所有行业分类
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Result  getList(@RequestParam(value = "pid",required = false) Long pid,
	                       @RequestParam(value="type",required = false)Long type){
        return industryTypeService.getIndustryTypeList(pid,type);
    }
    
    /**
     * 获取第一级分类，,最多获取到的行业分类为16条
     */
    	@RequestMapping(value = "first",method = RequestMethod.POST)
    	public Result getAllIndustryTypeFirst(){
    		
    		Result result = industryTypeService.getAllIndustryTypeFirst();
    		return result;
    	}
    	
    	/**
    	 * 获取第二级分类
    	 * id是一级类别的id
    	 * */
    	@RequestMapping(value = "second",method = RequestMethod.POST)
    	public Result getAllIndustryTypeSecond(@RequestParam(value="id") Integer id){
    		
    		Result result = industryTypeService.getAllIndustryTypeSecond(id);
    		return result;
    	}
    	
    	/**
         * 获取第一,二级分类
         */
        @RequestMapping(value = "findallindustrytype",method = RequestMethod.POST)
        public Result findAllIndustryTypeToAll(){
        		
        	Result result = industryTypeService.findAllIndustryType();
        	return result;
        }   	
    	
        /**
         * 根据1级分类id获取二级分类
         */
        @RequestMapping(value = "findsecondindustrytypebyid",method = RequestMethod.POST)
        public Result findSecondAllIndustryTypeById(
        		@RequestParam(value = "parentId")int parentId,
        		@RequestParam(value = "page",defaultValue ="1",required = false)int page,
				@RequestParam(value = "rows",defaultValue ="15",required = false)int rows){
        		
        	Result result = industryTypeService.findAllIndustryType(parentId,page,rows);
        	return result;
        }  
        
        /**
         * 根据分类获取批发商信息
         */
       @RequestMapping(value = "findhunterbyindustryid",method = RequestMethod.POST)
        public Result findHunterByiIndustryId(
        		@RequestParam(value = "fistTypeId",defaultValue ="",required = false) String fistTypeId,
        		@RequestParam(value = "secondTypeId",defaultValue ="",required = false) String secondTypeId,
        		@RequestParam(value="longitude",defaultValue="lo") String longitude,
        		@RequestParam(value="latitude",defaultValue="la") String latitude,
        		@RequestParam(value = "page",defaultValue ="1",required = false)int page,
				@RequestParam(value = "rows",defaultValue ="15",required = false)int rows
				){
        		
        	Result result = industryTypeService.findHunterByiIndustryId(fistTypeId,secondTypeId,page,rows,longitude,latitude);
        	return result;
        }  
        
       /**
        * 获取一级分类下的所有批发商信息
        */
      @RequestMapping(value = "findallhunterbyindustryid",method = RequestMethod.POST)
       public Result findAllHunterByiIndustryId(
       		@RequestParam(value = "fistTypeId",defaultValue ="",required = false) String fistTypeId,
       		@RequestParam(value="longitude",defaultValue="lo") String longitude,
    		@RequestParam(value="latitude",defaultValue="la") String latitude,
       		@RequestParam(value = "page",defaultValue ="1",required = false)int page,
				@RequestParam(value = "rows",defaultValue ="15",required = false)int rows
				){
       		
       	Result result = industryTypeService.findAllHunterByiIndustryId(fistTypeId,page,rows,longitude,latitude);
       	return result;
       }   
      /**
       * 获取一一级行业分类,最多有16条
       * @return
       */
      @RequestMapping(value = "industrytypefirstlimit",method = RequestMethod.POST)
      public Result getIndustryTypeFirstLimit(	@RequestParam(value = "page",defaultValue ="1",required = false)int page,
				                                @RequestParam(value = "rows",defaultValue ="16",required = false)int rows) {
    	 
    	  Result industryTypeFirstLimit = industryTypeService.getIndustryTypeFirstLimit(page-1,page*rows);
    	  return industryTypeFirstLimit;
    	  
      }
    	
}
