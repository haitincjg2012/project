package com.ph.shopping.facade.order.service.impl;

import cm.ph.shopping.facade.order.constant.*;
import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.entity.*;
import cm.ph.shopping.facade.order.exception.OrderException;
import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.service.IOrderAddressService;
import cm.ph.shopping.facade.order.vo.*;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lorne.tx.annotation.TxTransaction;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.dto.OrderCancelDTO;
import com.ph.shopping.common.core.dto.PrePayDTO;
import com.ph.shopping.common.core.dto.PushDTO;
import com.ph.shopping.common.core.util.PushUtil;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberBankCardBindService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.vo.MemberBankCardBindInfoVO;
import com.ph.shopping.facade.pay.dto.AlipayRefundDTO;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.product.dto.ProductSnapshotDTO;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.service.IProductSkuService;
import com.ph.shopping.facade.product.service.IProductSnapshotService;
import com.ph.shopping.facade.product.vo.ProductSnapshotVO;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.TakePointDTO;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.service.IWarehouseAddressService;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.spm.vo.OnlineTakePointVO;
import com.ph.shopping.facade.spm.vo.WareHouseVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单接口实现
 * @作者： 张霞
 * @创建时间： 10:13 2017/6/1
 * @Copyright @2017 by zhangxia
 */
@Component
@Service(version="1.0.0")
public class OnlineOrderServiceImpl implements IOnlineOrderService{
    // 创建日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IMemberMainOrderOnlineMapper iMemberMainOrderOnlineMapper;
    @Autowired
    IMemberSubOrderOnlineMapper iMemberSubOrderOnlineMapper;
    @Autowired
    IMemberSubOrderOnlineRecordMapper iMemberSubOrderOnlineRecordMapper;
    @Autowired
    IMemberSubOrderOnlineProductsMapper iMemberSubOrderOnlineProductsMapper;
    @Autowired
    ImemberSubOrderRefundMapper imemberSubOrderRefundMapper;
    @Autowired
    IMemberOrderOnlineMapper iMemberOrderOnlineMapper;
    @Reference(version="1.0.0")
    IMerchantService iMerchantService;
    @Reference(version="1.0.0")
    IWarehouseAddressService iWarehouseAddressService;
    @Reference(version="1.0.0")
    ISupplierService iSupplierService;
    @Reference(version = "1.0.0")
    IMemberService iMemberService;
    @Reference(version = "1.0.0")
    IProductSkuService productSkuService;
    @Reference(version="1.0.0")
    IScoreService iScoreService;

    @Reference(version = "1.0.0")
    IMemberBankCardBindService iMemberBankCardBindService;
    @Reference(version = "1.0.0")
    ICashService cashService;
    //商品接口
    @Reference(version = "1.0.0")
    private IProductSnapshotService iProductSnapshotService;
    //推送工具类
    @Autowired
    private PushUtil pushUtil;
    //短信接口
    @Autowired
    private SmsUtil smsUtil;
    /**
     * 订单和商品之间数据的处理
     */
    @Autowired
    OrderProductMapper orderProductMapper;
    @Autowired
    IOrderAddressService orderAddressService;

    @Override
    @Transactional
    public Result addOnlineOrder(AddMemberOrderOnlineDTO addMemberOrderOnlineDTO) {
        Result result;
        logger.info( "添加线上订单的参数:{}",JSON.toJSON( addMemberOrderOnlineDTO ) );
        String message = addMemberOrderOnlineDTO.validateForm();
        try {
            if (StringUtils.isEmpty( message )){
                List<MemberOrderOnlineDTO> list = addMemberOrderOnlineDTO.getMemberOrderOnlineDTOS();
                //校验和扣除库存
                checkStock(list);
                //订单数据新增
                Long subOrderId = addAllOnlineOrder(list);
                if (subOrderId==null){
                    result = ResultUtil.getResult( RespCode.Code.FAIL );
                }else {
                    result = ResultUtil.getResult( RespCode.Code.SUCCESS ,subOrderId);
                }

            }else {
                result = ResultUtil.getResult( RespCode.Code.FAIL );
                result.setMessage( message );
            }
        }catch (Exception e){
            logger.info( "添加线上订单异常，e={}",e );
            throw new OrderException( OrderExceptionEnum.ADD_ORDER_EXCEPTION );
        }

        return result;
    }

    /**
     * @author: 张霞
     * @description：获取线上订单列表(子订单)和具体订单的物流信息
     * @createDate: 10:05 2017/6/5
     * @param queryMemberOrderOnlineDTO
     * @param pagebean
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result getOnlineOrderVoList(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO, PageBean pagebean) {
        logger.info("线上订单列表查询入参，queryPurchaseOrderDTO={}", JSON.toJSON(queryMemberOrderOnlineDTO));
        Result result;
        if (pagebean != null) {
            pagebean.setPageSize(pagebean.getPageSize() == 0 ? PageConstant.pageSize : pagebean.getPageSize());
            pagebean.setPageNum(pagebean.getPageNum() == 0 ? PageConstant.pageNum : pagebean.getPageNum());
            PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
        }
        String startTime = queryMemberOrderOnlineDTO.getStartTime();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(startTime)) {
            queryMemberOrderOnlineDTO.setStartTime(startTime + " 00:00:00");
        }
        String endTime = queryMemberOrderOnlineDTO.getEndTime();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(endTime)) {
            queryMemberOrderOnlineDTO.setEndTime(endTime + " 23:59:59");
        }
        try {
            List<PhMemberSubOrderOnlinePageVO> phMemberSubOrderOnlinePageVOS = iMemberSubOrderOnlineMapper
                    .getMemberSubOrderOnlineListByPage(queryMemberOrderOnlineDTO);
            if (queryMemberOrderOnlineDTO.isShopping()) {
                // 用于封装商城页面所需商品数据
                for (PhMemberSubOrderOnlinePageVO vo : phMemberSubOrderOnlinePageVOS) {
                    vo.setPhMemberSubOrderOnlineProductVOS(getSubOrderProductsBySubOrderId(vo.getId()));
                }
            }
            PageInfo<PhMemberSubOrderOnlinePageVO> pageInfo = new PageInfo<PhMemberSubOrderOnlinePageVO>(
                    phMemberSubOrderOnlinePageVOS);
            result = ResultUtil.getResult(RespCode.Code.SUCCESS, phMemberSubOrderOnlinePageVOS, pageInfo.getTotal());
            logger.info("线上订单列表查询结果，result={}", JSON.toJSON(result));
            return result;
        } catch (Exception e) {
            logger.error("线上订单列表查询异常，{}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @author: 张霞
     * @description：通过id获取订单页面信息
     * @createDate: 11:47 2017/6/17
     * @param queryMemberOrderOnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result getOnlineOrderVoById(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO) {
        Result result;
        try {
            PhMemberSubOrderOnlinePageVO pageVO = new PhMemberSubOrderOnlinePageVO();
            PhMemberSubOrderOnline phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( queryMemberOrderOnlineDTO.getId() );
            BeanUtils.copyProperties( phMemberSubOrderOnline,pageVO );
            DateFormat df = new SimpleDateFormat( "yy-mm-dd HH:MM:SS" );
            pageVO.setCreateTime( df.format( phMemberSubOrderOnline.getCreateTime() ) );
            pageVO.setShippingType( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getShippingType() ) ) );
            pageVO.setStatus( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getStatus() ) ) );
            pageVO.setPayType( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getPayType() ) ) );
            pageVO.setTerminalUnit( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getTerminalUnit() ) ) );
            pageVO.setShippingType( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getShippingType() ) ) );

            if (queryMemberOrderOnlineDTO.isShopping()){ //用于封装商城查看物流页面数据
                Result memberResult = iMemberService.getMemberInfoById(phMemberSubOrderOnline.getCreaterId());//获取会员信息
                Object o = memberResult.getData();
                if (o instanceof Member){
                    // 会员信息
                    Member member = (Member) memberResult.getData();
                    pageVO.setShippingTelphone( member.getTelPhone() );
                    pageVO.setShippingName( member.getMemberName() );
                    pageVO.setShippingAddress( phMemberSubOrderOnline.getShippingAddress() );//提货地址、收货地址
                }else {
                    result = ResultUtil.getResult( RespCode.Code.FAIL );
                    result.setMessage( "订单有误，没有会员信息" );
                    return result;
                }
                if(phMemberSubOrderOnline.getShippingType()==0){
                    //只有当提货方式为“自提方式”，才有商户的信息
                    //商户信息
                    MerchantDTO merchantDTO = new MerchantDTO();
                    merchantDTO.setUserId( phMemberSubOrderOnline.getMerchantId() );
                    MerchantVO merchantVO = iMerchantService.getMerchantDetailById( merchantDTO );//获取商户信息
                    if (merchantVO==null){
                        result = ResultUtil.getResult( RespCode.Code.FAIL );
                        result.setMessage( "订单有误，为自提方式，没有提货点的商户信息" );
                        return result;
                    }
                    pageVO.setMerchantName( merchantVO.getMerchantName() );
                    pageVO.setMerchantTelphone( merchantVO.getTelPhone());
                    pageVO.setMerchantPersonName( merchantVO.getPersonName() );
                }
            }
            result = ResultUtil.getResult( RespCode.Code.SUCCESS,pageVO );
        }catch (Exception e){
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }

        return result;
    }

    /**
     * @author: 张霞
     * @description：取消线上订单
     * @createDate: 10:04 2017/6/5
     * @param updateOnlineOrderStatusDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result cancleOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO) {
        logger.info( "取消订单内容状态参数，updateOnlineOrderStatusDTO={}",JSON.toJSON( updateOnlineOrderStatusDTO ) );
        Result result;
        //验证参数信息
        String message = updateOnlineOrderStatusDTO.validateForm();
        if (StringUtils.isEmpty( message )){
            PhMemberSubOrderOnline phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( updateOnlineOrderStatusDTO.getOrderId() );
            if (phMemberSubOrderOnline.getStatus()==OrderStatusEnum.UNPAID_ORDER.getStatus()){
                try{
                    //返还订单中的商品数量
                    addProductNumToWarehouse(phMemberSubOrderOnline.getId());
                    result = updateOnlineOrder( updateOnlineOrderStatusDTO );;
                }catch (Exception e){
                    logger.info( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION.getMsg() );
                    throw new OrderException( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION );
                }
            }else {
                result = ResultUtil.getResult( OrderResultEnum.CANCEL_ORDER_UNABLE );
            }
        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( message );
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：线上订单申请退款（当订单处于代发货状态才能申请退款）
     * @createDate: 10:11 2017/6/5
     * @param addMemberOrderOnlineRefundDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result addRefundApplyOnlineOrder(AddMemberOrderOnlineRefundDTO addMemberOrderOnlineRefundDTO) {
        logger.info( "线上订单申请退款入参，addMemberOrderOnlineRefundDTO：{}",JSON.toJSONString( addMemberOrderOnlineRefundDTO ) );
        Result result;
        //验证参数信息
        String message = addMemberOrderOnlineRefundDTO.validateForm();
        if (StringUtils.isEmpty( message )){
            //验证订单是否可以退款
            PhMemberSubOrderOnline phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( addMemberOrderOnlineRefundDTO.getOrderId() );
            if (OrderOnlineStatusEnum.STATUS_TODO_SEND.getStatus()==phMemberSubOrderOnline.getStatus()){
                PhMemberSubOrderRefund phMemberSubOrderRefund = new PhMemberSubOrderRefund();
                phMemberSubOrderRefund.setSubOrderId( addMemberOrderOnlineRefundDTO.getOrderId());
                phMemberSubOrderRefund.setCreaterId( addMemberOrderOnlineRefundDTO.getCreaterId());
                phMemberSubOrderRefund.setAppliReason( addMemberOrderOnlineRefundDTO.getAppliReason() );
                phMemberSubOrderRefund.setAppliStatus( addMemberOrderOnlineRefundDTO.getStatus() );
                phMemberSubOrderRefund.setCreateTime( addMemberOrderOnlineRefundDTO.getCreateTime() );
                int resultInt = imemberSubOrderRefundMapper.insertSelective( phMemberSubOrderRefund );
                if (resultInt>0){
                    result = ResultUtil.getResult( RespCode.Code.SUCCESS );
                }else{
                    result = ResultUtil.getResult( OrderResultEnum.ORDER_REFUND_APPLY_FAIL );
                }
            }else {
                result = ResultUtil.getResult( OrderResultEnum.ORDER_OPERAION_INVALID );
            }
        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( message );
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：通过id获取线上订单详情
     * @createDate: 10:03 2017/6/5
     * @param queryMemberOrderOnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result getOnlineOrderDetailVO(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO) {
        Result result;
        PhMemberSubOrderOnlineDetailVO phMemberSubOrderOnlineDetailVO = new PhMemberSubOrderOnlineDetailVO();
        PhMemberSubOrderOnline phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( queryMemberOrderOnlineDTO.getId() );
        BeanUtils.copyProperties( phMemberSubOrderOnline,phMemberSubOrderOnlineDetailVO );
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUserId( phMemberSubOrderOnline.getMerchantId() );
        Result memberResult = iMemberService.getMemberInfoById(phMemberSubOrderOnline.getCreaterId());//获取会员信息
        Object o = memberResult.getData();
        if (o instanceof Member){
            // 会员信息
            Member member = (Member) memberResult.getData();
            phMemberSubOrderOnlineDetailVO.setMemberTelPhone( phMemberSubOrderOnline.getShippingTelphone() );//会员收货地址的电话
            phMemberSubOrderOnlineDetailVO.setMemberName( phMemberSubOrderOnline.getShippingName());//会员收货地址的联系人
            phMemberSubOrderOnlineDetailVO.setShippingAddress( phMemberSubOrderOnline.getShippingAddress() );//收货地址

        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( "订单有误，没有会员信息" );
            return result;
        }

        //组装页面数据
        if(phMemberSubOrderOnline.getShippingType()==0){
            //只有当提货方式为“自提方式”，才有商户的相关提货点信息
            //商户信息
            MerchantVO merchantVO = iMerchantService.getMerchantDetailById( merchantDTO );//获取商户信息
            if (merchantVO==null){
                result = ResultUtil.getResult( RespCode.Code.FAIL );
                result.setMessage( "订单有误，没有商户信息" );
                return result;
            }
            phMemberSubOrderOnlineDetailVO.setMerchantName( phMemberSubOrderOnline.getMerchantName() );//提货点的商户的门店
            phMemberSubOrderOnlineDetailVO.setCompanyName( merchantVO.getCompanyName() );//商户企业名称
            phMemberSubOrderOnlineDetailVO.setMerchantTelPhone( phMemberSubOrderOnline.getTakeGoodsTelphone());//提货点联系电话
            phMemberSubOrderOnlineDetailVO.setMerchantPersonName( phMemberSubOrderOnline.getTakeGoodsName());//提货点联系人
        }

        //供应商发货地址信息
        if(phMemberSubOrderOnline.getStatus()>=OrderOnlineStatusEnum.STATUS_TODO_RECEIVED.getStatus()&&phMemberSubOrderOnline.getStatus()!=OrderOnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus()){
            logger.info( "订单详情，查询发货地址参数：{}",phMemberSubOrderOnline.getSendAddressId() );
            try {
                Result wareHouseResult = iWarehouseAddressService.queryWareHouseById( phMemberSubOrderOnline.getSendAddressId() );
                if (wareHouseResult.isSuccess()&&wareHouseResult.getData()!=null){
                    WareHouseVO wareHouseVO = (WareHouseVO) wareHouseResult.getData();
                    phMemberSubOrderOnlineDetailVO.setSupplierTelPhone( wareHouseVO.getTelPhone() );
                    phMemberSubOrderOnlineDetailVO.setSupplierName( wareHouseVO.getSupplierName() );
                    phMemberSubOrderOnlineDetailVO.setSendAddressName( phMemberSubOrderOnline.getSendAddressName() );
                    phMemberSubOrderOnlineDetailVO.setSupplierPersonName( wareHouseVO.getContacts() );
                }else {
                    result = ResultUtil.getResult( RespCode.Code.FAIL );
                    result.setMessage( "订单有误，没有供应商信息" );
                    return result;
                }
            }catch (Exception e){
                result = ResultUtil.getResult( RespCode.Code.FAIL );
                result.setMessage( "订单有误，没有供应商信息" );
                return result;
            }
        }

        //订单信息(子订单)
        phMemberSubOrderOnlineDetailVO.setOrderNo( phMemberSubOrderOnline.getOrderNo() );
        phMemberSubOrderOnlineDetailVO.setStatus( Integer.parseInt( String.valueOf(  phMemberSubOrderOnline.getStatus() )) );
        phMemberSubOrderOnlineDetailVO.setCreateTime( phMemberSubOrderOnline.getCreateTime() );
        phMemberSubOrderOnlineDetailVO.setPayTime( phMemberSubOrderOnline.getPayTime() );
        phMemberSubOrderOnlineDetailVO.setSendTime( phMemberSubOrderOnline.getSendTime() );
        phMemberSubOrderOnlineDetailVO.setDoneTime( phMemberSubOrderOnline.getDoneTime() );
        phMemberSubOrderOnlineDetailVO.setCancelTime( phMemberSubOrderOnline.getCancelTime() );
        phMemberSubOrderOnlineDetailVO.setOrderMoney( phMemberSubOrderOnline.getOrderMoney() );
        phMemberSubOrderOnlineDetailVO.setProductMoney( phMemberSubOrderOnline.getProductMoney() );
        phMemberSubOrderOnlineDetailVO.setLogisticsMoney( phMemberSubOrderOnline.getLogisticsMoney() );
        phMemberSubOrderOnlineDetailVO.setShippingType( Integer.parseInt( String.valueOf( phMemberSubOrderOnline.getShippingType() ) ) );
        phMemberSubOrderOnlineDetailVO.setTakeGoodsAddress( phMemberSubOrderOnline.getTakeGoodsAddress() );
        //子订单中的商品
        phMemberSubOrderOnlineDetailVO.setPhMemberSubOrderOnlineProductVOS( getSubOrderProductsBySubOrderId(phMemberSubOrderOnline.getId()) );
        result = ResultUtil.getResult( RespCode.Code.SUCCESS,phMemberSubOrderOnlineDetailVO );
        return result;
    }

    /**
     * @author: 张霞
     * @description：线上订单发货
     * @createDate: 10:04 2017/6/5
     * @param updateOnlineOrderStatusDTO
     * @param sendOnlineOrderDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result sendOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO,SendOnlineOrderDTO sendOnlineOrderDTO) {

        logger.info( "线上子订单发货入参数：{}",JSON.toJSON( updateOnlineOrderStatusDTO ) );
        Result result;
        try {
            PhMemberSubOrderOnline orderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( updateOnlineOrderStatusDTO.getOrderId() );
            QueryMemberSubOrderRefundDTO queryMemberSubOrderRefundDTO = new QueryMemberSubOrderRefundDTO();
            queryMemberSubOrderRefundDTO.setSubOrderId( orderOnline.getId() );
            Map<String ,Object> checkResult = checkRefund(queryMemberSubOrderRefundDTO);
            boolean flagSending = (boolean) checkResult.get( "flag" );
            String flagDesc = (String) checkResult.get( "refundDesc" );
            //只有待发货状态才能发货
            if (orderOnline.getStatus()==1){
                //没有被申请为退款的，或者申请了退款然后处理为拒绝退款了的
                if (flagSending){
                    updateOnlineOrderStatusDTO.setContentDto( sendOnlineOrderDTO );
                    result = updateOnlineOrder( updateOnlineOrderStatusDTO );
                }else {
                    result = ResultUtil.getResult( RespCode.Code.FAIL );
                    result.setMessage( flagDesc );
                }
            }else {
                result = ResultUtil.getResult( RespCode.Code.FAIL );
                result.setCode( OrderResultEnum.ORDER_OPERAION_INVALID.getCode() );
                result.setMessage( OrderResultEnum.ORDER_OPERAION_INVALID.getMsg() );
            }
        }catch (Exception e){
            logger.info( "发货异常：{}",e );
            throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
        }
        return result;
    }



    /**
     * @author: 张霞
     * @description：后台审核线上订单申请退款
     * @createDate: 14:10 2017/6/5
     * @param refundDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result checkOnlineOrderRefund(CheckMemberSubOrderRefundDTO refundDTO) {
        Result result;
        logger.info( "线上订单申请退款审核入参，refundDTO：{}",JSON.toJSONString( refundDTO ) );
        String message = refundDTO.validateForm();
        if (StringUtils.isEmpty( message )){
            try {
                if (CheckResultEnum.SUCCESS.getCode()==refundDTO.getCheckResultStatus()){
                    //审核通过
                    /**
                     * 审核通过后，需要将线上子订单状态改为交易取消，退款记录表中状态为退款中，且将订单中的商品数量还回去
                     *
                     */

                    PhMemberSubOrderRefund phMemberSubOrderRefund = imemberSubOrderRefundMapper.selectByPrimaryKey( refundDTO.getId() );
                    PhMemberSubOrderOnline orderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( phMemberSubOrderRefund.getSubOrderId()  );
                    //更新退款记录订单中的状态
                    if (PayTypeEnum.PAY_TYPE_SCORE.getPayType()==orderOnline.getPayType()){ //积分支付直接退款完成
                        phMemberSubOrderRefund.setAppliStatus( OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDED.getAppliStatus() );
                    }else { //其他支付需要进行退款中
                        phMemberSubOrderRefund.setAppliStatus( OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDING.getAppliStatus() );
                    }
                    phMemberSubOrderRefund.setUpdaterId( refundDTO.getUpdaterId() );
                    phMemberSubOrderRefund.setUpdateTime( new Date(  ) );
                    phMemberSubOrderRefund.setCheckIsAccess( refundDTO.getCheckResultStatus() );
                    updateOnlineOrderRefund(phMemberSubOrderRefund);
                    //更新线上子订单状态
                    UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO = new UpdateOnlineOrderStatusDTO();
                    updateOnlineOrderStatusDTO.setOrderId( phMemberSubOrderRefund.getSubOrderId() );
                    updateOnlineOrderStatusDTO.setCurrentOrderStatus( OrderOnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus() );
                    updateOnlineOrderStatusDTO.setRoleType( RoleEnum.SUPPLIER.getCode() );
                    updateOnlineOrderStatusDTO.setUpdateContentType( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getType() );
                    updateOnlineOrderStatusDTO.setUpdaterId( refundDTO.getUpdaterId() );
                    updateOnlineOrder(updateOnlineOrderStatusDTO);
                    //退还商品
                    addProductNumToWarehouse(phMemberSubOrderRefund.getSubOrderId());
                    //退还金额---有待完善
                    result = refundMoneyByOrderOnline(orderOnline);
                }else if (CheckResultEnum.FAIL.getCode()==refundDTO.getCheckResultStatus()){
                    //审核未通过
                    //更新退款记录订单中的状态
                    PhMemberSubOrderRefund phMemberSubOrderRefund = imemberSubOrderRefundMapper.selectByPrimaryKey( refundDTO.getId() );
                    phMemberSubOrderRefund.setAppliStatus( OrderOnlineAppliStatusEnum.APPLISTATUS_REFUND_REFUSE.getAppliStatus() );
                    phMemberSubOrderRefund.setUpdaterId( refundDTO.getUpdaterId() );
                    phMemberSubOrderRefund.setUpdateTime( new Date(  ) );
                    phMemberSubOrderRefund.setRejectedReason( refundDTO.getRejectedReason() );
                    phMemberSubOrderRefund.setTelphone( refundDTO.getTelPhone() );
                    phMemberSubOrderRefund.setCheckIsAccess( refundDTO.getCheckResultStatus() );
                    updateOnlineOrderRefund(phMemberSubOrderRefund);
                }else {
                    result = ResultUtil.getResult( OrderResultEnum.ORDER_OPERAION_INVALID );
                    return result;
                }
                result = ResultUtil.getResult( RespCode.Code.SUCCESS );
            }catch (Exception e){
                logger.info( "审核退款失败" );
                throw new OrderException( RespCode.Code.FAIL );
            }
        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( message );
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：通过id获取申请退款记录
     * @createDate: 15:39 2017/6/5
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result getOnlineOrderRefundById(QueryMemberSubOrderRefundDTO qdto) {
        Result result;
        try {
            PhMemberSubOrderRefund phMemberSubOrderRefund = imemberSubOrderRefundMapper.selectByPrimaryKey( qdto.getId() );
            result = ResultUtil.getResult( RespCode.Code.SUCCESS,phMemberSubOrderRefund );
        }catch (Exception e){
            logger.info( "查看申请退款记录失败" );
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }

    /**
     * @author: 张霞
     * @description：获取线上订单申请退款列表
     * @createDate: 18:42 2017/6/19
     * @param qdto
     * @param pagebean
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result getOnlineOrderRefundVoList(QueryMemberSubOrderRefundDTO qdto, PageBean pagebean) {
        Result result;
        logger.info("线上订单列表查询入参，qdto={}", JSON.toJSON(qdto));
        if (pagebean != null){
            pagebean.setPageSize(pagebean.getPageSize() == 0 ? PageConstant.pageSize : pagebean.getPageSize());
            pagebean.setPageNum(pagebean.getPageNum() == 0 ? PageConstant.pageNum : pagebean.getPageNum());
            PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
        }
        if (!(StringUtils.isEmpty( qdto.getStartTime() )&& StringUtils.isEmpty( qdto.getEndTime() ))){
            if (qdto.getStartTime().equals( qdto.getEndTime() )){
                qdto.setStartTime( qdto.getStartTime()+" 00:00:00" );
                qdto.setEndTime( qdto.getEndTime()+" 23:59:59" );
            }
        }
        try {
            List<PhMemberSubOrderRefundPageVO> orderRefundPageVOList = imemberSubOrderRefundMapper.getMemberSubOrderOnlineRrfundListByPage( qdto );
            result = ResultUtil.getResult( RespCode.Code.SUCCESS,orderRefundPageVOList );
            return result;
        }catch (Exception e){
            logger.info( "查询线上订单申请退款列表异常" );
            result = ResultUtil.getResult( RespCode.Code.FAIL );

        }
        return result;
    }

    /**
     * @author: 张霞
     * @description：通过线上订单id进行确认收货
     * @createDate: 18:03 2017/6/26
     * @param udto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @Override
    public Result checkOrderShipping(UpdateOnlineOrderStatusDTO udto) {

        logger.info( "确认订单内容状态参数，updateOnlineOrderStatusDTO={}",JSON.toJSON( udto ) );
        Result result;
        //验证参数信息
        String message = udto.validateForm();
        if (StringUtils.isEmpty( message )){
            PhMemberSubOrderOnline phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( udto.getOrderId() );
            if (phMemberSubOrderOnline.getStatus()==OrderStatusEnum.RECEIVED_ORDER.getStatus()){
                try{
                    result = updateOnlineOrder( udto );;
                }catch (Exception e){
                    logger.info( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION.getMsg() );
                    throw new OrderException( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION );
                }
            }else {
                result = ResultUtil.getResult( OrderResultEnum.CANCEL_ORDER_UNABLE );
            }
        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( message );
        }
        return result;
    }

    /**
     * @author: 张霞
     * @description：通过线上订单子订单id获取订单的商品
     * @createDate: 14:27 2017/6/21
     * @param subOrderId
     * @return: java.util.List<cm.ph.shopping.facade.order.vo.PhMemberSubOrderOnlineProductVO>
     * @version: 2.1
     */
    private List<PhMemberSubOrderOnlineProductVO> getSubOrderProductsBySubOrderId(Long subOrderId) {
        List<PhMemberSubOrderOnlineProducts> phMemberSubOrderOnlineProductsList = iMemberSubOrderOnlineProductsMapper
                .getMemberSubOrderProductBySubOrderId(subOrderId);
        List<PhMemberSubOrderOnlineProductVO> phMemberSubOrderOnlineProductVOS = new ArrayList<>();
        for (PhMemberSubOrderOnlineProducts p : phMemberSubOrderOnlineProductsList) {
            PhMemberSubOrderOnlineProductVO phMemberSubOrderOnlineProductVO = new PhMemberSubOrderOnlineProductVO();
            phMemberSubOrderOnlineProductVO.setProductName(p.getProductName());
            phMemberSubOrderOnlineProductVO.setProductNum(p.getProductNum());
            phMemberSubOrderOnlineProductVO.setRetailPrice(p.getRetailPrice());
            phMemberSubOrderOnlineProductVO.setSkuFreight(p.getSkuFreight());
            phMemberSubOrderOnlineProductVO.setSkuName(p.getSkuName());
            phMemberSubOrderOnlineProductVO.setTotalMoney(p.getTotalFreight() + p.getTotalRetailPrice());
            phMemberSubOrderOnlineProductVO.setProductImageUrl(p.getProductImageUrl());
            phMemberSubOrderOnlineProductVOS.add(phMemberSubOrderOnlineProductVO);
        }
        return phMemberSubOrderOnlineProductVOS;
    }
    /**
     * @author: 张霞
     * @description：线上订单退款返回金额
     * @createDate: 18:49 2017/6/20
     * @param subOrderOnline
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    private Result refundMoneyByOrderOnline(PhMemberSubOrderOnline subOrderOnline){
        Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
        if (subOrderOnline.getPayType() == PayTypeEnum.PAY_TYPE_SCORE.getPayType()) {
            //积分支付--退还到会员积分中
            long score = MoneyTransUtil.transMultiDouble( subOrderOnline.getOrderMoney() )/10000;
            try {
                iScoreService.memberScoreTrade( subOrderOnline.getCreaterId(),
                        TransCodeEnum.ONLINE_ORDER_REFUND,score, subOrderOnline.getOrderNo() ,0);
            } catch (Exception e) {
                logger.info( "更新会员积分和记入总流水失败" );
                throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
            }
        }else if (subOrderOnline.getPayType() == PayTypeEnum.PAY_TYPE_ALIPAY.getPayType()){
            //支付宝支付--退还到支付宝账号中
            AlipayRefundDTO ard = new AlipayRefundDTO();
            ard.setAmount( String.valueOf( MoneyTransUtil.transDivisDouble( Long.valueOf( String.valueOf( subOrderOnline.getOrderMoney() ) ) ) ) );//订单金额
            ard.setReason( subOrderOnline.getCancelReason() );//申请原因
            ard.setOut_trade_no( subOrderOnline.getOrderNo() );//交易订单号
            result = cashService.alipayRefund( ard );//退还支付宝结果
            if (!result.isSuccess()){
                logger.info( "退还到支付宝账户失败" );
                throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
            }
        }else if (subOrderOnline.getPayType()==PayTypeEnum.PAY_TYPE_YILIANPAY.getPayType()){
            //易联支付--需要用银联代付进行退款
            //查询会员银行卡信息
            Result bankCard = iMemberBankCardBindService.selectBindMemberBankCardByUserId( subOrderOnline.getCreaterId() );
            logger.info("查询银行卡信息返回值，bankCard={}", JSON.toJSONString(bankCard));
            if (null !=bankCard && bankCard.isSuccess()) {
                MemberBankCardBindInfoVO vo = (MemberBankCardBindInfoVO) bankCard.getData();
                DefrayDTO defrayDTO = new DefrayDTO(String.valueOf( subOrderOnline.getId() ), UniqueUtils.getBathNo(), vo.getBankCardNo(), vo.getOwnerName(),
                        MoneyTransUtil.transDivis(new BigDecimal( subOrderOnline.getOrderMoney() )).toString(), vo.getBankName(),
                        subOrderOnline.getOrderNo());
                logger.info("调用银行卡归还金额接口,defrayDTO={}", JSON.toJSONString(defrayDTO));
                result = cashService.defray(defrayDTO);
                logger.info("调用银行卡归还金额接口,result={}", JSON.toJSONString(result));
            } else {
                throw new RuntimeException("查询银行卡信息失败");
            }
        }else {
            throw new RuntimeException("订单支付类型有误,支付类型为:"+Byte.toString( subOrderOnline.getPayType() ));
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：发货前查看订单是否处于退款状态情况
     * @createDate: 14:32 2017/6/20
     * @param qdto
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @version: 2.1
     */
    private Map<String,Object> checkRefund(QueryMemberSubOrderRefundDTO qdto){

        Map<String,Object> resultMap = new HashMap<>(  );
        List<PhMemberSubOrderRefundPageVO> list = imemberSubOrderRefundMapper.getMemberSubOrderOnlineRrfundListByPage( qdto );
        if (CollectionUtils.isEmpty( list )){
            resultMap.put( "flag",true );
            resultMap.put( "refundDesc","可以发货" );
            return resultMap;
        }else {
            PhMemberSubOrderRefundPageVO orderRefundPageVO = list.get( 0 );
            if (orderRefundPageVO.getAppliStatus()==OrderOnlineAppliStatusEnum.APPLISTATUS_REFUND_REFUSE.getAppliStatus()){
                //拒绝退款
                resultMap.put( "flag",true );
                resultMap.put( "refundDesc","可以发货" );
                return resultMap;
            }else {
                resultMap.put( "flag",false );
                if (orderRefundPageVO.getAppliStatus()==OrderOnlineAppliStatusEnum.APPLISTATUS_AUDITING.getAppliStatus()){
                    resultMap.put( "refundDesc",OrderOnlineAppliStatusEnum.APPLISTATUS_AUDITING.getDesc() );
                }else if (orderRefundPageVO.getAppliStatus()==OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDING.getAppliStatus()){
                    resultMap.put( "refundDesc",OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDING.getDesc() );
                }else if (orderRefundPageVO.getAppliStatus()==OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDED.getAppliStatus()){
                    resultMap.put( "refundDesc",OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDED.getDesc() );
                }
                return resultMap;
            }
        }
    }

    /**
     * @methodname checkStock 的描述：库存校验
     * @param list
     * @return void
     * @author 郑朋
     * @create 2017/5/16
     */
    private void checkStock(List<MemberOrderOnlineDTO> list) {
        //子订单集合
        for (MemberOrderOnlineDTO memberOrderOnlineDTO : list) {
            //商品集合
            for (MemberProductDTO memberProductDTO : memberOrderOnlineDTO.getProductDTOS()) {
                int count = 0;
                //sku集合
                for (MemberProductSkuDTO memberProductSkuDTO : memberProductDTO.getProductSkuDTOS()) {
                    if (!reduceSkuStock(memberProductSkuDTO.getSkuId(), memberProductSkuDTO.getSkuNum())) {
                        String message =  "新增订单失败，商品编号为：" + memberProductDTO.getProductId() + ",sku编号为：" +
                                memberProductSkuDTO.getSkuId() +"库存不足，请重新选择";
                        throw new RuntimeException(message);
                    }
                    count += memberProductSkuDTO.getSkuNum();
                }
                //扣除商品总量和商品销售数量
                if (count > 0) {
                    Long productId = memberProductDTO.getProductId();
                    int resultInt = orderProductMapper.reduceProductCount(productId,count);
                    logger.info( "扣除商品总量和添加销售数量结果：{}",resultInt );
                    logger.info( String.valueOf( resultInt<0 ) );
                    if (resultInt<0) {
                        String message =  "新增订单失败，编号为：" + productId + "的商品目前库存不足，请重新选择";
                        throw new RuntimeException(message);
                    }
                }

            }
        }
    }
    /**
     * @author: 张霞
     * @description：更新线上订单申请退款的记录表中的状态
     * @createDate: 14:56 2017/6/5
     * @param phMemberSubOrderRefund
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    private Result updateOnlineOrderRefund(PhMemberSubOrderRefund phMemberSubOrderRefund){

        Result result;
        int resultInt = imemberSubOrderRefundMapper.updateByPrimaryKeySelective( phMemberSubOrderRefund );
        if (resultInt>0){
            result = ResultUtil.getResult( RespCode.Code.SUCCESS );
        }else {
            logger.info( "更新线上订单退款记录失败" );
            throw new OrderException( OrderExceptionEnum.UPDATE_REFUND_ONLINEORDER_EXCEPTION );
        }
        return result;
    }

    /**
     * @author: 张霞
     * @description：更新线上订单相关内容和记录相关变更流水
     * @createDate: 20:07 2017/6/2
     * @param updateOnlineOrderStatusDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    private Result updateOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO) {
        try{
            logger.info( "更新订单内容状态参数，updateOnlineOrderStatusDTO={}",JSON.toJSON( updateOnlineOrderStatusDTO ) );
            Result result;
            //验证参数信息
            String message = updateOnlineOrderStatusDTO.validateForm();
            if (StringUtils.isEmpty( message )){
                //验证参数通过
                //更新订单内容
                PhMemberSubOrderOnline phMemberSubOrderOnline = new PhMemberSubOrderOnline();
                phMemberSubOrderOnline = iMemberSubOrderOnlineMapper.selectByPrimaryKey( updateOnlineOrderStatusDTO.getOrderId() );
                phMemberSubOrderOnline.setUpdateTime( new Date() );
                phMemberSubOrderOnline.setUpdaterId( updateOnlineOrderStatusDTO.getUpdaterId() );
                phMemberSubOrderOnline.setUpdaterRoleType( updateOnlineOrderStatusDTO.getRoleType() );
                //是否需要更新订单具体内容
                if (updateOnlineOrderStatusDTO.getCurrentOrderStatus()==OrderStatusEnum.RECEIVED_ORDER.getStatus()){
                    //更新订单为代发货状态，需要填充额外信息：发货相关信息(物流、仓库地址)
                    if (updateOnlineOrderStatusDTO.getContentDto()!=null && updateOnlineOrderStatusDTO.getContentDto() instanceof SendOnlineOrderDTO)
                    {
                        SendOnlineOrderDTO sendOnlineOrderDTO = (SendOnlineOrderDTO) updateOnlineOrderStatusDTO.getContentDto();
                        message = updateOnlineOrderStatusDTO.validateForm();
                        if (StringUtils.isEmpty( message )){
                            phMemberSubOrderOnline.setSendTime( new Date(  ) );
                            phMemberSubOrderOnline.setSendAddressId( sendOnlineOrderDTO.getSendAddressId() );
                            phMemberSubOrderOnline.setSendAddressName( sendOnlineOrderDTO.getSendAddressName() );
                            phMemberSubOrderOnline.setLogisticsId( sendOnlineOrderDTO.getLogisticsId() );
                            phMemberSubOrderOnline.setLogisticsName( sendOnlineOrderDTO.getLogisticsName() );
                            phMemberSubOrderOnline.setLogisticsNo( sendOnlineOrderDTO.getLogisticsNo() );
                        }else {
                            //参数验证失败
                            result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                            result.setMessage(message);
                            return result;
                        }

                    }
                }

                //更新记录内容
                PhMemberSubOrderOnlineRecord phMemberSubOrderOnlineRecord = new PhMemberSubOrderOnlineRecord();
                if (OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getType()==updateOnlineOrderStatusDTO.getUpdateContentType()){
                    logger.info( "更新内容：{}",OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getDesc() );
                    //更新记录内容
                    phMemberSubOrderOnlineRecord.setStatusBefore(Byte.toString( phMemberSubOrderOnline.getStatus() ));
                    phMemberSubOrderOnlineRecord.setUpdateContent( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getDesc() );
                    //更新订单状态
                    phMemberSubOrderOnline.setStatus( updateOnlineOrderStatusDTO.getCurrentOrderStatus() );
                    if (OrderOnlineStatusEnum.STATUS_TODO_SEND.getStatus()==updateOnlineOrderStatusDTO.getCurrentOrderStatus()){//待发货
                        phMemberSubOrderOnline.setPayTime( new Date(  ) );
                    }else if(OrderOnlineStatusEnum.STATUS_TODO_RECEIVED.getStatus()==updateOnlineOrderStatusDTO.getCurrentOrderStatus()){//待收货
                        phMemberSubOrderOnline.setSendTime( new Date(  ) );
                    }else if(OrderOnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus()==updateOnlineOrderStatusDTO.getCurrentOrderStatus()){//交易取消
                        phMemberSubOrderOnline.setCancelTime( new Date(  ) );
                    }else if (OrderOnlineStatusEnum.STATUS_DONE_ORDER.getStatus()==updateOnlineOrderStatusDTO.getCurrentOrderStatus()){//交易完成
                        phMemberSubOrderOnline.setDoneTime( new Date(  ) );
                    }
                }else if (OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISBACKSCORE.getType()==updateOnlineOrderStatusDTO.getUpdateContentType()){
                    //更新记录内容
                    phMemberSubOrderOnlineRecord.setStatusBefore(Byte.toString( phMemberSubOrderOnline.getIsBackScore() ));
                    phMemberSubOrderOnlineRecord.setUpdateContent( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISBACKSCORE.getDesc() );
                    //更新订单是否返回积分
                    logger.info( "更新内容：{}",OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISBACKSCORE.getDesc() );
                    phMemberSubOrderOnline.setIsBackScore( updateOnlineOrderStatusDTO.getCurrentOrderStatus() );
                }else if (OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISSETTLE.getType()==updateOnlineOrderStatusDTO.getUpdateContentType()){
                    //更新记录内容
                    phMemberSubOrderOnlineRecord.setStatusBefore(Byte.toString( phMemberSubOrderOnline.getIsSettle() ));
                    phMemberSubOrderOnlineRecord.setUpdateContent( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISSETTLE.getDesc() );
                    //更新订单是否计算(是否分润)
                    logger.info( "更新内容：{}",OrderOnlineUpdateContentEnum.ONLINE_UPDATE_ISSETTLE.getDesc() );
                    phMemberSubOrderOnline.setIsSettle( updateOnlineOrderStatusDTO.getCurrentOrderStatus() );
                }else {
                    logger.info( "更新订单参数内容有误" );
                    throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
                }
                //更新内容
                int resultInt = iMemberSubOrderOnlineMapper.updateByPrimaryKeySelective( phMemberSubOrderOnline );//更新订单
                if (resultInt !=1){
                    logger.info("更新子订单失败");
                    throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
                }else {
                    //插入更新记录
                    resultInt = addMemberSubOrderOnlineRecord(updateOnlineOrderStatusDTO,phMemberSubOrderOnlineRecord);
                    if (resultInt != 1){
                        logger.info("更新订单记录数据失败");
                        throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
                    }else {
                        logger.info( "更新订单成功" );
                        result = ResultUtil.getResult( OrderExceptionEnum.Code.SUCCESS );
                    }
                }
            }else {
                //参数验证失败
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            return result;
        }catch (Exception e){
            logger.info( "线上订单更新订单状态异常，{}",e );
            throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
        }
    }
    /**
     * @author: 张霞
     * @description：取消订单、退款订单时退还商品到仓库中
     * @createDate: 9:36 2017/6/2
     * @param subOrderId
     * @return: void
     * @version: 2.1
     */
    private void addProductNumToWarehouse(Long subOrderId){
        //根据子订单id查询商品信息
        List<PhMemberSubOrderOnlineProducts> phMemberSubOrderOnlineProductsList = iMemberSubOrderOnlineProductsMapper.getMemberSubOrderProductBySubOrderId( subOrderId );
        addProductNumToWarehouse(phMemberSubOrderOnlineProductsList);
    }
    /**
     * @author: 张霞
     * @description：归还商品到库存中
     * @createDate: 18:37 2017/6/28
     * @param phMemberSubOrderOnlineProductsList
     * @return: void
     * @version: 2.1
     */
    private void addProductNumToWarehouse(List<PhMemberSubOrderOnlineProducts> phMemberSubOrderOnlineProductsList){

        //归还商品数量
        Long currentSkuId = null;//查找最新的规格id
        try{
            if(CollectionUtils.isNotEmpty( phMemberSubOrderOnlineProductsList )){
                for (PhMemberSubOrderOnlineProducts p :
                        phMemberSubOrderOnlineProductsList) {
                    currentSkuId = orderProductMapper.selectCurrentProductSkuId( p.getProductId(),String.valueOf( p.getSpecificationValIds() ) );
                    //先添加sku数量后，再加上商品总数量
                    if (orderProductMapper.addSkuCount( currentSkuId,p.getProductNum() )>0){
                        orderProductMapper.addProductCount( p.getProductId(),p.getProductNum() );
                    }
                }
            }
        }catch (Exception e){
            logger.info( "归还(添加)商品异常" );
            throw new OrderException( OrderResultEnum.ORDER_ADD_PRODUCT_ERROR );
        }
    }


    /**
     * @methodname reduceSkuStock 的描述：库存扣除（减去商品数量）
     * @param skuId
     * @param skuNum
     * @return boolean
     * @author 郑朋
     * @create 2017/5/16
     */
    private boolean reduceSkuStock(Long skuId, Integer skuNum) {
        //如果商品sku数量不足，则订单不能生成，返回提示信息
        boolean flag = false;
        int num = orderProductMapper.reduceSkuCount(skuId, skuNum);
        if (num >= 1) {
            flag = true;
        }
        return flag;
    }
    /**
     * @author: 张霞
     * @description：创建线上订单后需要对商品仓库进行减仓操作
     * @createDate: 14:22 2017/6/2
     * @param subOrderId
     * @return: void
     * @version: 2.1
     */
    private void subdectionProductNumFromWarehouse(Long subOrderId){
        //根据子订单id查询商品信息
        List<PhMemberSubOrderOnlineProducts> phMemberSubOrderOnlineProductsList = iMemberSubOrderOnlineProductsMapper.getMemberSubOrderProductBySubOrderId( subOrderId );
        //减掉线上订单中商品数量
        Long currentSkuId = null;//查找最新的规格id
        try{
            if(CollectionUtils.isNotEmpty( phMemberSubOrderOnlineProductsList )){
                for (PhMemberSubOrderOnlineProducts p :
                        phMemberSubOrderOnlineProductsList) {
                    currentSkuId = orderProductMapper.selectCurrentProductSkuId( p.getProductId(),String.valueOf( p.getSpecificationValIds() ) );
                    //先添加sku数量后，再加上商品总数量
                    if (orderProductMapper.addSkuCount( currentSkuId,p.getProductNum() )>0){
                        orderProductMapper.addProductCount( p.getProductId(),p.getProductNum() );
                    }
                }
            }
        }catch (Exception e){
            logger.info( "减少商品异常" );
            throw new OrderException( OrderResultEnum.ORDER_SUBDUCTION_PRODUCT_ERROR );
        }
    }
    /**
     * @author: 张霞
     * @description：添加更新订单状态记录
     * @createDate: 17:40 2017/6/1
     * @param updateOnlineOrderStatusDTO
     * @return: int
     * @version: 2.1
     */
    private int addMemberSubOrderOnlineRecord(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO,PhMemberSubOrderOnlineRecord phMemberSubOrderOnlineRecord){
        phMemberSubOrderOnlineRecord.setCreaterId( updateOnlineOrderStatusDTO.getUpdaterId() );
        phMemberSubOrderOnlineRecord.setCreaterRoleType( updateOnlineOrderStatusDTO.getRoleType() );
        phMemberSubOrderOnlineRecord.setCreateTime( new Date(  ) );
        phMemberSubOrderOnlineRecord.setStatusAfter(Byte.toString( updateOnlineOrderStatusDTO.getCurrentOrderStatus() ));
        phMemberSubOrderOnlineRecord.setSubOrderId( updateOnlineOrderStatusDTO.getOrderId() );
        return iMemberSubOrderOnlineRecordMapper.insertSelective( phMemberSubOrderOnlineRecord );//插入订单更新记录
    }

    /**
     * @author: 张霞
     * @description：添加线上订单相关表数据
     * @createDate: 16:31 2017/6/5
     * @param subOnlineList
     * @return: void
     * @version: 2.1
     */
    private Long addAllOnlineOrder(List<MemberOrderOnlineDTO> subOnlineList){
        Long subOrderId=null;
        //封装所有订单数据
        Map<String,Object> map = dataEncapsulation( subOnlineList );
        logger.info( "线上订单封装后的数据，map={}",JSON.toJSON( map ) );
        //新增线上主订单
        PhMemberMainOrderOnline phMemberMainOrderOnline = (PhMemberMainOrderOnline) map.get( "mainOrder" );
        int i = iMemberMainOrderOnlineMapper.insertUseGeneratedKeys( phMemberMainOrderOnline );
        if (i==1){
            Long mainOrderId = phMemberMainOrderOnline.getId();
            List<PhMemberSubOrderOnline> phMemberSubOrderOnlines = (List<PhMemberSubOrderOnline>) map.get( "subOrder" );
            for (PhMemberSubOrderOnline phMemberSubOrderOnline:phMemberSubOrderOnlines) {
                //新增线上子订单
                phMemberSubOrderOnline.setMainOrderId( mainOrderId );
                logger.info( "插入子订单内容：{}",JSON.toJSON( phMemberSubOrderOnline ));
                iMemberSubOrderOnlineMapper.insertUseGeneratedKeys(phMemberSubOrderOnline  );
                subOrderId = phMemberSubOrderOnline.getId();
                //新增线上子订单中商品的数量
                List<PhMemberSubOrderOnlineProducts> products = (List<PhMemberSubOrderOnlineProducts>) map.get( phMemberSubOrderOnline.getOrderNo() );
                if (subOrderId ==null ){
                    //减少商品的数量需要加回去
                    addProductNumToWarehouse(products);
                    logger.info( "新增子订单失败" );
                    throw new OrderException( OrderExceptionEnum.ADD_ORDER_EXCEPTION );
                }
                for (PhMemberSubOrderOnlineProducts phMemberSubOrderOnlineProducts :
                        products) {
                    phMemberSubOrderOnlineProducts.setSubOrderId( subOrderId );
                    phMemberSubOrderOnlineProducts.setCreateTime( new Date(  ) );
                    int productId = iMemberSubOrderOnlineProductsMapper.insertUseGeneratedKeys( phMemberSubOrderOnlineProducts );
                    if (productId !=1){
                        logger.info( "新增子订单的商品信息失败" );
                        throw new OrderException( OrderExceptionEnum.ADD_ORDER_EXCEPTION );
                    }
                }
            }
        }else {
            logger.info( "新增主订单失败" );
            throw new OrderException( OrderExceptionEnum.ADD_ORDER_EXCEPTION );
        }
        return subOrderId;
    }
    /**
     * @author: 张霞
     * @description：线上订单数据封装
     * @createDate: 16:54 2017/6/6
     * @param list
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @version: 2.1
     */
    private Map<String, Object> dataEncapsulation(List<MemberOrderOnlineDTO> list) {

        Map<String, Object> map = new HashMap<>();
        ProductImage productImage;
        //创建主订单号
        String mainOrderNo = OrderUtil.createOrderCode(OrderBizCode.MAIN_MEMBER_ORDER);
        BigDecimal orderTotalCost,  //一个子订单总费用（商品+物流）
                orderProductTotalCost, //一个子订单商品总费用
                orderTotalFreight,  //一个子订单物流费用
                orderTotalSettlement,  //一个子订单所有结算总费用
                productTotalCost,  //一种商品总费用
                productFreight, //一种商品的物流费用
                productSettlement, //一种商品的结算费用
                skuTotalCost,       //一种sku的总价格
                skuTotalFreight,   //一种sku的总物流费
                skuTotalProduct,  //一种sku的总商品费用
                skuTotalSettlement,//一个sku的总商品结算价格
                mainOrderCost,  //总订单费用（商品+物流）
                mainOrderFreigtCost,//总订单物流费用
                mainOrderProductCost;//总订单商品费用

        List<MemberProductDTO> memberProductDTOS; //子订单对应的商品集合
        List<MemberProductSkuDTO> memberProductSkuDTOS; //商品对应的sku集合
        List<ProductSku> productSkuList; //商品对应的sku信息
        String subOrderNo;  //子订单号
        mainOrderCost = new BigDecimal(0);
        mainOrderFreigtCost = new BigDecimal( 0 );
        mainOrderProductCost = new BigDecimal(0);

        //子订单新增集合
        List<PhMemberSubOrderOnline> memberSubOrderOnlines = new ArrayList<>();
        //子订单商品集合
        List<PhMemberSubOrderOnlineProducts> memberSubOrderOnlineProductss = new ArrayList<>();
        //一类商品的进货数量
        int productNum;
        PhMemberSubOrderOnlineProducts memberSubOrderOnlineProducts;
        PhOrderAddressVO phOrderAddressVO;
        //循环订单信息
        for (MemberOrderOnlineDTO memberOrderOnlineDTO : list) {
            Long shippingAddressId = memberOrderOnlineDTO.getShippingAddressId();
            Result shippingResult = orderAddressService.getAddressDetailById( shippingAddressId );
            if (shippingResult.isSuccess()){
                //填充会员收货信息
                phOrderAddressVO = (PhOrderAddressVO) shippingResult.getData();
                memberOrderOnlineDTO.setShippingProvinceId( phOrderAddressVO.getProvinceId() );//省id
                memberOrderOnlineDTO.setShippingCityId( phOrderAddressVO.getCityId() );//市id
                memberOrderOnlineDTO.setShippingCountyId( phOrderAddressVO.getAreaId() );//县id
                memberOrderOnlineDTO.setShippingTownId( phOrderAddressVO.getTownId() );//区ic
                memberOrderOnlineDTO.setShippingAddressDetail( phOrderAddressVO.getAddress() );//详情地址
                StringBuilder sb = new StringBuilder();
                String proviceName = StringUtils.isEmpty( phOrderAddressVO.getProvinceName() )?"":phOrderAddressVO.getProvinceName();
                String cityName = StringUtils.isEmpty(phOrderAddressVO.getCityName() )?"":phOrderAddressVO.getCityName();
                String areaName = StringUtils.isEmpty( phOrderAddressVO.getAreaName() )?"":phOrderAddressVO.getAreaName();
                String townName = StringUtils.isEmpty( phOrderAddressVO.getTownName() )?"":phOrderAddressVO.getTownName();
                String  shippingAddress = sb.append( proviceName ).append( cityName ).append( areaName ).append( townName ).append( phOrderAddressVO.getAddress() ).toString();
                memberOrderOnlineDTO.setShippingAddress(shippingAddress);//完整地址
                memberOrderOnlineDTO.setPositionId( phOrderAddressVO.getPositionId() );//position表中的id
                memberOrderOnlineDTO.setShippingName( phOrderAddressVO.getContacts() );//收获人名称
                memberOrderOnlineDTO.setShippingTelphone( phOrderAddressVO.getTelPhone());//收货人电话
            }else {
                logger.info( "由于线上订单创建时，没有收获地址，不能创建成功" );
                new RuntimeException( "由于线上订单创建时，没有收获地址，不能创建成功" );
            }

            if (OrderOnlineShippingTypeEnum.SHIPPING_TYPE_BYSELF.getShippingType()==memberOrderOnlineDTO.getShippingType()){
                //自提方式，需要获取商户设置为自提地址的相关信息
                TakePointDTO takePointDTO = new TakePointDTO();
                takePointDTO.setUserId( memberOrderOnlineDTO.getMerchantId() );
                Result resultTakePoints = iWarehouseAddressService.queryTakePoints( takePointDTO );
                if (resultTakePoints.isSuccess()){
                    List<OnlineTakePointVO> onlineTakePointVOS = (List<OnlineTakePointVO>) resultTakePoints.getData();
                    if (CollectionUtils.isNotEmpty( onlineTakePointVOS )){
                        OnlineTakePointVO onlineTakePointVO = onlineTakePointVOS.get( 0 );
                        memberOrderOnlineDTO.setMerchantName( onlineTakePointVO.getMerchantName() );//提货点商户店名称
                        memberOrderOnlineDTO.setTakeGoodsName( onlineTakePointVO.getContacts() );//提货地址联系人
                        memberOrderOnlineDTO.setTakeGoodsAddress( onlineTakePointVO.getAddress() );//提货地址
                        memberOrderOnlineDTO.setTakeGoodsTelphone( onlineTakePointVO.getTelPhone()+" "+onlineTakePointVO.getPhoneNo() );//提货地址的联系电话
                    }else {
                        logger.info( "由于线上订单创建时，自提方式时，商户没有自提地址，不能创建成功" );
                        new RuntimeException( "由于线上订单创建时，自提方式时，商户没有自提地址，不能创建成功" );
                    }
                }
            }

            //商品信息
            memberProductDTOS = memberOrderOnlineDTO.getProductDTOS();
            //一个供应商生成一个订单
            orderTotalFreight = new BigDecimal(0);
            orderTotalCost = new BigDecimal(0);
            orderProductTotalCost = new BigDecimal(0);
            orderTotalSettlement = new BigDecimal( 0 );

            //创建子订单号
            subOrderNo = OrderUtil.createOrderCode(OrderBizCode.SUB_MEMBER_ORDER);
            //同一个供应商下的所有商品
            for (MemberProductDTO memberProductDTO : memberProductDTOS) {
                productFreight = new BigDecimal(0);
                productTotalCost = new BigDecimal(0);
                productSettlement = new BigDecimal( 0 );
                productNum = 0;
                memberProductSkuDTOS = memberProductDTO.getProductSkuDTOS();
                ProductSnapshotDTO productSnapshotDTO = new ProductSnapshotDTO();
                productSnapshotDTO.setProductId( memberProductDTO.getProductId() );
//                productSnapshot.setId( 30l );
                ProductSnapshotVO productSnapshotVO = iProductSnapshotService.selectProductSnapshotByproductId( productSnapshotDTO );
                //通过productId查询商品所有sku信息，如果查询失败，订单生成失败
                productSkuList = getProductSku(memberProductDTO.getProductId());
                //从商品模块获取sku的信息在和页面传入的进行比较
                for (MemberProductSkuDTO memberProductSkuDTO : memberProductSkuDTOS) {
                    for (ProductSku productSku : productSkuList) {

                        if (memberProductSkuDTO.getSkuId().equals(productSku.getId())) { //一个sku商品信息
                            skuTotalFreight = new BigDecimal(0);
                            memberSubOrderOnlineProducts = new PhMemberSubOrderOnlineProducts();
                            memberSubOrderOnlineProducts.setProductId( memberProductDTO.getProductId() );//商品id
                            memberSubOrderOnlineProducts.setProductImageUrl( memberProductDTO.getProductImageUrl() );//商品图片
                            memberSubOrderOnlineProducts.setSkuName( productSku.getSkuName() );//商品sku名称
                            memberSubOrderOnlineProducts.setProductNum( memberProductSkuDTO.getSkuNum() );//商品规格数量
                            memberSubOrderOnlineProducts.setSpecificationValIds( productSku.getSpecificationValIds() );//规格值组合id(为商城按规格查询使用)
                            memberSubOrderOnlineProducts.setProductName( productSnapshotVO.getProductName() );//商品名称
                            memberSubOrderOnlineProducts.setBarCode( productSnapshotVO.getBarCode() );//商品条形编码
                            memberSubOrderOnlineProducts.setProductSnapId( productSnapshotVO.getId() );//商品快照id
                            memberSubOrderOnlineProducts.setCreaterId( memberOrderOnlineDTO.getCreaterId() );//创建人
                            //sku零售价
                            memberSubOrderOnlineProducts.setRetailPrice( productSku.getRetailPrice().doubleValue()*10000);
                            //sku结算价
                            memberSubOrderOnlineProducts.setSettlementPrice(productSku.getSettlementPrice().doubleValue()*10000);
                            //sku物流费
                            memberSubOrderOnlineProducts.setSkuFreight(productSku.getFreight().doubleValue()*10000);

                            //一种sku的商品总价
                            skuTotalProduct = productSku.getRetailPrice().multiply( new BigDecimal(memberProductSkuDTO.getSkuNum()) );
                            memberSubOrderOnlineProducts.setTotalRetailPrice( skuTotalProduct.doubleValue()*10000 );
                            //一种sku的商品结算总价
                            skuTotalSettlement = productSku.getSettlementPrice().multiply( new BigDecimal( memberProductSkuDTO.getSkuNum() ) );
                            memberSubOrderOnlineProducts.setTotalSettlementPrice( skuTotalSettlement.doubleValue()*10000 );
                            //下单数量大于包邮数量，邮费为0
                            if (memberProductSkuDTO.getSkuNum() >= productSku.getNumberOfPackages().intValue()) {
                                skuTotalFreight = new BigDecimal(0);
                                memberSubOrderOnlineProducts.setSkuFreight(Double.valueOf( 0 ));
                            }else {
                                //一种sku的总物流费用
                                skuTotalFreight = productSku.getFreight().multiply( new BigDecimal(memberProductSkuDTO.getSkuNum()) );
                            }
                            //一种sku商品总的物流费
                            memberSubOrderOnlineProducts.setTotalFreight( skuTotalFreight.doubleValue()*10000 );
                            //一种sku的总价格(一种sku的商品总价+一种sku的物流总价)
                            skuTotalCost = skuTotalFreight.add( skuTotalProduct );
                            memberSubOrderOnlineProducts.setTotalCost( skuTotalCost.doubleValue()*10000 );


                            //一种商品物流费用
                            productFreight = productFreight.add(skuTotalFreight);
                            //一种商品的总价
                            productTotalCost = productTotalCost.add(skuTotalProduct);
                            //一种商品的结算总价
                            productSettlement = productSettlement.add( skuTotalSettlement );
                            //一种商品总数量
                            productNum += memberProductSkuDTO.getSkuNum();
                            memberSubOrderOnlineProducts.setProductNum( productNum );

                            //判断后台传入总金额和物流费用以及单价是否和计算后的一致，如果不一致，订单生成出错
                            /*if (!memberProductDTO.getTotalFreight().equals(skuTotalFreight)//判断订单中每种规格商品的总物流费用
                                    || !memberProductDTO.getTotalMoney().equals( productTotalCost )  //判断订单中每种规格商品的总费用
                                    || !memberProductDTO.getRetailPrice().equals(productSku.getRetailPrice())) {
                                throw new RuntimeException("订单选择的商品价格有修改，请重新刷新后进行下单~");
                            }*/
                            memberSubOrderOnlineProductss.add(memberSubOrderOnlineProducts);
                        }
                    }
                }
                //子订单物流总价
                orderTotalFreight = orderTotalFreight.add(productFreight);
                //子订单商品的总价
                orderProductTotalCost = orderProductTotalCost.add(productTotalCost);
                //子订单商品的结算总价
                orderTotalSettlement = orderTotalSettlement.add( productSettlement );

            }
            map.put(subOrderNo,memberSubOrderOnlineProductss); //子订单对应的商品信息
            //子订单对象数据封装
            PhMemberSubOrderOnline phMemberSubOrderOnline = new PhMemberSubOrderOnline();
            BeanUtils.copyProperties(memberOrderOnlineDTO,phMemberSubOrderOnline);
            phMemberSubOrderOnline.setLogisticsMoney(orderTotalFreight.doubleValue()*10000);//子订单物流费用
            phMemberSubOrderOnline.setProductMoney( orderProductTotalCost.doubleValue()*10000 );//子订单的商品总价
            //子订单总价格 =  子订单的各个sku商品总价
            orderTotalCost = orderTotalCost.add( orderProductTotalCost ).add(orderTotalFreight );
            phMemberSubOrderOnline.setOrderMoney( orderTotalCost.doubleValue()*10000 );//子订单总价格
            phMemberSubOrderOnline.setSettleMoney( orderTotalSettlement.doubleValue()*10000);//子订单的结算总价
            phMemberSubOrderOnline.setCreateTime(new Date());//创建时间
            phMemberSubOrderOnline.setCreaterId(memberOrderOnlineDTO.getCreaterId());//创建人
            phMemberSubOrderOnline.setStatus(OrderOnlineStatusEnum.STATUS_TODO_PAY.getStatus());//订单状态
            phMemberSubOrderOnline.setIsSettle( OrderIsSettleEnum.IS_SETTLE_NOT.getIsSettle() );//是否已结算
            phMemberSubOrderOnline.setIsBackScore( OrderIsBackScoreEnum.IS_BACK_SCORE_NOT.getIsBackScore() );//是否已返积分
            phMemberSubOrderOnline.setIsProfit( OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit() );//是否已分润
            phMemberSubOrderOnline.setOrderNo(subOrderNo);//子订单号
            phMemberSubOrderOnline.setShippingName( memberOrderOnlineDTO.getShippingName() );//会员收货人姓名
            phMemberSubOrderOnline.setShippingTelphone( memberOrderOnlineDTO.getShippingTelphone() );//会员收货人电话
            phMemberSubOrderOnline.setShippingProvinceId( memberOrderOnlineDTO.getShippingProvinceId() );//会员收货地址省id
            phMemberSubOrderOnline.setShippingCityId( memberOrderOnlineDTO.getShippingCityId() );//会员收货地址市id
            phMemberSubOrderOnline.setShippingCountyId( memberOrderOnlineDTO.getShippingCountyId() );//会员收货地址县id
            phMemberSubOrderOnline.setShippingTownId( memberOrderOnlineDTO.getShippingTownId() );//会员收货地址社区id
            phMemberSubOrderOnline.setShippingAddressDetail( memberOrderOnlineDTO.getShippingAddressDetail() );//会员收货地址详情地址
            phMemberSubOrderOnline.setShippingAddress( memberOrderOnlineDTO.getShippingAddress() );//会员收货地址完整地址
            phMemberSubOrderOnline.setPositionId( memberOrderOnlineDTO.getPositionId() );//区域id（分润用）
            phMemberSubOrderOnline.setSupplierId( memberOrderOnlineDTO.getSupplierId() );//供应商id
            phMemberSubOrderOnline.setTerminalUnit( memberOrderOnlineDTO.getTerminalUnit() );//终端来源
            //填充提货地址信息(当收货为自提时，分润以商户所在区域进行分润)
            phMemberSubOrderOnline.setMerchantId( memberOrderOnlineDTO.getMerchantId() );//提货地址的商户id
            phMemberSubOrderOnline.setMerchantName( memberOrderOnlineDTO.getMerchantName() );//提货点店名称
            phMemberSubOrderOnline.setTakeGoodsName( memberOrderOnlineDTO.getTakeGoodsName() );//提货点联系人
            phMemberSubOrderOnline.setTakeGoodsTelphone( memberOrderOnlineDTO.getTakeGoodsTelphone() );//提货点人联系电话
            phMemberSubOrderOnline.setTakeGoodsAddress( memberOrderOnlineDTO.getTakeGoodsAddress() );//提货点地址
            phMemberSubOrderOnline.setTakeProvinceId( memberOrderOnlineDTO.getTakeProvinceId() );//提货省id
            phMemberSubOrderOnline.setTakeProvinceName( memberOrderOnlineDTO.getTakeProvinceName() );//提货省名称
            phMemberSubOrderOnline.setTakeCityId( memberOrderOnlineDTO.getTakeCityId() );//提货市id
            phMemberSubOrderOnline.setTakeCityName( memberOrderOnlineDTO.getTakeCityName() );//提货市名称
            phMemberSubOrderOnline.setTakeCountyId( memberOrderOnlineDTO.getTakeCountyId() );//提货县id
            phMemberSubOrderOnline.setTakeCountyName( memberOrderOnlineDTO.getTakeCountyName() );//提货县名称
            phMemberSubOrderOnline.setTakeTownId( memberOrderOnlineDTO.getTakeTownId() );//提货区id
            phMemberSubOrderOnline.setTakeTownName( memberOrderOnlineDTO.getTakeTownName() );//提货区名称
            //需要创建的子订单对象
            memberSubOrderOnlines.add(phMemberSubOrderOnline);
            logger.info( "所有子订单创建对象内容：{}",JSON.toJSON( memberSubOrderOnlines ) );
            //主订单的总价
            mainOrderCost = mainOrderCost.add(orderTotalCost);
            //主订单的商品总价
            mainOrderProductCost = mainOrderProductCost.add(orderProductTotalCost);
            //主订单的物流总价
            mainOrderFreigtCost = mainOrderFreigtCost.add( orderTotalFreight );
        }
        map.put("subOrder",memberSubOrderOnlines); //所有子订单
        //主订单数据封装
        PhMemberMainOrderOnline phMemberMainOrderOnline = new PhMemberMainOrderOnline();
        phMemberMainOrderOnline.setOrderNo(mainOrderNo);//主订单号
        phMemberMainOrderOnline.setOrderMoney( mainOrderCost.doubleValue()*10000 );//主订单总金额
        phMemberMainOrderOnline.setProductMoney( mainOrderProductCost.doubleValue()*10000 );//主订单商品金额
        phMemberMainOrderOnline.setLogisticsMoney( mainOrderFreigtCost.doubleValue()*10000 );//主订单所有物流费
        phMemberMainOrderOnline.setCreateTime(new Date());//创建时间
        phMemberMainOrderOnline.setMemberId( list.get( 0 ).getCreaterId());//创建人
        map.put("mainOrder",phMemberMainOrderOnline); //主订单
        return map;
    }

    /**
     * @methodname getProductSku 的描述：查询商品sku信息
     * @param productId
     * @return com.ph.shopping.facade.product.entity.ProductSku
     * @author 郑朋
     * @create 2017/5/16
     */
    private List<ProductSku> getProductSku(Long productId) {
        List<ProductSku> productSkuList;
        ProductSku productSkuIn = new ProductSku();
        productSkuIn.setProductId(productId);
        logger.info("查询商品sku信息接口入参，phProductVo={}", JSON.toJSONString(productSkuIn));
        productSkuList= productSkuService.getProductSkuList(productSkuIn);
        logger.info("查询商品sku信息接口返回值，phProductVo={}",JSON.toJSONString(productSkuList));
        return productSkuList;
    }

    @Override
    @Transactional
    public Result onlineOrderClose() {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try {
            List<Long> list = this.iMemberOrderOnlineMapper.getOrderByStatus();
            for (Long id : list) {
                PhMemberOrderOnline phMemberOrderOnline = this.iMemberOrderOnlineMapper.getLock(id);
                logger.info("订单"+phMemberOrderOnline.getOrderNo()+"超过15分钟未支付，准备取消");
                phMemberOrderOnline.setStatus(7);
                if(this.iMemberOrderOnlineMapper.updateByPrimaryKeySelective(phMemberOrderOnline)>0){
                    logger.info("订单"+phMemberOrderOnline.getOrderNo()+"超过15分钟未支付，已取消");
                }else{
                    logger.info("订单"+phMemberOrderOnline.getOrderNo()+"超过15分钟未支付，取消失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            result = ResultUtil.getResult(RespCode.Code.FAIL);
            throw new ProfitException(ProfitExceptionEnum.ONLINE_ORDERP_CLOSE_EXCEPTION);
        }

        return result;
    }

    /**
     * 创建订单-kuaihuo
     * @author lzh
     */
    @Override
    @TxTransaction
    @Transactional
    public Result addOnLineOrder(AddMemberOrderOnlineDTO2 addDto,String dishs,String dCount,String subscriptionMoney,Long shopCartId) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try {
            //判断留言
            if(addDto.getMessage()!=null&&!"".equals(addDto.getMessage().replaceAll("\\s*", ""))){
                if(addDto.getMessage().length()>50){
                    return ResultUtil.getResult(RespCode.Code.LIUYAN_ERROR);
                }
            }
            //判断预计到店时间是否大于当前时间
            if(DateUtil.isCompareTime(addDto.getHopeServiceDate(),new Date())){
                return ResultUtil.getResult(RespCode.Code.NOW_TIME);

            }
          
            //查询营业时间以及非营业时间并进行判断
            Merchant merchantTime=iMemberOrderOnlineMapper.getMerchantTime(addDto.getMerchantId());
            if(merchantTime!=null&&addDto.getHopeServiceDate()!=null){
            	 if(DateUtil.belongCalendar(addDto.getHopeServiceDate(),merchantTime.getCloseBeginTime(),merchantTime.getCloseEndTime())){
                 	//如果在非营业时间返回
                     return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                 }
          	   //判断时间在营业时间之内
                String hopeTime = DateUtil.dateToStr(addDto.getHopeServiceDate(), 8);//截取预计到达时间
     	        long hopeTimes = Long.valueOf(hopeTime.replaceAll("[-\\s:]",""));			//截取字符串转换成long
     	        long a=Long.valueOf(merchantTime.getOpenBeginTime().replaceAll("[-\\s:]",""));
     	        long b=Long.valueOf(merchantTime.getOpenEndTime().replaceAll("[-\\s:]",""));
                 if(hopeTimes<a||hopeTimes>b){
                 	return  ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                 }
            }
           

            //通过id查询菜品信息
            String[] dish1=dishs.split(",");
            List<DishOrder> dishOrder=iMemberOrderOnlineMapper.getDishs(dish1);
            Date leaveTime=null;
            int count=0; //计数
            long totalPrice=0; //菜品总价
            //=====================判断不能同时选择餐位跟打包=============================
            List<Long> list1=new ArrayList<Long>();
            //将餐位ID放到list中
            for(int p=0;p<dishOrder.size();p++){
            	if(dishOrder.get(p).getType()==1){
            		list1.add(dishOrder.get(p).getId());
            	}
            	
            }
            //判断id为-1并且还有别的餐位
            for(int o=0;o<list1.size();o++){
            	if(list1.size()>1&&list1.get(o)==-1){
            		return ResultUtil.getResult(RespCode.Code.NO_DABAO_MERCHANT);
                }
            }
            
            for(int i=0;i<dishOrder.size();i++)
            {

            	//查询菜品数量
            	Map<String,Object> map=new HashMap<String, Object>();
            	map.put("id",dishOrder.get(i).getId() );
            	map.put("merchantId", addDto.getMerchantId());
            	map.put("memberId", addDto.getMemberId());
            	Long counts=iMemberOrderOnlineMapper.getDcount(map);
            	dishOrder.get(i).setdCount1(counts);	
                if(dishOrder.get(i).getMoney()!=null){
	                //计算菜品总价
	                totalPrice+=dishOrder.get(i).getMoney()*counts;
                }
                //判断必须选择餐位
                int type=dishOrder.get(i).getType();
                if(type==1){
                    count++;
                }
                
                if(type==1&&dishOrder.get(i).getId()!=-1){//先将包间id写成-1占坑
                    //获取时间段
                    List<PhMemberOrderOnline> timeFromSE=iMemberOrderOnlineMapper.getTimeByid(dishOrder.get(i).getId());
                    //获取商家设定包间时间
                    Long hopetime=dishOrder.get(i).getHopeTime();
                    //获取预计离店时间
                    leaveTime=DateUtil.DsDay_Second(addDto.getHopeServiceDate(),hopetime*60);
                    /**
                     * 判断期望时间之内没有预定的包间
                     */
                    for(int t=0;t<timeFromSE.size();t++){
                        //判断餐位预定时间
                        if(DateUtil.belongCalendar(addDto.getHopeServiceDate(),timeFromSE.get(t).getHopeServiceDate(),timeFromSE.get(t).getPredictServiceDate())||DateUtil.belongCalendar(addDto.getHopeServiceDate(),DateUtil.JDay_Second(timeFromSE.get(t).getHopeServiceDate(),hopetime*60),timeFromSE.get(t).getHopeServiceDate())){
                            return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
                        }
                    }

                }

            }
            
            if(count==0){
                return  ResultUtil.getResult(RespCode.Code.DISH_ERROR);
            }
            /**
             * 删除购物车信息
             */
            iMemberOrderOnlineMapper.deleteShopCart(addDto.getMemberId(),addDto.getMerchantId());
            //生成订单号
            String orderNum= "KHXSDD"+UtilDate.getOrderNum()+UtilDate.getThree();
            addDto.setOrderNo(orderNum);
            addDto.setPredictServiceDate(leaveTime);
            addDto.setTotalPrice(totalPrice);
            //添加到订单表
            iMemberOrderOnlineMapper.addOnLineOrder(addDto);
            //获取订单id
            Long orderOnlineId=iMemberOrderOnlineMapper.getMaxId();
            if(orderOnlineId!=null&&orderOnlineId!=0){
                for(int i=0;i<dishOrder.size();i++){
                    //遍历将订单id插入到DishOrder实体
                    dishOrder.get(i).setOrderOnlineId(orderOnlineId);
                }
                //添加到中间表
                iMemberOrderOnlineMapper.addOrderSku(dishOrder);
            }
            /**
             * 订单完成，给商户推送一下
             */
            Merchant merchant=iMemberOrderOnlineMapper.getMerchantForSms(addDto.getMerchantId());
            logger.info("=================预订订单完成，开始给商户推送====================");
            PushDTO push=new PushDTO();
            push.setCodeType("fr0002");
            push.setContent("您有一笔新订单，点击查看");
            push.setTitle("通知");
            String userId=merchant.getTelPhone()+"_1";
            push.setUserId(userId);
            pushUtil.push(push);
            logger.info("=================预订订单推送完成================================");
            logger.info("=================预订订单完成，开始发送短信====================");
            PrePayDTO prePay=new PrePayDTO();
            prePay.setKfPhone("4000-8586-315");
            prePay.setOrderNum(orderNum);
            prePay.setPhone(merchant.getTelPhone());
            smsUtil.sendPrepayMsg(prePay);
            
            logger.info("=================预订订单短信完成================================");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            result = ResultUtil.getResult(RespCode.Code.FAIL);
        }

        return result;
    }
    /**
     *商户接单  时间验证
     *
     */
    @Transactional
    @Override
    public Result findOrderNoStatus(AddMemberOrderOnlineDTO2 addDto) {
        try {
            if (addDto.getOrderNo()==null ) {
                return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(),"[订单号]不能为空");
            }
            //推送
            PushDTO pushDto=new PushDTO();
            AddMemberOrderOnlineDTO2 findMerchantName = iMemberOrderOnlineMapper.findMerchantName(addDto.getMerchantId());
//            判断如果是选择的是打包就不判断时间
            if(addDto.getType()==1 && addDto.getDishId()!=-1){
                //获取餐位保留时间
                Long hopeTime=iMemberOrderOnlineMapper.getHopeTimeByDishId(addDto.getDishId());
            //当前用户的时间和这个包间已经预定过的时间做对比
            List<PhMemberOrderOnline> phMemberOrderOnline = iMemberOrderOnlineMapper.findOrderNoStatus(addDto.getMerchantId(),addDto.getDishId());
            for (int i = 0; i < phMemberOrderOnline.size(); i++) {
                if(DateUtil.belongCalendar(addDto.getHopeServiceDate(),phMemberOrderOnline.get(i).getHopeServiceDate(),phMemberOrderOnline.get(i).getPredictServiceDate())||DateUtil.belongCalendar(addDto.getHopeServiceDate(),DateUtil.JDay_Second(phMemberOrderOnline.get(i).getHopeServiceDate(),hopeTime*60),phMemberOrderOnline.get(i).getHopeServiceDate())){
                    //当前时间的包间已被预定提示商户
                    iMemberOrderOnlineMapper.updateOrderNoStatusQu(addDto.getOrderNo());
                    logger.info("=================订单取消，开始给会员推送====================");
                    pushDto.setTitle("通知");
                    pushDto.setContent("【快火】您在"+findMerchantName.getMerchantName()+"下的订单未被接单，已自动取消，点击查看详情");
                    String userId=addDto.getMemberTelphone()+"_0";
                    pushDto.setUserId(userId);
                    pushDto.setCodeType("fr0001");
                    pushUtil.push(pushDto);
                    logger.info("=================给会员推送完成====================");
                    //取消订单推送消息入库
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("content",pushDto.getContent());
                    map.put("userId",addDto.getMemberTelphone());
                    map.put("messageType",3);
                    iMemberOrderOnlineMapper.insertMessageRecord(map);
                    return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
                }
            }
            }
            //查询营业时间以及非营业时间并进行判断
            Merchant merchantTime=iMemberOrderOnlineMapper.getMerchantTime(addDto.getMerchantId());
            if(merchantTime!=null){
            	  if(DateUtil.belongCalendar(addDto.getHopeServiceDate(),merchantTime.getCloseBeginTime(),merchantTime.getCloseEndTime())){
                   	//如果在非营业时间返回
                       return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                   }
                  //判断时间在营业时间之内
                  String hopeTime = DateUtil.dateToStr(addDto.getHopeServiceDate(), 8);//截取预计到达时间
                  long hopeTimes = Long.valueOf(hopeTime.replaceAll("[-\\s:]",""));			//截取字符串转换成long
      	        long a=Long.valueOf(merchantTime.getOpenBeginTime().replaceAll("[-\\s:]",""));
      	        long b=Long.valueOf(merchantTime.getOpenEndTime().replaceAll("[-\\s:]",""));
                  if(hopeTimes<a||hopeTimes>b){
                  	return  ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                  }
            }
            //如果当前时间的包间没有用户下单，则商家可以进行接单
            iMemberOrderOnlineMapper.updateOrderNoStatus(addDto.getOrderNo());
            //查询sku表中的销量  dCount
            List<AddMemberOrderOnlineDTO2> queryOrderDate = iMemberOrderOnlineMapper.queryOrderDcount(addDto.getOrderNo());
            for (int i = 0; i < queryOrderDate.size(); i++) {
            	 iMemberOrderOnlineMapper.updateDishSaleNum(queryOrderDate.get(i).getSaleNum(),queryOrderDate.get(i).getDishId());
			}
            logger.info("=================商户已接单，开始给会员推送====================");
            pushDto.setTitle("通知");
            pushDto.setContent("【快火】您在"+findMerchantName.getMerchantName()+"下的订单已被接单，点击查看详情");
            String userId=addDto.getMemberTelphone()+"_0";
            pushDto.setUserId(userId);
            pushDto.setCodeType("fr0001");
            pushUtil.push(pushDto);
            //接单推送消息入库
            Map<String,Object> map=new HashMap<>();
            map.put("content",pushDto.getContent());
            map.put("userId",addDto.getMemberTelphone());
            map.put("messageType",2);
            iMemberOrderOnlineMapper.insertMessageRecord(map);
            logger.info("=================给会员推送完成====================");
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 查询kh线上订单(列表)
     * @author 王雪洋
     */

    @Override
    public Result onlineOrderQuery(QueryOrderDTO queryOrderDTO) {
        Result result;
        List<OrderOnlineVO> oList = new ArrayList<OrderOnlineVO>();

        try{
            //订单信息
            //提取起始页码数值
            queryOrderDTO.setBeginIndex((queryOrderDTO.getPageNum()-1)*queryOrderDTO.getPageSize());
            RowBounds rowBounds=new RowBounds(queryOrderDTO.getBeginIndex(),queryOrderDTO.getPageSize());
            List<OrderOnlineVO> orders = this.iMemberOrderOnlineMapper.selectOrdersList(queryOrderDTO,rowBounds);
            //添加相应的点菜  包间信息
            for (int i = 0; i < orders.size(); i++) {
                OrderOnlineVO vo = orders.get(i);
                //订单包含包间餐位
                List<DishDetailsVO> dishList = this.iMemberOrderOnlineMapper.selectDishesOrSeats(vo.getId(), 1);
                vo.setSeats(dishList);
                //餐位人数
                int n = 0;
                //处理餐位人数和对应的金额
                for (int j = 0 ; j<dishList.size() ; j++){
                    n += dishList.get(j).getNum();
                    dishList.get(j).setMoney(DoubleUtils.formatRound(vo.getSubscriptionMoney() / 10000.00, 2));
                }
                vo.setNum(n);
                //订单包含菜品  (暂时不展示)
                //vo.setDlist(this.iMemberOrderOnlineMapper.selectDishesOrSeats(vo.getOrderNo(),0));
                //处理定金显示
                vo.setSubscriptionMoney(Double.parseDouble(DoubleUtils.formatRound(vo.getSubscriptionMoney() / 10000.00, 2)));
                oList.add(vo);
            }
            logger.info("查询线上订单成功！");
            result = ResultUtil.getResult(RespCode.Code.SUCCESS,oList);
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("查询线上订单失败！");
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }

    /**
     * 查询订单详情   by orderNo 王雪洋
     */
    @Override
    public Result queryOrderDital(String orderNo) {
        Result result;
        try {
            OrderOnlineVO orderOnline = this.iMemberOrderOnlineMapper.selectOrderByOrderNo(orderNo);
            //订单不存在
//            if(orderOnline != null){
//                logger.info("该订单不存在！");
//                return ResultUtil.setResult(false,"订单不存在!");
//            }
            //订单包含包间餐位
            List<DishDetailsVO> dishList = this.iMemberOrderOnlineMapper.selectDishesOrSeats(orderOnline.getId(), 1);
            //计算用餐人数
            int  num = 0;
            for (DishDetailsVO dish : dishList) {
                num += dish.getNum();
            }
            orderOnline.setNum(num);
            orderOnline.setSeats(dishList);
            //订单包含菜品
            List<DishDetailsVO> dishes = this.iMemberOrderOnlineMapper.selectDishesOrSeats(orderOnline.getId(), 0);
            //处理菜品价格展示  订购量
            for (int j=0 ; j<dishes.size() ; j++){
                dishes.get(j).setMoney(DoubleUtils.formatRound(dishes.get(j).getColumnMoney() / 10000.00, 2));
            }
            orderOnline.setDlist(dishes);

            
            //处理定金显示
            orderOnline.setSubscriptionMoney(Double.parseDouble(DoubleUtils.formatRound(orderOnline.getSubscriptionMoney() / 10000.00, 2)));
            logger.info("查询订单详情成功！");
            //订单状态是已经取消还未取消
          Integer onLineType =  orderOnline.getStatus() == null? 0:orderOnline.getStatus();
            if(onLineType==7){//取消订单
                orderOnline.setOnLineType(1);//0是未取消，1是取消订单
            }else{
                orderOnline.setOnLineType(0);
            }

            result = ResultUtil.getResult(RespCode.Code.SUCCESS, orderOnline);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询订单详情失败！");
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }

    @Override
    public AddMemberOrderOnlineDTO2 queryOrderDate(String orderNo) {
        return iMemberOrderOnlineMapper.queryOrderDate(orderNo);
    }
    /**
     * @author lzh
     * 获取购物车信息
     */
    @Override
    public Result toAddOnLineOrder(ShopCart shopCart) {
    	//获取商户门店照片 type=3
    	List<String> imgUrl=iMemberOrderOnlineMapper.getMerchantImg(shopCart.getMerchantId());
    	//获取商户的userId
    	Long userId=iMemberOrderOnlineMapper.getUserIdByMerchantId(shopCart.getMerchantId());
    	shopCart.setMerchantId(userId);
        //获取购物车信息
        List<DishDTO> sh=iMemberOrderOnlineMapper.getShopCart(shopCart);
        //处理金额
        for(int j=0;j<sh.size();j++){
        	sh.get(j).setMoney(Double.parseDouble(DoubleUtils.formatRound(sh.get(j).getMoney()/10000.00, 2)));
        	//菜品图片
        	//查询菜品图片
			List<String> address=iMemberOrderOnlineMapper.getDishImg(sh.get(j).getId());
			
			if(address!=null&&!address.isEmpty()){
				for(int k=0;k<address.size();k++){
					sh.get(j).setImgAddress(address.get(0));	//将图片插入到菜品实体中
				}
						
			}

        }
        if(sh==null||sh.isEmpty()){
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        }
        
        ShopCartDTO shopcarts=null;
        //获取购物车其他信息
       List<ShopCartDTO> shopcart1=iMemberOrderOnlineMapper.getShopCartOtherMessage(shopCart);
       for(int i=0;i<shopcart1.size();i++){
    	   shopcarts=shopcart1.get(i);
       }
       shopcarts.setDishs(sh);
       shopcarts.setMerchantImg(imgUrl);//商户照片  
       long peopleNum=0;
        //判断时间
        for(int i=0;i<sh.size();i++){
            if(sh.get(i).getType()==1&&sh.get(i).getId()!=-1){  //判断type为1且不为包间
            	//判断包间描述不为空
            	if(sh.get(i).getCount()!=null&&!"".equals(sh.get(i).getCount())){
            		//获取包间人数
                	peopleNum+=Long.valueOf(sh.get(i).getCount()).longValue();
            	}
            	
                //获取购物车包间的时间
                List<PhMemberOrderOnline> date=iMemberOrderOnlineMapper.getDateByDishId(sh.get(i).getId());
                if(date!=null){
                	for(int j=0;j<date.size();j++){
                		 if(DateUtil.belongCalendar(shopcarts.getHopesDate(),date.get(j).getHopeServiceDate(),date.get(j).getPredictServiceDate())){
     	                    return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
     	                }
                	}
	               
                }
            }
        }
        if(peopleNum!=0){
        	 //设置包间人数
            shopcarts.setPeopleNum(peopleNum);
        }
       
        return ResultUtil.getResult(RespCode.Code.SUCCESS,shopcarts);
    }
    /**
	 * 清空购物车接口(用户重新选择时间清空餐位)
	 * @param shopCart
	 * @return
	 * @author lzh
	 */
	@Override
	@Transactional
	public Result cleanShopCart(ShopCart shopCart) {
		try {
			//查询购物车中的餐位信息
			List<DishDTO> shop=iMemberOrderOnlineMapper.getShopCart(shopCart);
		      //判断type为1删除该餐位
	        for(int i=0;i<shop.size();i++){
	        	if(shop.get(i).getType()==1){
	        		int r=iMemberOrderOnlineMapper.deleteShopCartForType(shopCart.getMemberId(), shopCart.getMerchantId());
	        	}
	        	
	        }
		} catch (Exception e) {
			 e.printStackTrace();
	         logger.info("删除购物车餐位失败");
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	
	}
    /**
     * 会员取消接单
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public Result cancelOrder(Long orderId,Long memberId) {
        logger.info("取消订单传入参数id：" + orderId);
        try {
            if (orderId == null) {
                return ResultUtil.setResult(false, "订单ID不能为空！");
            }
            if (memberId == null || memberId.equals("")) {
                return ResultUtil.setResult(false, "商户id不能为空！");
            }
            //判断当前订单状态
            PhMemberOrderOnline orderOnline = iMemberOrderOnlineMapper.selectOrderByOrderId(orderId);
            /*if (orderOnline.getStatus() == 2 || orderOnline.getStatus() == 3) {
                return ResultUtil.setResult(false, "该订单已确认，不能取消！");
            }*/
            if (orderOnline.getStatus() == 7) {
                return ResultUtil.setResult(false, "该订单已取消，不可重复操作！");
            }
            if (orderOnline.getMemberId().longValue() != memberId.longValue()) {
                return ResultUtil.setResult(false, "当前用户身份校验失败，取消操作！");
            }
            if (iMemberOrderOnlineMapper.cancelOrder(orderId) > 0 && (orderOnline.getStatus() == 2 || orderOnline.getStatus() == 3)) {
                //取消成功，给商户推送消息
                logger.info("++++++++++++++++   会员取消订单成功，开始推送消息给商户   +++++++++++++++");

                //会员用户手机号
                String memberTel = iMemberOrderOnlineMapper.getMemberTel(orderOnline.getMemberId());
                //商户手机号
                String merchantTel = iMemberOrderOnlineMapper.getMerchantTel(orderOnline.getMerchantId());
                PushDTO pushDto=new PushDTO();
                pushDto.setTitle("取消订单");
                pushDto.setContent("您有一笔新的订单，用户已取消，点击查看");
                pushDto.setUserId(merchantTel+"_1");
                pushDto.setCodeType("fr0002");
                pushUtil.push(pushDto);

                logger.info("++++++++++++++++   消息推送完成   +++++++++++++++");
                logger.info("++++++++++++++++   会员取消订单成功，开始发送短信给商户   +++++++++++++++");
                OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
                orderCancelDTO.setKfPhone("400-8586-315");
                orderCancelDTO.setName(memberTel);//用户名为会员手机号
                logger.info("*************   会员手机号："+memberTel);
                orderCancelDTO.setPhone(merchantTel);
                logger.info("************  商户手机号："+merchantTel);
                smsUtil.cancelOrder(orderCancelDTO);
                logger.info("++++++++++++++++   短信发送完毕   +++++++++++++++");
            }
            return ResultUtil.setResult(true, "订单取消成功！");
        } catch (Exception e) {
            logger.error("会员取消订单："+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
        }
        return ResultUtil.setResult(true, "订单取消失败！");
    }
    /**
	 * 再来一单接口
	 * @param
	 * @return
	 * @author lzh
	 */
@Override
@TxTransaction
@Transactional
public Result onceMoreOnlineOrder(Long orderId) {
	Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
	try {
		//通过订单ID获取会员ID和商户ID
		AddMemberOrderOnlineDTO2 info=iMemberOrderOnlineMapper.getMemberIdAndmerchantId(orderId);	
		//删除购物车信息
		 iMemberOrderOnlineMapper.deleteShopCart(info.getMemberId(),info.getMerchantId());
		//获取订单信息  并插入到购物车表中
		 List<ShopCart> shopCart=iMemberOrderOnlineMapper.getOrderAllByorderId(orderId);
		 for(int i=0;i<shopCart.size();i++){
			 if(shopCart.get(i).getType()==0){  //如果是菜品  加入到购物车中
				//将原订单中的菜品信息插入到购物车中   不添加包间
				 iMemberOrderOnlineMapper.addShopCartOnceMore(shopCart.get(i));
			 }
			 
		 }
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getStackTrace().toString());
        return ResultUtil.getResult(RespCode.Code.FAIL);
	}
	
	return result;
}

    @Override
    @Transactional
    public Result cancelOrderByMerchant(Long orderId, Long userId) {
        logger.info("取消订单传入参数id：" + orderId);
        try {
            if (orderId == null) {
                return ResultUtil.setResult(false, "订单ID不能为空！");
            }
            if (userId == null || userId.equals("")) {
                return ResultUtil.setResult(false, "登录信息有误");
            }
            //判断当前订单状态
            PhMemberOrderOnline orderOnline = iMemberOrderOnlineMapper.selectOrderByOrderId(orderId);
            if (orderOnline.getStatus() == 2 || orderOnline.getStatus() == 3) {
                return ResultUtil.setResult(false, "该订单已确认，不能取消！");
            }
            if (orderOnline.getStatus() == 7) {
                return ResultUtil.setResult(false, "该订单已取消，不可重复操作！");
            }
            if (orderOnline.getMerchantId().longValue() != userId.longValue()) {
                return ResultUtil.setResult(false, "当前用户身份校验失败，取消操作！");
            }
            if (iMemberOrderOnlineMapper.cancelOrder(orderId) > 0 && orderOnline.getStatus() == 0) {
                //取消成功，给用户推送消息
                logger.info("++++++++++++++++   会员取消订单成功，开始推送消息给商户   +++++++++++++++");

                //会员用户手机号
                String memberTel = iMemberOrderOnlineMapper.getMemberTel(orderOnline.getMemberId());
                PushDTO pushDto=new PushDTO();
                pushDto.setTitle("取消订单");
                pushDto.setContent("您的预定订单未被接单，点击查看");
                pushDto.setUserId(memberTel+"_0");
                pushDto.setCodeType("fr0001");
                pushUtil.push(pushDto);
				//消息入库
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("content",pushDto.getContent());
				map.put("userId",memberTel);
				map.put("messageType",3);
				iMemberOrderOnlineMapper.insertMessageRecord(map);
                logger.info("++++++++++++++++   消息推送完成   +++++++++++++++");
            }
            return ResultUtil.setResult(true, "订单取消成功！");
        } catch (Exception e) {
            logger.error("会员取消订单："+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
        }
        return ResultUtil.setResult(true, "订单取消失败！");
    }


/*=====================================快火二期===============================================*/
    /**
     * 创建订单-kuaihuo
     * @author lzh
     */
    @Override
    @TxTransaction
    @Transactional
    public Result addOnLineOrderTwo(AddMemberOrderOnlineDTO2 addDto,Long dishId) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try {
            //判断留言
            if(addDto.getMessage()!=null&&!"".equals(addDto.getMessage().replaceAll("\\s*", ""))){
                if(addDto.getMessage().length()>50){
                    return ResultUtil.getResult(RespCode.Code.LIUYAN_ERROR);
                }
            }
            //判断预计到店时间是否大于当前时间
            if(DateUtil.isCompareTime(addDto.getHopeServiceDate(),new Date())){
                return ResultUtil.getResult(RespCode.Code.NOW_TIME);

            }

            //查询营业时间以及非营业时间并进行判断
            Merchant merchantTime=iMemberOrderOnlineMapper.getMerchantTime(addDto.getMerchantId());
            if(merchantTime!=null&&addDto.getHopeServiceDate()!=null){
                if(DateUtil.belongCalendar(addDto.getHopeServiceDate(),merchantTime.getCloseBeginTime(),merchantTime.getCloseEndTime())){
                    //如果在非营业时间返回
                    return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                }
                //判断时间在营业时间之内
                String hopeTime = DateUtil.dateToStr(addDto.getHopeServiceDate(), 8);//截取预计到达时间
                long hopeTimes = Long.valueOf(hopeTime.replaceAll("[-\\s:]",""));			//截取字符串转换成long
                long a=Long.valueOf(merchantTime.getOpenBeginTime().replaceAll("[-\\s:]",""));
                long b=Long.valueOf(merchantTime.getOpenEndTime().replaceAll("[-\\s:]",""));
                if(hopeTimes<a||hopeTimes>b){
                    return  ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
                }
            }
            //通过id查询餐位信息
            DishOrder dishOrder=iMemberOrderOnlineMapper.getDishsTwo(dishId);
            if(dishOrder.getType()==1&&dishOrder.getId()!=-1) {//先将包间id写成-1占坑
                //获取时间段
                List<PhMemberOrderOnline> timeFromSE = iMemberOrderOnlineMapper.getTimeByid(dishOrder.getId());
                /**
                 * 判断期望时间之内没有预定的包间
                 */
                for (int t = 0; t < timeFromSE.size(); t++) {
                    //判断餐位预定时间
                    if (DateUtil.belongCalendar(addDto.getHopeServiceDate(), timeFromSE.get(t).getHopeServiceDate(), timeFromSE.get(t).getPredictServiceDate())) {
                        return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
                    }
                }
            }
            //生成订单号
            String orderNum= "KHXSDD"+UtilDate.getOrderNum()+UtilDate.getThree();
            addDto.setOrderNo(orderNum);
            addDto.setTotalPrice(dishOrder.getMoney());
            //添加到订单表
            int o=iMemberOrderOnlineMapper.addOnLineOrderTwo(addDto);
            //获取订单id
            if(o!=0){
                //将订单id插入到DishOrder实体
                dishOrder.setOrderOnlineId(addDto.getId());
                dishOrder.setdCount1((long)1); //添加包间到中间表
                int k=iMemberOrderOnlineMapper.addOrderSkuTwo(dishOrder);

                //查询购物车中是否有没有关联的菜品  如果有的话则关联到这个订单中（完成再来一单功能）
                List<ShopCartDTO> shopCart=iMemberOrderOnlineMapper.getShopCartForNull(addDto);
                if(shopCart.size()!=0){
                	for(int p=0;p<shopCart.size();p++){
						shopCart.get(p).setOrderId(dishOrder.getOrderOnlineId());
					}
					//将菜品插入到订单中间表
                    int s=iMemberOrderOnlineMapper.addOrderSkuForShopCartTwo(shopCart);
					//删除购物车信息
					iMemberOrderOnlineMapper.deleteShopCartForNull(addDto);
                }

            }
			/**
			 * 订单完成，给商户推送一下
			 */
            Merchant merchant=iMemberOrderOnlineMapper.getMerchantForSms(addDto.getMerchantId());
            logger.info("=================预订订单完成，开始给商户推送====================");
            PushDTO push=new PushDTO();
            push.setCodeType("fr0002");
            push.setContent("您有一笔新订单，点击查看");
            push.setTitle("通知");
            String userId=merchant.getTelPhone()+"_1";
            push.setUserId(userId);
            pushUtil.push(push);
            logger.info("=================预订订单推送完成================================");
            logger.info("=================预订订单完成，开始发送短信====================");
            PrePayDTO prePay=new PrePayDTO();
            prePay.setKfPhone("4000-8586-315");
            prePay.setOrderNum(orderNum);
            prePay.setPhone(merchant.getTelPhone());
            smsUtil.sendPrepayMsg(prePay);

            logger.info("=================预订订单短信完成================================");


        } catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //抛出异常回滚事物
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            result = ResultUtil.getResult(RespCode.Code.FAIL);
        }

        return result;
    }
    /**
     * 创建订单（菜品）
     * @author lzh
     */
    @Override
    @TxTransaction
    @Transactional
    public Result addOnLineDishOrder(String dishs, Long orderId, Long memberId, Long merchantId) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try{
            //获取菜品信息
            String[] dish1=dishs.split(",");
            List<DishOrder> dishOrder=iMemberOrderOnlineMapper.getDishs(dish1);
            //菜品价格
            long totalPrice=0;
            for(int i=0;i<dishOrder.size();i++){
                //查询菜品数量
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",dishOrder.get(i).getId() );
                map.put("merchantId", merchantId);
                map.put("memberId", memberId);
                map.put("orderId",orderId);
                Long counts=iMemberOrderOnlineMapper.getDcount(map);
                dishOrder.get(i).setdCount1(counts);  //将菜品数量设置到实体中
                if(dishOrder.get(i).getMoney()!=null&&dishOrder.get(i).getdCount1()!=null){
                    //计算菜品总价
                    totalPrice+=dishOrder.get(i).getMoney()*counts;
                }
                /**
                 * 判断订单中是否有该菜品信息  如果有+count  如果没有 加一条数据
                 */
                Map<String,Object> dishMap=new HashMap<String, Object>();
                dishMap.put("id",dishOrder.get(i).getId() );
                dishMap.put("orderId",orderId);
                Long id=iMemberOrderOnlineMapper.getDishMessageForOrder(dishMap);
                //如果订单中没有菜品则增加数据
                dishOrder.get(i).setOrderOnlineId(orderId);  //订单id
                if(id==null||id==0){
                    int s=iMemberOrderOnlineMapper.addOrderSkuTwo(dishOrder.get(i));
                }else{
                    //如果订单中有菜品则修改菜品数量
                    int d=iMemberOrderOnlineMapper.updateOrderDish(dishOrder.get(i));
                }
            }
            //修改订单表中的总价totalPrice
            Map<String,Object> priceMap=new HashMap<String, Object>();
            priceMap.put("id",orderId);
            priceMap.put("totalPrice",totalPrice);
            iMemberOrderOnlineMapper.updateOrderTotalPrice(priceMap);
            /**
             * 删除购物车中的菜品信息
             */
            iMemberOrderOnlineMapper.deleteShopCartTwo(memberId,merchantId,orderId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            result = ResultUtil.getResult(RespCode.Code.FAIL);
        }
        return result;
    }
	/**
	 * 再来一单接口
	 * @param
	 * @return
	 * @author lzh
	 */
	@Override
	@TxTransaction
	@Transactional
	public Result onceMoreOnlineOrderTwo(Long orderId) {
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		try {
			//通过订单ID获取会员ID和商户ID
			AddMemberOrderOnlineDTO2 info=iMemberOrderOnlineMapper.getMemberIdAndmerchantId(orderId);
			//删除购物车信息
			iMemberOrderOnlineMapper.deleteShopCart(info.getMemberId(),info.getMerchantId());
			//查询会员在这个商家中预定的所有包间预定的订单ID
			List<Long> orders=iMemberOrderOnlineMapper.getOnlineOrderDish(info); //memberId merchantId
			//获取订单信息中的菜品信息  并插入到购物车表中
			List<ShopCart> shopCart=iMemberOrderOnlineMapper.getOrderAllByorderId(orderId);
			for(int i=0;i<shopCart.size();i++){
				if(shopCart.get(i).getType()==0){  //如果是菜品  加入到购物车中
					if(orders.size()!=0){		//如果已经预定餐位
						for(int k=0;k<orders.size();k++){
							shopCart.get(i).setOrderId(orders.get(k));
							iMemberOrderOnlineMapper.addShopCartOnceMoreFor(shopCart.get(i));
						}
					}else {   //
						//将原订单中的菜品信息插入到购物车中   不添加包间
						iMemberOrderOnlineMapper.addShopCartOnceMore(shopCart.get(i));
					}

				}

			}

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}

		return result;
	}
}
