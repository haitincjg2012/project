package com.alqsoft.service.impl.memberaddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.memberaddress.MemberAddressDao;
import com.alqsoft.entity.area.Area;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.rpc.mobile.RpcMemberAddressService;
import com.alqsoft.service.memberaddress.MemberAddressService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.MemberAddressVO;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 收货地址
 * @author Xuejizheng
 * @date 2017-02-28 18:21
 * @see
 * @since 1.8
 */
@Service
@Transactional(readOnly = true)
public class MemberAddressServiceImpl implements MemberAddressService {

    private static Logger log = LoggerFactory.getLogger(MemberAddressServiceImpl.class);

    @Autowired
    private MemberAddressDao memberAddressDao;
    @Autowired
    private RpcMemberAddressService rpcMemberAddressService;

    /**
     * 获取收货地址列表
     * @param map
     * @return
     */
    @Override
    public Result getAddressList(Map<String, Object> map) {
        Result result = new Result();
        try {
            log.info(" member address list param:{}",map);
            Long uid =(Long) map.get("uid");
            if(uid==null){
                result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
                result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
                return result;
            }
            List<MemberAddressVO> list= memberAddressDao.getAddressList(map);
            int size =list.size();
            if (size!=0){
                result.setCode(StatusCodeEnums.SUCCESS.getCode());
                result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
                result.setContent(list);
            } else {
                result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
                result.setMsg("当前没有添加收货地址");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
        } finally {
            return result;
        }
    }

    /**
     * 获取默认收货地址
     * @param map 参数Map
     * @return
     */
    @Override
    public Result getDefaultAddress(Map<String, Object> map) {
        Result result = new Result();
        try {
            log.info(" member address list param:{}",map);
            Long uid =(Long) map.get("uid");
            if(uid==null){
                result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
                result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
                return result;
            }
             MemberAddressVO address= memberAddressDao.getDefaultAddress(map);
             if(address==null){
                 result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
                 result.setMsg("无默认地址信息");
             } else {
                 result.setCode(StatusCodeEnums.SUCCESS.getCode());
                 result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
                 result.setContent(address);
             }
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
        } finally {
            return result;
        }
    }

	@Override
	public Result saveAddress(MemberAddress memberAddress, Long mId, Long pId, Long cId) {
        if(memberAddress.getUserName() == null || memberAddress.getUserName().equals("")){
        	return ResultUtils.returnError("收货人姓名为空");
        }
        if(memberAddress.getUserName().length()>8){
        	return ResultUtils.returnError("收货人姓名不能超过8个字符");
        }
        String mobile = memberAddress.getMobile();
        if(mobile == null || mobile.equals("")){
        	return ResultUtils.returnError("手机号为空");
        }
		String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5,7]))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(mobile);  
        if(!m.matches()){
        	return ResultUtils.returnError("手机号格式有误");
        }
        if(memberAddress.getDetailAddress() == null || memberAddress.getDetailAddress().equals("")){
        	return ResultUtils.returnError("收货地址为空");
        }
        if(memberAddress.getDetailAddress().length()>50){
	    	 return ResultUtils.returnError("收货地址不能超过50字符");
	     }
        if(pId == null){
        	return ResultUtils.returnError("请选择省");
        }
        if(cId == null){
        	return ResultUtils.returnError("请选择市");
        }
		Member member = new Member();
		Area pro = new Area();
		Area city = new Area();
		member.setId(mId);
		pro.setId(pId);
		city.setId(cId);
		memberAddress.setMember(member);
		memberAddress.setCityArea(city);
		memberAddress.setProArea(pro);
		return this.rpcMemberAddressService.saveAddress(memberAddress);
	}

	@Override
	public Result updateAddress(MemberAddress memberAddress, Long mId, Long pId, Long cId) {
		 if(memberAddress.getUserName() == null || memberAddress.getUserName().equals("")){
	        return ResultUtils.returnError("收货人姓名为空");
	     }
		 if(memberAddress.getUserName().length()>8){
	        return ResultUtils.returnError("收货人姓名不能超过8个字符");
	     }
	     String mobile = memberAddress.getMobile();
	     if(mobile == null || mobile.equals("")){
	    	 return ResultUtils.returnError("手机号为空");
	     }
	     String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5,7]))\\d{8}$";   
	     Pattern p = Pattern.compile(regExp);  
	     Matcher m = p.matcher(mobile);  
	     if(!m.matches()){
	        return ResultUtils.returnError("手机号格式有误");
	     }
	     if(memberAddress.getDetailAddress() == null || memberAddress.getDetailAddress().equals("")){
	        return ResultUtils.returnError("收货地址为空");
	     }
	     if(memberAddress.getDetailAddress().length()>50){
	    	 return ResultUtils.returnError("收货地址不能超过50字符");
	     }
	     if(pId == null){
	        return ResultUtils.returnError("请选择省");
	     }
	     if(cId == null){
	        return ResultUtils.returnError("请选择市");
	     }
		MemberAddress address = this.memberAddressDao.findAddressById(memberAddress.getId());
		if(address.getMember() == null){
			return ResultUtils.returnError("收货地址异常");
		}
		Long id = address.getMember().getId();
		if(id.longValue() != mId.longValue()){
			return ResultUtils.returnError("用户信息异常");
		}
		Member member = new Member();
		Area pro = new Area();
		Area city = new Area();
		member.setId(mId);
		pro.setId(pId);
		city.setId(cId);
		memberAddress.setMember(member);
		memberAddress.setCityArea(city);
		memberAddress.setProArea(pro);
		return this.rpcMemberAddressService.updateAddress(memberAddress);
	}

	@Override
	public Result delAddressById(MemberAddress memberAddress, Long mId) {
		MemberAddress address = this.memberAddressDao.findAddressById(memberAddress.getId());
		if(address.getMember() == null){
			return ResultUtils.returnError("收货地址异常");
		}
		Long id = address.getMember().getId();
		if(id.longValue() != mId.longValue()){
			return ResultUtils.returnError("用户信息异常");
		}
		Member member = new Member();
		member.setId(mId);
		memberAddress.setMember(member);
		return this.rpcMemberAddressService.delAddressById(memberAddress);
	}

	@Override
	public Result findAddressById(MemberAddress memberAddress, Long mId) {
		MemberAddress address = this.memberAddressDao.findAddressById(memberAddress.getId());
		if(address.getMember() == null){
			return ResultUtils.returnError("收货地址异常");
		}
		Long id = address.getMember().getId();
		List<MemberAddress> list = new ArrayList<MemberAddress>();
		if(id.longValue() != mId.longValue()){
			return ResultUtils.returnError("用户信息异常");
		}
		list.add(address);
		return ResultUtils.returnSuccess("查询成功", list);
	}

    @Override
    @Transactional
    public Result update(Long uid, Long aid) {
        if(Objects.isNull(uid) || Objects.isNull(aid)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg(),
                    StatusCodeEnums.ERROR_PARAM.getCode());
        }
        try {
        MemberAddress memberAddress =rpcMemberAddressService.get(aid);
        if (memberAddress==null){
            return ResultUtils.returnError("收货地址不存在");
        }
        Member member= memberAddress.getMember();
        if (member == null){
            return ResultUtils.returnError("收货地址对应用户信息不存在.");
        }
        Long memberId = member.getId();
        if(!uid.equals(memberId)){
            return ResultUtils.returnError("收货地址和用户信息不匹配.");
        }
        int isDefalut=memberAddress.getIsDefault();
        if (isDefalut==1){
            return ResultUtils.returnError("此收货地址已经为默认");
        }
        memberAddress.setIsDefault(1);

        //更新为非默认
        boolean success=rpcMemberAddressService.updateBatchNotDefault(memberId);
        if(success){
            rpcMemberAddressService.saveAndModify(memberAddress);
            return ResultUtils.returnError(StatusCodeEnums.SUCCESS.getMsg(),
                    StatusCodeEnums.SUCCESS.getCode()) ;
        }
        return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg(),
                StatusCodeEnums.ERROR.getCode()) ;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg(),
                    StatusCodeEnums.ERROR.getCode()) ;
        }
    }

	@Override
	public Map<String, Object> getDefAddByMid(Long id) {
		Map<String, Object> defAddByMid = this.memberAddressDao.getDefAddByMid(id);
		if(!(defAddByMid == null || defAddByMid.isEmpty())){
			String detail_address = defAddByMid.get("detail_address").toString();
			String proName = defAddByMid.get("proName").toString();
			String cityName = defAddByMid.get("cityName").toString();
			detail_address = proName+cityName+detail_address;
			defAddByMid.put("detail_address", detail_address);
		}
		return defAddByMid;
	}
}
