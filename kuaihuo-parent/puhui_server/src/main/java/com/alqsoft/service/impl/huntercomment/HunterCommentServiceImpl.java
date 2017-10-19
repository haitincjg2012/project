package com.alqsoft.service.impl.huntercomment;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.huntercomment.HunterCommentDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.huntercomment.HunterComment;
import com.alqsoft.entity.huntercommentfabulous.HunterCommentFabulous;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.huntercomment.HunterCommentService;
import com.alqsoft.service.huntercommentfabulous.HunterCommentFabulousService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.order.OrderService;


@Service
@Transactional(readOnly=true)
public class HunterCommentServiceImpl implements HunterCommentService{

	private static final Logger logger = LoggerFactory.getLogger(HunterCommentServiceImpl.class);
	@Autowired
	private HunterCommentDao hunterCommentDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private HunterService hunterService;
	@Autowired
	private HunterCommentFabulousService hunterCommentFabulousService;
	
	@Cacheable(key="'alq_hunter_comment'+#arg0",value="alq_hunter_comment")
	@Override
	public HunterComment get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterCommentDao.findOne(arg0);
	}

	@CacheEvict(key="'alq_hunter_comment'+#arg0.id",value="alq_hunter_comment")
	@Transactional
	@Override
	public HunterComment saveAndModify(HunterComment arg0) {
		// TODO Auto-generated method stub
		return hunterCommentDao.save(arg0);
	}
	/**
	 *删除
	 *@param arg0
	 *@return 
	 */
	@CacheEvict(key="'alq_hunter_comment'+#arg0",value="alq_hunter_comment")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * app首页批发商评论列表--游客给批发商评论者的子评论，后需求变更只能批发商能回复，游客不能回复了
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id 
	 */
	@Override
	@Transactional
	public Result saveVisitorHunterComment(Long hunterid, Long masterid, Long visitorid, String content) {
		if(hunterid==null||masterid==null||visitorid==null){
			return ResultUtils.returnError("参数id不能为空");
		}
		try {
			HunterComment parentdb = getRowLock(masterid);
			if(parentdb==null){
				return ResultUtils.returnError("该评论信息不存在，请刷新界面重试");
			}
			if(parentdb.getHunter()==null||parentdb.getHunter().getId()==null){
				return ResultUtils.returnError("该评论信息异常，未关联批发商信息");
			}
			if(parentdb.getIsOne()==1){
				return ResultUtils.returnError("此评论您已回复，不能再回复了");
			}
			
			if(parentdb.getHunter().getId().longValue()!=hunterid.longValue()){
				return ResultUtils.returnError("该评论所关联的批发商信息与您的批发商信息不符，不能回复");
			}
			HunterComment hunterComment=new HunterComment();
			Member member = new Member();
			member.setId(visitorid);
			hunterComment.setMember(member);//回复评论的会员id
			hunterComment.setHunter(parentdb.getHunter());
			hunterComment.setParent(parentdb);
			hunterComment.setContent(content);
			this.saveAndModify(hunterComment);
			parentdb.setReplyNum(parentdb.getReplyNum()==null?1L:parentdb.getReplyNum()+1);//父级的回复评论数量加1
			parentdb.setIsOne(1);//更新回复
			this.saveAndModify(parentdb);//更新父级评论的回复数量
			return ResultUtils.returnSuccess("评论成功");
		} catch (Exception e) {
			logger.error("app首页列表，保存用户给父级评论："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("评论失败");
		}
	}
	
	@Override
	@Transactional
	public HunterComment getRowLock(Long id) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getRowLock(id);
	}


	/**
	 *  app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 */
	@Override
	public int getDirectHunterCommentCount(Long hunterid, Long memberid) {
		// TODO Auto-generated method stub
		return hunterCommentDao.getDirectHunterCommentCount(hunterid, memberid);
	}

	/**
	 *  app首页批发商评论列表--保存直接评论批发商
	 */
	@Override
	@Transactional
	public Result savaDirectHunterComment(Long hunterid, Long memberid, Integer start, String content) {
		try{
		if(hunterid==null){
			return ResultUtils.returnError("批发商id不能为空");
		}
		if(memberid==null){
			return ResultUtils.returnError("会员id不能为空");
		}
		if(content==null||"".equals(content)){
			return ResultUtils.returnError("请填写评价内容");
		}
		if(start<0||start>5){
			return ResultUtils.returnError("超出星级个数范围,请重新评价");
		}
		Hunter hunterdb = hunterService.getRowLock(hunterid);//要评价的次批发商
		//==========判断该会员是否已有过评价此批发商================
		if(this.getDirectHunterCommentCount(hunterid,memberid)>0){
			return ResultUtils.returnError("您之前已经对该批发商评价过，不能再评价了");
		}
		//==========判断此评价的会员是否在该批发商下有已完成的订单==============
		if(orderService.getMemberHaveOrderForHunterCommentCount(hunterid,memberid)>0){
			return ResultUtils.returnError("您还没有已完成的订单在该批发商下，不能进行评价");
		}
		HunterComment huntercomment = new HunterComment();
		Hunter hunter = new Hunter();
		Member member = new Member();
		hunter.setId(hunterid);
		member.setId(memberid);
		huntercomment.setHunter(hunter);//评价的该批发商
		huntercomment.setMember(member);//评价的会员
		huntercomment.setContent(content);//评论的内容
		huntercomment.setStar(start);//评价的星星数量
		huntercomment.setParent(null);
		huntercomment.setReplyNum(0L);
		huntercomment.setFabulousNum(0L);
		huntercomment.setIsOne(0);
		this.saveAndModify(huntercomment);
		//===========================更新批发商的好评数================
		if(start>=0&&start<=2){
			hunterdb.setBadCommentNum(hunterdb.getBadCommentNum()==null?1L:hunterdb.getBadCommentNum()+1);//差评
		}else if(start>=3&&start<=4){
			hunterdb.setCommentNum(hunterdb.getCommentNum()==null?1L:hunterdb.getCommentNum()+1);//中评
		}else if(start==5){
			hunterdb.setGoodCommentNum(hunterdb.getGoodCommentNum()==null?1L:hunterdb.getGoodCommentNum()+1);//好评
		}else{ }
		hunterService.saveAndModify(hunterdb);
		return ResultUtils.returnSuccess("评论成功");
		
		}catch(Exception e){
			logger.error("app首页批发商评论列表-直接评论批发商："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("评论失败");
		}
		
	}

	/**
	 * type点赞 1   取消赞2
	 * app首页批发商评论列表--用户给父级评论点赞
	 */
	@Override
	@Transactional
	public Result saveFabulousHunterComment(Long commentid, Long memberid, Integer type) {
		try {
			
		
		HunterComment huntercommentdb = getRowLock(commentid);
		HunterCommentFabulous hunterCommentFabulousdb = hunterCommentFabulousService.getHunterCommentFabulousByCommentIdAndMemberId(commentid, memberid);
		if(hunterCommentFabulousdb==null){
			if(type==2){
				return ResultUtils.returnError("操作失败，未有点赞记录");
			}
			HunterCommentFabulous hunterCommentFabulous = new HunterCommentFabulous();
			Member member =new Member();
			member.setId(memberid);
			HunterComment huntercomment = new HunterComment();
			huntercomment.setId(commentid);
			hunterCommentFabulous.setMember(member);//点赞用户id
			hunterCommentFabulous.setHunterComment(huntercomment);//评论id
			hunterCommentFabulous.setType(type);//点赞状态
			hunterCommentFabulousService.saveAndModify(hunterCommentFabulous);//新增记录
		}else{
			hunterCommentFabulousdb.setType(type);
			hunterCommentFabulousService.saveAndModify(hunterCommentFabulousdb);//更新点赞状态
		}
		Long fabulousnum = huntercommentdb.getFabulousNum()==null?0L:huntercommentdb.getFabulousNum();//需要维护的点赞数
		if(type==1){
			huntercommentdb.setFabulousNum(fabulousnum+1);//点赞数加1
		}else{
			huntercommentdb.setFabulousNum(fabulousnum-1<0?0:fabulousnum-1);//点赞数减1
		}
			this.saveAndModify(huntercommentdb);//更新该评论的点赞数量
			return ResultUtils.returnSuccess("成功");
		} catch (Exception e) {
			logger.error("app首页批发商评论列表--用户给父级评论点赞："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("失败");
		}
		
	}


	

}
