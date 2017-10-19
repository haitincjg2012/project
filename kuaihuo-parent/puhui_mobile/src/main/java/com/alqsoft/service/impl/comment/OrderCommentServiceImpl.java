package com.alqsoft.service.impl.comment;

import com.alqsoft.dao.comment.OrderCommentDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.ordercomment.OrderComment;
import com.alqsoft.entity.product.Product;
import com.alqsoft.rpc.mobile.RpcOrderCommentService;
import com.alqsoft.service.comment.OrderCommentService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.utils.NumberFormat;
import org.alqframework.date.DateUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly=true)
public class OrderCommentServiceImpl implements OrderCommentService{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderCommentServiceImpl.class);
	
	@Autowired
	private OrderCommentDao orderCommentDao;
	@Autowired
	private RpcOrderCommentService rpcOrderCommentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private HunterService hunterService;
	
	@Override
	public List<Map<String, Object>> findOderCommentForParentList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderCommentDao.findOderCommentForParentList(params);
	}

	@Override
	public int getOrderCommentListForParentCount(Long productid) {
		// TODO Auto-generated method stub
		return orderCommentDao.getOrderCommentListForParentCount(productid);
	}

	@Override
	public List<Map<String, Object>> findSonOrderCommentForByParentIdList(Long parentId) {
		// TODO Auto-generated method stub
		return orderCommentDao.findSonOrderCommentForByParentIdList(parentId);
	}
	
	
	@Override
	public Result findOrderCommentList(Long productid, Integer type, Integer page, Integer rows,Long memberid) {
		try{
			if(productid==null){
				return ResultUtils.returnError("商品id不能为空");
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			params.put("productid", productid);
			params.put("type", type);
			List<Map<String,Object>> parentcontents = findOderCommentForParentList(params);
			
			Map<String,Object> resultdata=new HashMap<String,Object>();//返回给手机的数据
			
//			resultdata.put("goodnum", 0);//初始该批发商的好评数
//			resultdata.put("commentnum", 0);
//			resultdata.put("badnum", 0);
//			resultdata.put("allcommentnum", 0);
			Product product = productService.getProductById(productid);
			Long good=product.getNiceCommentNum()==null?0:product.getNiceCommentNum();
			Long zhong=product.getCommonCommentNum()==null?0:product.getCommonCommentNum();
			Long bad=product.getBadCommentNum()==null?0:product.getBadCommentNum();
			Long total = good+zhong+bad;
			resultdata.put("goodnum",NumberFormat.getFormatNumber(String.valueOf(good)));//该批发商的好评数
			resultdata.put("commentnum",NumberFormat.getFormatNumber(String.valueOf(zhong)));//该批发商的中评数
			resultdata.put("badnum", NumberFormat.getFormatNumber(String.valueOf(bad)));//该批发商的差评数
			resultdata.put("allcommentnum",NumberFormat.getFormatNumber(String.valueOf(total)) );//总评价数
			List<Map<String,Object>> huntercontent=new ArrayList<Map<String,Object>>();//评论列表，包括父级和子级
			if(parentcontents.size()>0){
//				Long good=(Long)parentcontents.get(0).get("goodnum");
//				Long zhong=(Long)parentcontents.get(0).get("commentnum");
//				Long bad =(Long)parentcontents.get(0).get("badnum");
//				resultdata.put("goodnum",good);//该批发商的好评数
//				resultdata.put("commentnum",zhong);//该批发商的中评数
//				resultdata.put("badnum", bad);//该批发商的差评数
//				resultdata.put("allcommentnum", good+zhong+bad);//总评价数
				for(Map<String,Object> everyparent : parentcontents){ //循环查询每个父级下的子评论列表
						//================如果用户是登录状态，要查询用户对这条评论的点赞状态=========================
						if(memberid!=null){//
							Map<String,Object> fabulousparams= new HashMap<String,Object>();
							fabulousparams.put("commentid", (Long)everyparent.get("pcommentid"));
							fabulousparams.put("memberid", memberid);
						Map<String,Object> fabulousstatus =	getOrderCommentParentForMemberFabulousStatus(fabulousparams);
						if(fabulousstatus==null){
							everyparent.put("fabulousstatus", 2);
						}else{
							// type  点赞 1   取消赞2
							everyparent.put("fabulousstatus", fabulousstatus.get("type")==null?2:fabulousstatus.get("type"));
						}
						}
						//================================================================================
						String createTime=DateUtils.dateFormat((Date)everyparent.get("pcreatedtime"), "yyyy-MM-dd HH:mm:ss");
						everyparent.put("pcreatedtime", createTime);
					List<Map<String,Object>> soncontent=findSonOrderCommentForByParentIdList((Long)everyparent.get("pcommentid"));//父级下的子评论列表
					for(Map<String,Object> everyson : soncontent){
						everyson.put("created_time", DateUtils.dateFormat((Date)everyson.get("created_time"), "yyyy-MM-dd HH:mm:ss"));
					}
					everyparent.put("soncontentList", soncontent);
					huntercontent.add(everyparent);
				}
			}
			resultdata.put("contentList", huntercontent);
			return ResultUtils.returnSuccess("查询成功", resultdata);
			}catch(Exception e){
				logger.error("查询商品评论列表："+e.getMessage());
				return ResultUtils.returnError("查询失败");
			}
	}

	/**
	 * 商品评论--直接评论批发商
	 */
	@Override
	public Result savaDirectOrderComment(Long orderid,Long memberid, Integer start, String content) {
		// TODO Auto-generated method stub
		return rpcOrderCommentService.savaDirectOrderComment(orderid,memberid, start, content);
	}

	/**
	 * 商品评论---游客给父级评论者评论
	 */
	@Override
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content) {
		// TODO Auto-generated method stub
		return rpcOrderCommentService.saveVisitorOrderComment(hunterid,masterid, visitorid, content);
	}


	/**
	 * 查询用户对评论的点赞状态
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getOrderCommentParentForMemberFabulousStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderCommentDao.getOrderCommentParentForMemberFabulousStatus(params);
	}

	@Override
	public OrderComment getOrderCommentById(Long commentid) {
		// TODO Auto-generated method stub
		return orderCommentDao.getOrderCommentById(commentid);
	}

	/**
	 * 商品评论列表，给父级评论点赞   1点赞   2取消赞
	 */
	@Override
	public Result saveFabulousOrderComment(Long commentid, Long memberid, Integer type) {
		if(commentid==null||memberid==null||type==null){
			return ResultUtils.returnError("参数错误,id不能为空");
		}
		if(type!=1&&type!=2){
			return ResultUtils.returnError("点赞参数非法值错误");
		}
		if(this.getOrderCommentById(commentid)==null){
			return ResultUtils.returnError("该评论不存在，请刷新界面重试");
		}
		return rpcOrderCommentService.saveFabulousOrderComment(commentid,memberid,type);
		
	}

	/**
	 * 根据订单号查询该商品的评价详情
	 */
	@Override
	public OrderComment getOrderCommentByOrderId(Long orderid) {
		// TODO Auto-generated method stub
		return orderCommentDao.getOrderCommentByOrderId(orderid);
	}

	/**
	 * 订单评价详情byId
	 */
	@Override
	public Result getOrderCommentDetail(Long orderid,Long memberid) {
		// TODO Auto-generated method stub
		try {
			if(orderid==null){
				return ResultUtils.returnError("参数错误,订单id为空");
			}
			OrderComment ordercomment = getOrderCommentByOrderId(orderid);
			if(ordercomment==null){
				return ResultUtils.returnError("该订单评论信息不存在");
			}
			if(ordercomment.getMember()==null||ordercomment.getMember().getId()==null){
				return ResultUtils.returnError("该订单评论信息异常，关联用户为空");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("masterid", ordercomment.getId());
			data.put("memberid", ordercomment.getMember().getId());//用户id
			data.put("start", ordercomment.getStartNum());//星
			data.put("content",ordercomment.getContent());//评价内容
			String createdTime  =	DateUtils.dateFormat(ordercomment.getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
			data.put("createdTime",createdTime);//评论时间
			List<Map<String,Object>> soncontent=findSonOrderCommentForByParentIdList(ordercomment.getId());//回复的评论
			data.put("hasReply", soncontent.size()==0?0:1);//0未回复，1为已回复 
			for(Map<String,Object> everyson : soncontent){
				everyson.put("createdTime", DateUtils.dateFormat((Date)everyson.get("created_time"), "yyyy-MM-dd HH:mm:ss"));
			}
			data.put("soncontent", soncontent);
			return ResultUtils.returnSuccess("查询成功", data);
		} catch (Exception e) {
			logger.error("查询商品评论详情："+e.getMessage());
			return ResultUtils.returnSuccess("查询失败");
		}
		
	}

	/**
	 * 属于该批发商下订单的评价列表 
	 */
	@Override
	public Result hunterOrderCommentList(Long hunterid, Integer type, Integer page, Integer rows,Long memberid){
		try{
			if(hunterid==null){
				return ResultUtils.returnError("批发商id不能为空");
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			params.put("hunterid", hunterid);
			params.put("type", type);
			List<Map<String,Object>> parentcontents = findHunterOrderCommentList(params);
			
			Map<String,Object> resultdata=new HashMap<String,Object>();//返回给手机的数据
			Hunter hunter = hunterService.getById(hunterid);
			resultdata.put("goodnum", NumberFormat.getFormatNumber(String.valueOf(hunter.getGoodCommentNumOrder())));//初始该批发商的好评数
			resultdata.put("commentnum", NumberFormat.getFormatNumber(String.valueOf(hunter.getCommentNumOrder())));
			resultdata.put("badnum", NumberFormat.getFormatNumber(String.valueOf(hunter.getBadCommentNumOrder())));
			//resultdata.put("allcommentnum", 0);
			List<Map<String,Object>> huntercontent=new ArrayList<Map<String,Object>>();//评论列表，包括父级和子级
			if(parentcontents.size()>0){
//				Long good=(Long)parentcontents.get(0).get("goodnumorder");
//				Long zhong=(Long)parentcontents.get(0).get("commentnumorder");
//				Long bad =(Long)parentcontents.get(0).get("badnumorder");
//				resultdata.put("goodnum", good);//该批发商的好评数
//				resultdata.put("commentnum", zhong);//该批发商的中评数
//				resultdata.put("badnum", bad);//该批发商的差评数
				//resultdata.put("allcommentnum", good+zhong+bad);//总评价数
				for(Map<String,Object> everyparent : parentcontents){ //循环查询每个父级下的子评论列表
						//================如果用户是登录状态，要查询用户对这条评论的点赞状态=========================
						if(memberid!=null){//
							Map<String,Object> fabulousparams= new HashMap<String,Object>();
							fabulousparams.put("commentid", (Long)everyparent.get("pcommentid"));
							fabulousparams.put("memberid", memberid);
						Map<String,Object> fabulousstatus =	getOrderCommentParentForMemberFabulousStatus(fabulousparams);
						if(fabulousstatus==null){
							everyparent.put("fabulousstatus", 2);
						}else{
							// type  点赞 1   取消赞2
							everyparent.put("fabulousstatus", fabulousstatus.get("type")==null?2:fabulousstatus.get("type"));
						}
						
						}
						//================================================================================
						//格式化时间
						String createTime=DateUtils.dateFormat((Date)everyparent.get("pcreatedtime"), "yyyy-MM-dd HH:mm:ss");
						everyparent.put("pcreatedtime", createTime);//父级评论时间
					List<Map<String,Object>> soncontent=findSonOrderCommentForByParentIdList((Long)everyparent.get("pcommentid"));//父级下的子评论列表
					for(Map<String,Object> everyson : soncontent){
						everyson.put("created_time", DateUtils.dateFormat((Date)everyson.get("created_time"), "yyyy-MM-dd HH:mm:ss"));
					}
					everyparent.put("soncontentList", soncontent);
					everyparent.remove("goodnumorder");everyparent.remove("commentnumorder");everyparent.remove("badnumorder");
					huntercontent.add(everyparent);
				}
			}
			resultdata.put("contentList", huntercontent);
			return ResultUtils.returnSuccess("查询成功", resultdata);
			}catch(Exception e){
				logger.error("查询属于该批发商订单评论列表："+e.getMessage());
				return ResultUtils.returnError("查询失败");
			}
		
	}
	
	/**
	 * 查询属于该批发商下订单的评价列表 
	 */
	@Override
	public List<Map<String, Object>> findHunterOrderCommentList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderCommentDao.findHunterOrderCommentList(params);
	}

	@Override
	public Result getOrderCommentByOrderAndMember(Long oid) {
		Result result = new Result();
		try {
			String content= orderCommentDao.getordercommentByOrderAndMember(oid);
			result.setCode(1);
			result.setMsg("成功");
			result.setContent(content);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(0);
			result.setMsg("失败");
		}finally {
			return result;
		}
	}
	/**
	 * 订单评价详情通过订单号
	 */
	@Override
	public Result getOrderCommentDetailByOrderNo(String orderno, Long mId) {
		try {
			if(orderno==null){
				return ResultUtils.returnError("参数错误,订单号为空");
			}
			//通过订单号获取评论详情
			List<OrderComment> orderCommentDetailByOrderNo = orderCommentDao.getOrderCommentDetailByOrderNo(orderno);

			if(orderCommentDetailByOrderNo == null){
				return ResultUtils.returnError("该订单评论信息不存在");
			}
			if(orderCommentDetailByOrderNo !=null && orderCommentDetailByOrderNo.size()<1){
				return ResultUtils.returnError("该订单评论信息不存在");
			}

			ArrayList list = new ArrayList();
			for (int i = 0; i < orderCommentDetailByOrderNo.size(); i++) {

				    OrderComment ordercomment=orderCommentDetailByOrderNo.get(i);

					if(ordercomment.getMember()==null||ordercomment.getMember().getId()==null){
						return ResultUtils.returnError("该订单评论信息异常，关联用户为空");
					}
					if(ordercomment.getHunter()==null){
						return ResultUtils.returnError("该订单评论信息异常，关联批发商为空");
					}
				Map map=orderCommentDao.getHunterHeadImageAndNickName(ordercomment.getHunter().getId());

					Map<String,Object> data = new HashMap<String,Object>();
					data.put("masterid", ordercomment.getId());
					data.put("memberid", ordercomment.getMember().getId());//用户id
					data.put("start", ordercomment.getStartNum());//星
					data.put("content",ordercomment.getContent());//评价内容
					String createdTime  =	DateUtils.dateFormat(ordercomment.getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
					data.put("createdTime",createdTime);//评论时间
					List<Map<String,Object>> soncontent=findSonOrderCommentForByParentIdList(ordercomment.getId());//回复的评论
					data.put("hasReply", soncontent.size()==0?0:1);//0未回复，1为已回复
					for(Map<String,Object> everyson : soncontent){
						everyson.put("createdTime", DateUtils.dateFormat((Date)everyson.get("created_time"), "yyyy-MM-dd HH:mm:ss"));
					}
					data.put("soncontent", soncontent);
				    data.put("hunterId",map.get("id"));
				    data.put("nickname",map.get("nickname"));
				    data.put("headaddress",map.get("address"));
					list.add(data);
			}
			return ResultUtils.returnSuccess("查询成功", list);
		} catch (Exception e) {
			logger.error("查询商品评论详情通过订单号："+e.getMessage());
			return ResultUtils.returnSuccess("查询失败");
		}

	}

}
