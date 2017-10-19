package com.alqsoft.controller.mobile.after.memberaddress;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.service.memberaddress.MemberAddressService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 收货地址控制器
 * @author Xuejizheng
 * @date 2017-02-28 18:16
 * @see
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/after/memberaddress")
public class MemberAddressController {

    @Autowired
    private MemberAddressService memberAddressService;


    /**
     * 获取地址列表
     * @param member 登录用户
     * @return
     */
    @RequestMapping(value = "address-list",method = RequestMethod.POST)
    public Result getAddressList(@MemberAnno Member member){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",member.getId());
        Result result= memberAddressService.getAddressList(map);
        return result;
    }

    /**
     * 获取默认收货地址
     * @param  member 登录用户
     * @return
     */
    @RequestMapping(value = "default-address",method = RequestMethod.POST)
    public Result getDefaultAddress(@MemberAnno Member member){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",member.getId());
        Result result= memberAddressService.getDefaultAddress(map);
        return result;
    }

    /**
     * 设置默认收货地址
     * @param member   会员
     * @param aid  地址ID
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "set-default-address",method = RequestMethod.POST)
    public Result setDefaultAddress(@MemberAnno Member member,@RequestParam("aid")Long aid){

       return  memberAddressService.update(member.getId(),aid);

    }
    
    /**
     * 保存收货地址
     * @param memberAddress
     * @param mId
     * @param pId
     * @param cId
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "saveaddress",method = RequestMethod.POST)
    public Result saveAddress(@MemberAnno Member m, Member member, MemberAddress memberAddress, Long pId, Long cId){
    	try {
			Result result = this.memberAddressService.saveAddress(memberAddress, m.getId(), pId, cId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("添加地址异常");
		}
    }
    
    /**
     * 修改收货地址
     * @param memberAddress
     * @param mId
     * @param pId
     * @param cId
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "updateaddress",method = RequestMethod.POST)
    public Result updateAddress(@MemberAnno Member m, Member member, MemberAddress memberAddress, Long pId, Long cId){
    	try {
			Result result = this.memberAddressService.updateAddress(memberAddress, m.getId(), pId, cId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("修改地址异常");
		}
    }
    
    /**
     * 删除收货地址
     * @param memberAddress
     * @param mId
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "deladdressbyid",method = RequestMethod.POST)
    public Result delAddressById(@MemberAnno Member m, Member member, MemberAddress memberAddress){
    	try {
    		Result result = this.memberAddressService.delAddressById(memberAddress, m.getId());
    		return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultUtils.returnError("删除地址异常");
    	}
    }
    
    /**
     * 查询地址详情
     * @param memberAddress
     * @return
     */
    @RequestMapping(value = "findaddressbyid",method = RequestMethod.POST)
    public Result findAddressById(@MemberAnno Member m, Member member, MemberAddress memberAddress){
    	try {
    		Result result = this.memberAddressService.findAddressById(memberAddress, m.getId());
    		return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultUtils.returnError("查询异常");
    	}
    }
    
    
}
