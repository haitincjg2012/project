package com.ph.shopping.facade.order.service.impl;

import java.util.Date;
import java.util.List;

import cm.ph.shopping.facade.order.vo.CommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.IMemberOrderOnlineMapper;
import com.ph.shopping.facade.mapper.IOnlineOrderCommentMapper;

import cm.ph.shopping.facade.order.dto.CommentDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnline;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineComment;
import cm.ph.shopping.facade.order.service.IOnlineOrderCommentService;
import cm.ph.shopping.facade.order.vo.OrderOnlineCommentVO;

@Component
@Service(version="1.0.0")
public class IOnlineOrderCommentServiceImpl implements IOnlineOrderCommentService{
	
	private static final Logger logger = LoggerFactory.getLogger(IOnlineOrderCommentServiceImpl.class);

	@Autowired
	private IMemberOrderOnlineMapper iMemberOrderOnlineMapper;
	
	@Autowired
	private IOnlineOrderCommentMapper iOnlineOrderCommentMapper;
	
	/**
	 * 直接评论订单
	 */
	
	@Override
	@Transactional
	public Result savaDirectOrderComment(CommentDTO commentDTO) {
		logger.info("会员直接评论订单传入参数：订单id："+commentDTO.getOrderid()+"  会员id："+ commentDTO.getMemberid()+" 评论内容："+commentDTO.getContent()+"星星数量："+commentDTO.getStart());
		try{
			if(commentDTO.getOrderid()==null){
				return ResultUtil.setResult(false, "订单ID不能为空");
			}
			if(commentDTO.getMemberid()==null){
				return ResultUtil.setResult(false, "会员ID不能为空");
			}
			if(commentDTO.getContent()==null||"".equals(commentDTO.getContent())){
				return ResultUtil.setResult(false,"评价内容不能为空,请填写评价内容");
			}
			if(commentDTO.getStart()<0 || commentDTO.getStart()>5){
				return ResultUtil.setResult(false,"超出星级个数范围,请重新评价");
			}
			//=========================查询该订单的评价状态=======================
			PhMemberOrderOnline orderOnline = iMemberOrderOnlineMapper.selectOrderByOrderId(commentDTO.getOrderid());
			if(orderOnline==null){
				return ResultUtil.setResult(false,"系统未查询到您的订单信息,请刷新重试");
			}
			//已经评论的状态
			if(orderOnline.getStatus()==3){
				return ResultUtil.setResult(false,"该订单商品您已评价过了,不能再平价");
			}
			if(orderOnline.getMemberId() ==null || orderOnline.getMemberId()==null){
				return ResultUtil.setResult(false,"该订单异常,关联用户id为空");
			}
			if(orderOnline.getMerchantId()==null||orderOnline.getMerchantId()==null){
				return ResultUtil.setResult(false,"该订单异常,关联商户id为空");
			}
			/*if(orderdb.getProduct()==null||orderdb.getProduct().getId()==null){
				return ResultUtils.returnError("该订单异常,关联商品id为空");
			}*/
			if(orderOnline.getMemberId().longValue()!=commentDTO.getMemberid().longValue()){
				return ResultUtil.setResult(false,"该订单不是您所下的订单，无法评价");
			}
			//订单未完成(订单交易状态必须是完成状态)
			if(orderOnline.getStatus() == 1){
				return ResultUtil.setResult(false,"该订单状态未确认无法评价");
			}
			if(commentDTO.getContent()==null||"".equals(commentDTO.getContent().replaceAll("\\s*", ""))){
				return ResultUtil.setResult(false,"请填写评价内容");
			}
			if(commentDTO.getContent().length()>255){
				return ResultUtil.setResult(false,"评价内容不能超过255字");
			}
			//=============保存订单商品评价======================================
			PhMemberOrderOnlineComment orderComment = new PhMemberOrderOnlineComment();
			orderComment.setContent(commentDTO.getContent());
			//当前时间
			orderComment.setCreated_time(new Date());
			orderComment.setFabulousNum(0L);
			orderComment.setIsDelete(0);
			orderComment.setIsOne(0);
			orderComment.setMemberId(commentDTO.getMemberid());
			orderComment.setMerchantId(orderOnline.getMerchantId());
			orderComment.setOrderId(commentDTO.getOrderid());
			orderComment.setParentId(null);
			orderComment.setReplyNum(0L);
			orderComment.setStartNum(commentDTO.getStart());
			orderComment.setUpdate_time(new Date());
			iOnlineOrderCommentMapper.insertOrderOnlineComment(orderComment);
			
			//=============更新订单评价状态=======================================
			//已评价  状态码3
			iMemberOrderOnlineMapper.updateOrderCommentStatus(commentDTO.getOrderid(), 3);
			
			//=============更新商品的评价和批发商的评价好评数中平数差评数(暂时不做)=========================================
			//========================================维护批发商等级 star  level==================================
			return ResultUtil.setResult(true,"评论成功");
			}catch(Exception e){
				logger.error("app用户直接评论订单："+e.getMessage());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
				return ResultUtil.setResult(false,"评论失败");
			}
	}
	/**
	 * 回复评论
	 * @param commentDTO {commentid 评论ID memberid 会员ID (merchantid 商户id) content 评论内容}
	 */
	@Override
	@Transactional
	public Result saveReplyComment(CommentDTO commentDTO , String userType) {
		//{parentid 父级评论ID memberid 会员ID content 评论内容}
		logger.info("商户回复评论传入参数：评论ID: "+commentDTO.getCommentid() + "会员ID: " + commentDTO.getMemberid() +"商户id"+ commentDTO.getMerchantid() +"评论内容: "+commentDTO.getContent()+"uesrType: "+userType);
		try {
			if(commentDTO.getCommentid()==null){
				return ResultUtil.setResult(false,"评论ID不能为空！");
			}
			if(commentDTO.getContent() == null && commentDTO.getContent().equals("")){
				return ResultUtil.setResult(false,"评论内容不能为空！");
			}
			if(userType == null || userType.equals("")){
				logger.info("回复人类型为空!");
				return ResultUtil.setResult(false,"回复异常！");
			}
			if(userType.equals("member")){
				if(commentDTO.getMemberid() == null)
					return ResultUtil.setResult(false,"获取回复人id失败！");

			}else{
				if(commentDTO.getMerchantid() == null)
					return ResultUtil.setResult(false,"获取回复人id失败！");
			}


			/********************   校验订单评论    **********************/
			PhMemberOrderOnlineComment onlineComment = iOnlineOrderCommentMapper.getOrderCommentById(commentDTO.getCommentid());
			if(onlineComment == null){
				return ResultUtil.setResult(false,"订单评价不存在！");
			}
			//订单评论参与人校验（不允许他人加入评论）
			//检索当前订单关联的人身份
			OrderOnlineCommentVO firstOrderComment = iOnlineOrderCommentMapper.getFirstOrderCommentByOrderId(onlineComment.getOrderId());
			if(userType.equals("member") &&  firstOrderComment.getMemberId().longValue() != commentDTO.getMemberid().longValue()){
				return ResultUtil.setResult(false,"订单关联用户身份不符，无法评论！");
			}else if(userType.equals("merchant") && firstOrderComment.getMerchantId().longValue() != commentDTO.getMerchantid().longValue()){
				return ResultUtil.setResult(false,"订单关联用户身份不符，无法评论！");
			}
			/*if(userType.equals("member")){
				if(onlineComment.getMemberId() != commentDTO.getMemberid() || onlineComment.getId() != commentDTO.getOrderid() ){
					return ResultUtil.setResult(false,"订单非法！");
				}
			}else{
				if(onlineComment.getMerchantId() != commentDTO.getMerchantid() || onlineComment.getId() != commentDTO.getOrderid() ){
					return ResultUtil.setResult(false,"订单非法！");
				}
			}*/
			/********************  商户(会员)回复订单评论    **********************/
			
			PhMemberOrderOnlineComment orderComment = new PhMemberOrderOnlineComment();
			orderComment.setContent(commentDTO.getContent());
			//当前时间
			orderComment.setCreated_time(new Date());
			orderComment.setFabulousNum(0L);
			orderComment.setIsDelete(0);
			orderComment.setIsOne(0);
			//判断回复人是商户还是会员
			if(userType.equals("member")){
				orderComment.setMemberId(commentDTO.getMemberid());
			}else if(userType.equals("merchant")){
				orderComment.setMerchantId(commentDTO.getMerchantid());
			}else{
				return ResultUtil.setResult(false,"回复人身份类型异常！");
			}
			orderComment.setOrderId(onlineComment.getOrderId());
			//父级ID
			orderComment.setParentId(commentDTO.getCommentid());
			orderComment.setReplyNum(0L);
			orderComment.setStartNum(null);
			orderComment.setUpdate_time(new Date());
			iOnlineOrderCommentMapper.insertOrderOnlineComment(orderComment);
			return ResultUtil.setResult(true,"回复评论成功");
		} catch (Exception e) {
			logger.error("商户回复订单评价："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtil.setResult(false,"回复评论失败");
		}
	}
	/**
	 * 检索单个订单下所有的评价
	 */
	@Override
	public Result queryOrderCommentsByOrderId(Long orderId) {
		logger.info("查询单个订单下所有评价传入参数： "+orderId);
		try {
			if(orderId == null){
				return ResultUtil.setResult(false, "订单ID不能为空");
			}
			//查询
			//查询当前下 评论信息    OrderOnlineCommentVO
			OrderOnlineCommentVO comment = iOnlineOrderCommentMapper.getFirstOrderCommentByOrderId(orderId);
			//查询当前下 所有评论
			List<CommentVO> comments = iOnlineOrderCommentMapper.getOrderCommentsByOrderId(orderId);

			if(comments == null || comments.size() == 0){
				logger.info("获取失败！！");
				return ResultUtil.setResult(false,"获取所有评价失败！");
			}

			//处理用户标识问题
			for (int i = 0 ; i<comments.size() ; i++){
				if(i==0){
					comments.get(i).setUserType(1);
				}else if(comments.get(i).getMemberId().equals("0")){
					comments.get(i).setUserType(2);
				}else if(comments.get(i).getMerchantId().equals("0")){
					comments.get(i).setUserType(1);
				}
			}
			//封装
			comment.setcList(comments);
			logger.info("获取成功！！");
			return ResultUtil.getResult(RespCode.Code.SUCCESS, comment);
		} catch (Exception e) {
			logger.error("查询单个订单下所有评论失败："+e.getMessage());
			return ResultUtil.setResult(false,"获取所有评价失败！");
		}
	}
}
