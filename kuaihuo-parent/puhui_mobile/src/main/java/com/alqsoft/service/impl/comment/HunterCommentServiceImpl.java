package com.alqsoft.service.impl.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alqsoft.utils.NumberFormat;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.comment.HunterCommentDao;
import com.alqsoft.entity.huntercomment.HunterComment;
import com.alqsoft.rpc.mobile.RpcHunterCommentService;
import com.alqsoft.service.comment.HunterCommentService;


/**
 * 评论
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class HunterCommentServiceImpl implements HunterCommentService{

	@Autowired
	private HunterCommentDao hunterCommentDao;
	@Autowired
	private RpcHunterCommentService rpcHunterCommentService;


	
	
	private static final Logger logger = LoggerFactory.getLogger(HunterCommentServiceImpl.class);
	/**
	 * 批发商评论列表
	 */
	@Override
	public Result findHunterCommentList(Long hunterId,Integer type, Integer page, Integer rows,Long memberid) {
		try{
		if(hunterId==null){
			return ResultUtils.returnError("批发商id不能为空");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		params.put("hunterid", hunterId);
		params.put("type", type);
		List<Map<String,Object>> parentcontents = findHunterCommentForParentList(params);
		
		Map<String,Object> resultdata=new HashMap<String,Object>();//返回给手机的数据
		
		resultdata.put("goodnum", 0);//初始该批发商的好评数
		resultdata.put("commentnum", 0);
		resultdata.put("badnum", 0);
		List<Map<String,Object>> huntercontent=new ArrayList<Map<String,Object>>();//评论列表，包括父级和子级
		if(parentcontents.size()>0){
			resultdata.put("goodnum", NumberFormat.getFormatNumber(String.valueOf(parentcontents.get(0).get("goodnum"))));//该批发商的好评数
			resultdata.put("commentnum", NumberFormat.getFormatNumber(String.valueOf(parentcontents.get(0).get("commentnum"))));//该批发商的中评数
			resultdata.put("badnum", NumberFormat.getFormatNumber(String.valueOf(parentcontents.get(0).get("badnum"))));//该批发商的差评数
			for(Map<String,Object> everyparent : parentcontents){ //循环查询每个父级下的子评论列表
				//================如果用户是登录状态，要查询用户对这条评论的点赞状态=========================
				if(memberid!=null){//
					Map<String,Object> fabulousparams= new HashMap<String,Object>();
					fabulousparams.put("commentid", (Long)everyparent.get("commentid"));
					fabulousparams.put("memberid", memberid);
				Map<String,Object> fabulousstatus =	getHunterCommentParentForMemberFabulousStatus(fabulousparams);
				if(fabulousstatus==null){
					everyparent.put("fabulousstatus", 2);
				}else{
					// type  点赞 1   取消赞2
					everyparent.put("fabulousstatus", fabulousstatus.get("type")==null?2:fabulousstatus.get("type"));
				}
				
				}
				//================================================================================
				List<Map<String,Object>> soncontent=findSonHunterCommentForByParentIdList((Long)everyparent.get("commentid"));//父级下的子评论列表
				everyparent.put("soncontentList", soncontent);
				everyparent.remove("goodnum");
				everyparent.remove("commentnum");
				everyparent.remove("badnum");
				huntercontent.add(everyparent);
			}
		}
		resultdata.put("contentList", huntercontent);
		return ResultUtils.returnSuccess("查询成功", resultdata);
		}catch(Exception e){
			logger.error("查询app首页批发商评论列表："+e.getMessage());
			return ResultUtils.returnError("查询失败");
		}
	}
	
	/**
	 * 批发商评论父级列表
	 */
	@Override
	public List<Map<String, Object>> findHunterCommentForParentList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return hunterCommentDao.findHunterCommentForParentList(params);
	}
	
	/**
	 * 批发商评论父级列表数量,也是该批发商的评论总数量
	 */
	@Override
	public int getHunterCommentListForParentCount(Long hunterid) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getHunterCommentListForParentCount(hunterid);
	}
	
	/**
	 * 批发商评论父级下的子评论
	 */
	@Override
	public List<Map<String, Object>> findSonHunterCommentForByParentIdList(Long parentId) {
		// TODO Auto-generated method stub
		return hunterCommentDao.findSonHunterCommentForByParentIdList(parentId);
	}

	/**
	 * app首页批发商评论列表--游客给父级评论者评论
	 */
	@Override
	public Result saveVisitorHunterComment(Long hunterid, Long masterid, Long visitorid, String content) {
		// TODO Auto-generated method stub
		return rpcHunterCommentService.saveVisitorHunterComment(hunterid, masterid, visitorid, content);
	}

	/**
	 * app首页直接评价批发商
	 * 只有跟该批发商下单并且完成了订单的用户，才能评论 订单状态大于3
	 */
	@Override
	public Result savaDirectHunterComment(Long hunterid, Long memberid, Integer start, String content) {
		
		return rpcHunterCommentService.savaDirectHunterComment(hunterid, memberid, start, content);
	}

	/**
	 * app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 * @param params
	 * @return
	 */
	@Override
	public int getDirectHunterCommentCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getDirectHunterCommentCount(params);
	}

	/**
	 * app首页批发商评论列表--用户给批发商评论列表父级评论点赞    1点赞   2取消赞
	 */
	@Override
	public Result saveFabulousHunterComment(Long commentid, Long memberid, Integer type) {
		// TODO Auto-generated method stub
		if(commentid==null||memberid==null||type==null){
			return ResultUtils.returnError("参数错误,id不能为空");
		}
		if(type!=1&&type!=2){
			return ResultUtils.returnError("点赞参数非法值错误");
		}
		if(this.getHunterCommentById(commentid)==null){
			return ResultUtils.returnError("该评论不存在，请刷新界面重试");
		}
		return rpcHunterCommentService.saveFabulousHunterComment(commentid,memberid,type);
	}

	/**
	 * 查询用户对这条评论的点赞状态
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getHunterCommentParentForMemberFabulousStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getHunterCommentParentForMemberFabulousStatus(params);
	}

	@Override
	public HunterComment getHunterCommentById(Long id) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getHunterCommentById(id);
	}

}
