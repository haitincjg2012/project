package com.alqsoft.controller.mobile.view.huntershow;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.huntershow.HunterShowService;

/**2、批发商详情页,批发商信息+介绍查询
 * @author wangzn
 *
 */
@Controller
@RequestMapping("mobile/view/huntershow")
public class HunterShowController {
	
	@Autowired
	private HunterShowService hunterShowService;
	
	/**一期的路径
	 * @param id
	 * @param member_id
	 * @return
	 */
	@RequestMapping(value="huntershow-list",method=RequestMethod.POST)
	@ResponseBody
	public Result hunterShow(Long id,Long member_id){
		Result rs =  hunterShowService.getHunterShowList(id,member_id);
		return rs;
	}
	
	/**批发商详情页+批发商信息【3张图】
	 * @param id
	 * @param member_id
	 * @return
	 */
	@RequestMapping(value="singleshow-list",method=RequestMethod.POST)
	@ResponseBody
	public Result singleshow(Long id){
		Result rs =  hunterShowService.getSingleShowList(id);
		return rs;
	}
	
	/**批发商详情页+介绍查询
	 * @param id
	 * @param member_id
	 * @return
	 */
	@RequestMapping(value="detailshow-list",method=RequestMethod.POST)
	@ResponseBody
	public Result detailshow(Long id){
		Result rs =  hunterShowService.getDetailShowList(id);
		return rs;
	}


	
}
