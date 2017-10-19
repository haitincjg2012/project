package com.alqsoft.service.impl.ordercomment;

import com.alqsoft.dao.ordercomment.OrderCommentDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.ordercomment.OrderComment;
import com.alqsoft.entity.ordercommentfabulous.OrderCommentFabulous;
import com.alqsoft.entity.product.Product;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.ordercomment.OrderCommentService;
import com.alqsoft.service.ordercommentfabulous.OrderCommentFabulousService;
import com.alqsoft.service.product.ProductService;
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

import java.util.Map;


@Service
@Transactional(readOnly=true)
public class OrderCommentServiceImpl implements OrderCommentService{

	private static final Logger logger = LoggerFactory.getLogger(OrderCommentServiceImpl.class);
	@Autowired
	private OrderCommentDao orderCommentDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderCommentFabulousService orderCommentFabulousService;
	@Autowired
	private HunterService hunterService;
	
	@CacheEvict(key="'alq_order_comment'+#arg0",value="alq_order_comment")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Cacheable(key="'alq_order_comment'+#arg0",value="alq_order_comment")
	@Override
	public OrderComment get(Long arg0) {
		// TODO Auto-generated method stub
		return orderCommentDao.findOne(arg0);
	}

	@Override
	@CacheEvict(key="'alq_order_comment'+#arg0.id",value="alq_order_comment")
	@Transactional
	public OrderComment saveAndModify(OrderComment arg0) {
		// TODO Auto-generated method stub
		return orderCommentDao.save(arg0);
	}

	/**
	 * 商品评论---游客给父级评论者评论
	 */
	@Override
	@Transactional
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content) {
		if(hunterid==null||masterid==null||visitorid==null){
			return ResultUtils.returnError("参数id不能为空");
		}
		try {
			OrderComment parentdb = getRowLock(masterid);
			if(parentdb==null){
				return ResultUtils.returnError("该评论信息不存在，请刷新界面重试");
			}
			if((parentdb.getIsDelete()==null?0:parentdb.getIsDelete())==1){
				return ResultUtils.returnError("该评论信息已删除，请刷新界面重试");
			}
			if(parentdb.getOrder()==null||parentdb.getOrder().getId()==null){
				return ResultUtils.returnError("该评论信息异常，未关联订单信息");
			}
			if(parentdb.getOrder().getHunter()==null||parentdb.getOrder().getHunter().getId()==null){
				return ResultUtils.returnError("该评论信息异常，未关联批发商信息");
			}
			if(parentdb.getIsOne()!=null&&parentdb.getIsOne()==1){
				return ResultUtils.returnError("此评论您已回复，不能再回复了");
			}
			if(parentdb.getOrder().getHunter().getId().longValue()!=hunterid.longValue()){
				return ResultUtils.returnError("该评论所关联的批发商信息与" +
						"符，不能回复");
			}
			if(parentdb.getProduct()==null||parentdb.getProduct().getId()==null){
				return ResultUtils.returnError("该评论信息异常，未关商品信息");
			}
			if(content==null||"".equals(content.replaceAll("\\s*", ""))){
				return ResultUtils.returnError("请填写回复内容");
			}
			if(content.length()>200){
				return ResultUtils.returnError("回复内容不能超过200字");
			}
			OrderComment orderComment = new OrderComment();
			Member member = new Member();
			member.setId(visitorid);
			orderComment.setMember(member);
			orderComment.setOrder(parentdb.getOrder());
			orderComment.setParent(parentdb);
			orderComment.setProduct(parentdb.getProduct());
			orderComment.setContent(content);
			orderComment.setHunter(parentdb.getOrder().getHunter());
			this.saveAndModify(orderComment);
			parentdb.setIsOne(1);
			parentdb.setReplyNum(parentdb.getReplyNum()==null?1L:parentdb.getReplyNum()+1);//父级的回复评论数量加1
			this.saveAndModify(parentdb);//更新父级评论的回复数量
			return ResultUtils.returnSuccess("回复成功");
		} catch (Exception e) {
			logger.error("app商品评论列表，游客给父级评论："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("回复失败");
		}
	}

	/**
	 * 用户直接评论商品  start默认0
	 */
	@Override
	@Transactional
	public Result savaDirectOrderComment(Long orderid,Long memberid, Integer start, String content) {
				try{
			if(orderid==null){
				return ResultUtils.returnError("订单id不能为空");
			}
			if(memberid==null){
				return ResultUtils.returnError("会员id不能为空");
			}
			if(content==null||"".equals(content)){
				return ResultUtils.returnError("评价内容不能为空,请填写评价内容");
			}
			if(start<0||start>5){
				return ResultUtils.returnError("超出星级个数范围,请重新评价");
			}
			//=========================查询该订单的评价状态=======================
			Order orderdb =orderService.getRowLock(orderid);
			if(orderdb==null){
				return ResultUtils.returnError("系统未查询到您的订单信息,请刷新重试");
			}
			if(orderdb.getStatus()==4){
				return ResultUtils.returnError("该订单商品您已评价过了,不能再平价");
			}
			if(orderdb.getMember()==null||orderdb.getMember().getId()==null){
				return ResultUtils.returnError("该订单异常,关联用户id为空");
			}
			if(orderdb.getHunter()==null||orderdb.getHunter().getId()==null){
				return ResultUtils.returnError("该订单异常,关联批发商id为空");
			}
			if(orderdb.getOrderNo()==null){
				return ResultUtils.returnError("该订单异常,主订单号为空");
			}
			if(orderdb.getProduct()==null||orderdb.getProduct().getId()==null){
				return ResultUtils.returnError("该订单异常,关联商品id为空");
			}
			if(orderdb.getMember().getId().longValue()!=memberid.longValue()){
				return ResultUtils.returnError("该订单不是您所下的订单，无法评价");
			}
			if(orderdb.getStatus()!=7){
				return ResultUtils.returnError("该订单状态还未确认收货，无法评价");
			}
			if(content==null||"".equals(content.replaceAll("\\s*", ""))){
				return ResultUtils.returnError("请填写评价内容");
			}
			if(content.length()>200){
				return ResultUtils.returnError("评价内容不能超过200字");
			}
			//=============保存订单商品评价======================================
			OrderComment ordercomment = new OrderComment();
			ordercomment.setOrder(orderdb);
			ordercomment.setProduct(orderdb.getProduct());
			ordercomment.setContent(content);
			ordercomment.setParent(null);
			ordercomment.setStartNum(start);
			ordercomment.setHunter(orderdb.getHunter());
			ordercomment.setIsOne(0);
			ordercomment.setReplyNum(0L);
			ordercomment.setFabulousNum(0L);
			ordercomment.setMember(orderdb.getMember());
			ordercomment.setIsDelete(0);
			//*********添加主订单号*******@author wudi*************************
			ordercomment.setOrderNum(orderdb.getOrderNo());
			this.saveAndModify(ordercomment);
			//=============更新订单评价状态=======================================
			orderdb.setStatus(4);//已评价
			orderService.saveAndModify(orderdb);
			//=============更新商品的评价和批发商的评价好评数中平数差评数=========================================
			Product productdb = productService.get(orderdb.getProduct().getId());
			//Hunter hunterdb1 = orderdb.getHunter();
			Hunter hunterdb = hunterService.getRowLock(orderdb.getHunter().getId());
			if(start>=0&&start<=2){
				productdb.setBadCommentNum(productdb.getBadCommentNum()==null?1L:productdb.getBadCommentNum()+1);//商品的差评
				hunterdb.setBadCommentNumOrder(hunterdb.getBadCommentNumOrder()==null?1L:hunterdb.getBadCommentNumOrder()+1);
			}else if(start>=3&&start<=4){
				productdb.setCommonCommentNum(productdb.getCommonCommentNum()==null?1L:productdb.getCommonCommentNum()+1);//中评
				hunterdb.setCommentNumOrder(hunterdb.getCommentNumOrder()==null?1L:hunterdb.getCommentNumOrder()+1);
			}else if(start==5){
				productdb.setNiceCommentNum(productdb.getNiceCommentNum()==null?1L:productdb.getNiceCommentNum()+1);//好评
				hunterdb.setGoodCommentNumOrder(hunterdb.getGoodCommentNumOrder()==null?1L:hunterdb.getGoodCommentNumOrder()+1);
			}else{ }
			//========================================维护批发商等级 star  level===========================================================
			Map<String, Object> hl = hunterService.checkHunterLevel(hunterdb.getOrderMoney()==null?0:hunterdb.getOrderMoney(), hunterdb.getGoodCommentNumOrder()==null?0:hunterdb.getGoodCommentNumOrder());
			if(hl!=null){
				hunterdb.setStar(hl.get("star")==null?0:Integer.valueOf(hl.get("star").toString()));
				hunterdb.setLevel(hl.get("level")==null?0:Integer.valueOf(hl.get("level").toString()));
			}
			productService.saveAndModify(productdb);//更新商品表
			hunterService.saveAndModify(hunterdb);//更新该批发商表
			return ResultUtils.returnSuccess("评论成功");
			
			}catch(Exception e){
				logger.error("app用户直接评论商品："+e.getMessage());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
				return ResultUtils.returnError("评论失败");
			}
	}

	@Override
	public int getDirectOrderCommentCount(Long orderid, Long memberid) {
		// TODO Auto-generated method stub
		return orderCommentDao.getDirectOrderCommentCount(orderid,memberid);
	}

	@Override
	@Transactional
	public OrderComment getRowLock(Long id) {
		// TODO Auto-generated method stub
		return orderCommentDao.getRowLock(id);
	}

	/**
	 * type点赞 1   取消赞2
	 * 商品评论列表--给父级点赞
	 */
	@Override
	@Transactional
	public Result saveFabulousOrderComment(Long commentid, Long memberid, Integer type) {
		try {
		OrderComment ordercommentdb=getRowLock(commentid);
		//查询用户是否对这条父级评论点赞
		OrderCommentFabulous orderCommentFabulousdb=orderCommentFabulousService.getOrderCommentFabulousByCommentIdAndMemberId(commentid, memberid);
		if(orderCommentFabulousdb==null){
			if(type==2){
				return ResultUtils.returnError("操作失败，未有点赞记录");
			}
			OrderCommentFabulous orderCommentFabulous = new OrderCommentFabulous();
			Member member = new Member();
			member.setId(memberid);
			OrderComment ordercomment =  new OrderComment();
			ordercomment.setId(commentid);
			orderCommentFabulous.setMember(member);//点赞用户id
			orderCommentFabulous.setOrderComment(ordercomment);//评论id
			orderCommentFabulous.setType(type);//点赞状态
			orderCommentFabulousService.saveAndModify(orderCommentFabulous);//新增记录
		}else{
			orderCommentFabulousdb.setType(type);
			orderCommentFabulousService.saveAndModify(orderCommentFabulousdb);
		}
		Long fabulousnum = ordercommentdb.getFabulousNum()==null?0L:ordercommentdb.getFabulousNum();//需要维护的点赞数
		if(type==1){
			ordercommentdb.setFabulousNum(fabulousnum+1);
		}else{
			ordercommentdb.setFabulousNum(fabulousnum-1<0?0:fabulousnum-1);//点赞数减1
		}
		this.saveAndModify(ordercommentdb);//更新该评论的点赞数量
		return ResultUtils.returnSuccess("成功");
		} catch (Exception e) {
			logger.error("商品评论列表--用户给父级评论点赞："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("失败");
		}
	}

	@Override
	public OrderComment getCommentByOrderId(Long id) {
		return this.orderCommentDao.getCommentByOrderId(id);
	}

	

}
