package com.ph.shopping.facade.spm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.PhMemberOrderOnlineCommentMapper;
import com.ph.shopping.facade.spm.service.IMerchantCommentService;
import com.ph.shopping.utils.NumberFormat;

@Component
@Service(version = "1.0.0")
public class IMerchantCommentServiceImpl implements IMerchantCommentService {

	@Autowired
	private PhMemberOrderOnlineCommentMapper phMemberOrderOnlineCommentMapper;

	private static final Logger logger = LoggerFactory.getLogger(IMerchantCommentServiceImpl.class);

	/**
	 * 批发商评论列表 登入前，与登入后的区别在于关联了这个登入用户是否对评论点赞
	 * 
	 * @param
	 *
	 * @param type
	 *            全部，好评1，中评2 差评3 好评=5星；中评3-4星；差评0-2星
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Result findMerchantCommentList(Long merchantId, Integer type, Integer page, Integer rows, Long memberid) {
		try {
			if (merchantId == null) {
				return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR, "批发商不能为空");
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startIndex", (page - 1) * rows);
			params.put("endIndex", rows);
			params.put("merchantid", merchantId);
			params.put("type", type);
			List<Map<String, Object>> parentcontents = findMerchantCommentForParentList(params);

			Map<String, Object> resultdata = new HashMap<String, Object>();// 返回给手机的数据

			Result result = new Result();

			Map<String, Object> num = getMerchantCommentNum(params);
			resultdata.put("goodnum", 0);// 初始该批发商的好评数
			resultdata.put("commentnum", 0);
			resultdata.put("badnum", 0);
			resultdata.put("goodnum", NumberFormat.getFormatNumber(String.valueOf(num.get("goodnum"))));// 该批发商的好评数
			resultdata.put("commentnum", NumberFormat.getFormatNumber(String.valueOf(num.get("commentnum"))));// 该批发商的中评数
			resultdata.put("badnum", NumberFormat.getFormatNumber(String.valueOf(num.get("badnum"))));// 该批发商的差评数
			if (parentcontents == null || parentcontents.isEmpty()) {

				result.setSuccess(true);
				result.setCode("200");
				result.setMessage("该商户没有评论");
				result.setData(resultdata);
				return result;
			}

			List<Map<String, Object>> huntercontent = new ArrayList<Map<String, Object>>();// 评论列表，包括父级和子级
			if (parentcontents.size() > 0) {

				for (Map<String, Object> everyparent : parentcontents) { // 循环查询每个父级下的子评论列表
					// ================如果用户是登录状态，要查询用户对这条评论的点赞状态=========================
					if (memberid != null) {//
						Map<String, Object> fabulousparams = new HashMap<String, Object>();
						fabulousparams.put("commentid", (Long) everyparent.get("commentid"));
						fabulousparams.put("memberid", memberid);
						Map<String, Object> fabulousstatus = getHunterCommentParentForMemberFabulousStatus(
								fabulousparams);
						if (fabulousstatus == null) {
							everyparent.put("fabulousstatus", 2);
						} else {
							// type 点赞 1 取消赞2
							everyparent.put("fabulousstatus",
									fabulousstatus.get("type") == null ? 2 : fabulousstatus.get("type"));
						}

					}
					// ================================================================================
					List<Map<String, Object>> soncontent = findSonHunterCommentForByParentIdList(
							(Long) everyparent.get("porderid"));// 父级下的子评论列表
					/*
					 * String s = new
					 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
					 * everyparent.get("pcreatedtime"));
					 * everyparent.put("pcreatedtime", s);
					 */
					for (Map<String, Object> map : soncontent) {
						
						if (map.get("merchantName") != null && !"".equals(map.get("merchantName"))) {
							map.put("merchantName", "商家回复");
						}
						if (map.get("merchantName") == null && map.get("memberName") == null ) {
							map.put("memberName", "匿名用户");
						}

					}
					everyparent.put("soncontentList", soncontent);
					everyparent.remove("goodnum");
					everyparent.remove("commentnum");
					everyparent.remove("badnum");
					huntercontent.add(everyparent);
				}
			}
			resultdata.put("contentList", huntercontent);
			return ResultUtil.getResult(RespCode.Code.SUCCESS, resultdata);
		} catch (Exception e) {
			logger.error("查询app首页批发商评论列表：" + e.getMessage());
			return ResultUtil.getResult(RespCode.Code.FAIL, "查询失败");
		}

	}

	/**
	 * 商户评论父级列表
	 */
	@Override
	public List<Map<String, Object>> findMerchantCommentForParentList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return phMemberOrderOnlineCommentMapper.findMerchantCommentForParentList(params);
	}

	/**
	 * 商户评论父级列表数量,也是该商户的评论总数量
	 */
	@Override
	public int getHunterCommentListForParentCount(Long hunterid) {
		return phMemberOrderOnlineCommentMapper.getHunterCommentListForParentCount(hunterid);
	}

	/**
	 * 商户评论父级下的子评论
	 */
	@Override
	public List<Map<String, Object>> findSonHunterCommentForByParentIdList(Long parentId) {
		return phMemberOrderOnlineCommentMapper.findSonHunterCommentForByParentIdList(parentId);
	}

	/**
	 * 查询用户对这条评论的点赞状态
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getHunterCommentParentForMemberFabulousStatus(Map<String, Object> params) {
		return phMemberOrderOnlineCommentMapper.getHunterCommentParentForMemberFabulousStatus(params);
	}

	/**
	 * 获取评价数
	 */
	@Override
	public Map<String, Object> getMerchantCommentNum(Map<String, Object> params) {
		return phMemberOrderOnlineCommentMapper.getMerchantCommentNum(params);
	}

}
