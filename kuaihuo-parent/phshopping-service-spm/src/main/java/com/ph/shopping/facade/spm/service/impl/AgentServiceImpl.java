package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.permission.constant.UserConstant;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.entity.UserRole;
import com.ph.shopping.facade.permission.exception.PermissionBizException;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.AgentImageDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.entity.*;
import com.ph.shopping.facade.spm.exception.AgentException;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.*;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 何文浪
 * @version 2.1
 * @项目 phshopping-service-spm
 * @描述 代理商业务层
 * @时间 2017-5-15
 */
@Component
@Service(version = "1.0.0")
public class AgentServiceImpl implements IAgentService {
    // 日志
    private final static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
    //初始化代理商持久层
    @Autowired
    private AgentMapper agentMapper;
    //代理图片持久层
    @Autowired
    private AgentImageMapper agentImageMapper;
    //商户持久层
    @Autowired
    private MerchantMapper merchantMapper;
    //用户登录持久层
    @Autowired
    private PermissionCommonUserMapper permissionCommonUserMapper;
    //用户角色持久层
    @Autowired
    private UserRoleMapper userRoleMapper;
    //用户余额持久层
    @Autowired
    private UserBalanceMapper userBalanceMapper;
    
    
	@Reference(version = "1.0.0")
	private IUserService userService;
    

    @Override
    public List<AgentVO> getAgentListDateil(AgentDTO agentDTO) {
        return agentMapper.getAgentListDateil(agentDTO);
    }

    @Override
    public List<AgentVO> getAgentList(AgentDTO agentDTO) {
        return agentMapper.getAgentList(agentDTO);
    }

    @Override
    public List<AgentVO> getAgentPositionList(AgentDTO agentDTO) {
        return agentMapper.getPositionGetAgentList(agentDTO);
    }
    
    @Override
    public List<AgentVO> getAgentChildList(AgentDTO agentDTO) {
        List<AgentVO> list = new ArrayList<>();
        List<AgentVO> listReturn = agentMapper.getAgentList(agentDTO);
        if (listReturn.size() > 0) {
            for (AgentVO vo : listReturn) {
                list.add(VoDtoEntityUtil.convert(vo, AgentVO.class));
                AgentDTO agent = new AgentDTO();
                agent.setParentId(vo.getId());
                list.addAll(returnList(agent));
            }
        }
        return list;
    }
    @Override
    public List<AgentVO> getPositionGetNextAgentList(AgentDTO agentDTO) {
    	AgentDTO dto = null;
    	List<AgentVO> listReturn = new ArrayList<AgentVO>();
    	List<AgentVO> list = agentMapper.getAgentList(agentDTO);
    	if(list!=null && list.size()>0){
    		for(AgentVO vo:list){
    			dto = new AgentDTO();
//    			dto.setStatus(agentStatus.ONE.getIndex());
    			dto.setIsDelete(agentIsDelete.ZERO.getIndex());
//    			dto.setIsFrozen(agentIsFrozen.ZERO.getIndex());
    			dto.setProvinceId(vo.getProvinceId());
    			dto.setCityId(vo.getCityId());
    			dto.setCountyId(vo.getCountyId());
    			dto.setTownId(vo.getTownId());
    			dto.setIsFormSystem(vo.getIsFormSystem());
    			List<AgentVO> getList = agentMapper.getPositionGetNextAgentList(dto);
    			if(getList!=null && getList.size()>0)
    				listReturn.addAll(getList);
    		}
    	}
    	return listReturn;
    }


    @Override
    public Result getAgentPage(PageBean pageBean, AgentDTO agentDTO) {
        //分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
       List<AgentVO> list = agentMapper.getAgentList(agentDTO);
      //  List<Map> list = agentMapper.getAgentListMap(agentDTO);
//        if(list == null || list.size()<=0){
//        	return new Result(false, agentExtion.GET.getIndex(), agentExtion.GET.getName(), list,
//                    0);
//        }else{
	        PageInfo<AgentVO> pageInfo = new PageInfo<AgentVO>(list);
      //  PageInfo<Map> pageInfo = new PageInfo<Map>(list);
	        return new Result(true, agentExtion.GET.getIndex(), "", pageInfo.getList(),
	                pageInfo.getTotal());
//        }
    }

    @Override
    public AgentVO getAgentVODateilById(AgentDTO agentDTO) {
        List<AgentVO> list = agentMapper.getAgentListDateil(agentDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AgentVO getAgentVOListById(AgentDTO agentDTO) {
        List<AgentVO> list = agentMapper.getAgentList(agentDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AgentVO getAgentByMerchant(AgentDTO agentDTO) {
        List<AgentVO> list = agentMapper.getAgentByMerchantList(agentDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Result addAgent(AgentDTO agentDTO) {
        Result result = new Result(false);
        //验证参数
        try {
            String validateMessage = agentDTO.validateForm();
            if (validateMessage != null) {
                result.setMessage(validateMessage);
            } else {
                //验证创建人不能为空
                if (agentDTO.getCreaterId() == null) {
                    result.setMessage("创建人不能为空");
                } else {
                    //验证图片不能为空
                    Result validateAgentImg = this.validateAgentImg(agentDTO);
                    PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                    if (validateAgentImg.isSuccess()) {
                        return validateAgentImg;
                    } else {
                        permissionCommonUser.setTelphone(agentDTO.getTelPhone());
                        if (this.permissionCommonUserMapper.selectOne(permissionCommonUser) != null) {
                            result.setMessage("该电话号码已经被创建");
                        } else {
                            //查询当前区域是否已经有代理
                            AgentDTO existAgent = new AgentDTO();
                            existAgent.setTownId(agentDTO.getTownId());
                            existAgent.setCountyId(agentDTO.getCountyId());
                            existAgent.setCityId(agentDTO.getCityId());
                            List<AgentVO> agents = this.agentMapper.getAgentVoByPosition(existAgent);
                            if (agents.stream().anyMatch(agent -> agent.getAgentLevelId().equals(agentDTO.getAgentLevelId()))) {
                                result.setMessage("该地区已经存在代理");
                            } else {
                                //保存到登录表
                                permissionCommonUser.basic(agentDTO.getCreaterId());
                                permissionCommonUser.setUserName(agentDTO.getAgentName());
                                permissionCommonUser.setIsable(UserConstant.START);
                                this.permissionCommonUserMapper.insertUseGeneratedKeys(permissionCommonUser);
                                //保存代理
                                Agent agent = VoDtoEntityUtil.convert(agentDTO, Agent.class);
                                agent.setUserId(permissionCommonUser.getId());
                                agent.basic(agentDTO.getCreaterId());
                                //待审核
                                agent.setStatus(SPMEnum.agentStatus.ZERO.getIndex());
                                //未冻结
                                agent.setIsFrozen(SPMEnum.agentIsFrozen.ZERO.getIndex());
                                //未删除
                                agent.setIsDelete(SPMEnum.agentIsDelete.ZERO.getIndex());
                                //代理设置parentId
                                Long parentId = 0L;
                                // agents 是该区域所有代理 已经按照agentLevel从大到小排序
                                for (AgentVO agentVO : agents) {
                                    if (agentVO.getAgentLevelId() < agentDTO.getAgentLevelId()) {
                                        parentId = agentVO.getId();
                                        break;
                                    }
                                }
                                agent.setParentId(parentId);
                                this.agentMapper.insertUseGeneratedKeys(agent);
                                //所有所有区域商户 重置agentId
                                this.resetMerchantAgentId(agent, agentDTO.getStatusLevel());
                                //保存商户图片
                                Date createTime = new Date();
                                for (AgentImageDTO agentImageDTO : agentDTO.getAgentImageDTOList()) {
                                    this.agentImgInit(agentImageDTO, agent.getId(), createTime);
                                }
                                List<AgentImage> list = VoDtoEntityUtil.convertList(agentDTO.getAgentImageDTOList(), AgentImage.class);
                                this.agentImageMapper.insertList(list);
                                //余额初始化
                                UserBalance userBalance = new UserBalance();
                                userBalance.setScore(0L);
                                userBalance.setUserId(agent.getUserId());
                                userBalance.setUserType(Integer.valueOf(RoleEnum.SUPPLIER.getCode()));
                                userBalance.basic(agent.getCreaterId());
                                this.userBalanceMapper.insert(userBalance);
                                //权限初始化
                                UserRole userRole = new UserRole();
                                RoleEnum roleEnum = null;
                                SPMEnum.agentLevel agentLevel = SPMEnum.agentLevel.getAgentLevelByIndex(Byte.valueOf(agent.getAgentLevelId() + ""));
                                if (agentLevel == null) {
                                    logger.error("错误的代理商类型:{}", JSON.toJSONString(agent));
                                    throw new AgentException("增加代理异常,错误的代理商类型");
                                }
                                if(agentDTO.getIsFormSystem()==0){
                                    switch (agentLevel) {
                                        case CITY_AGENT:
                                            roleEnum = RoleEnum.CITY_AGENT;
                                            break;
                                        case COUNTY_AGENT:
                                            roleEnum = RoleEnum.COUNTY_AGENT;
                                            break;
                                        case COMMUNITY_AGENT:
                                            roleEnum = RoleEnum.COMMUNITY_AGENT;
                                            break;
                                    }
                                }
                                if(agentDTO.getIsFormSystem()==1){
                                    switch (agentLevel) {
                                        case CITY_AGENT:
                                            roleEnum = RoleEnum.WHOLESALE_CITY_AGENT;
                                            break;
                                        case COUNTY_AGENT:
                                            roleEnum = RoleEnum.WHOLESALE_COUNTY_AGENT;
                                            break;
                                        case COMMUNITY_AGENT:
                                            roleEnum = RoleEnum.WHOLESALE_COMMUNITY_AGENT;
                                            break;
                                    }
                                }

                                userRole.setRoleId(Long.valueOf(roleEnum.getCode()));
                                userRole.setUserId(agent.getUserId());
                                userRole.basic(agent.getCreaterId());
                                this.userRoleMapper.insert(userRole);
                                result.setMessage("代理商保存成功");
                                result.setSuccess(true);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("增加代理商异常", e);
            throw e;
        }
        return result;
    }

    @Transactional
    @Override
    public Result updateAgent(AgentDTO agentDTO) {
        Result result = new Result();
        try {
            if (agentDTO.getUpdaterId() == null || agentDTO.getUpdaterId() <= 0) {
                result.setMessage("修改人不能为空!");
            } else {
                //扩展代理商修改
                result = updateAgentExtends(agentDTO);
                if(result.isSuccess() && agentDTO.getIsDelete() != null && agentDTO.getIsDelete() == agentIsDelete.ONE.getIndex()){
                    User user = new User();
                    user.setId(agentDTO.getUserId());
                    result = deleteUser(user);
                    if (result.isSuccess()) {
                    	deleteUserBalance(agentDTO.getUserId());
                    }
                }else if(result.isSuccess() && agentDTO.getStatus() != null && agentDTO.getStatus()==agentStatus.ONE.getIndex()){
            			User user = new User();
            			user.setUpdaterId(agentDTO.getUpdaterId());
            			user.setId(agentDTO.getUserId());
            			result = userService.resetPassword(user, SmsSourceEnum.AGENT, "uu");
                }

                //冻结重置下级代理关系
                if (!(agentDTO.getIsFrozen() == null)) {
                    if (agentIsFrozen.ONE.getIndex() == agentDTO.getIsFrozen()) {
                        this.resetAgent(agentDTO);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("修改代理商异常:", e.fillInStackTrace());
            result.setMessage(new SPMEnum(SPMEnum.agentExtion.UPDATE.getName(), SPMEnum.agentExtion.UPDATE.getIndex()).getMsg());
        }
        return result;
    }

    /**
     * 无限查询子级(只是这个类里面的方法调用)
     *
     * @param vo
     * @return List<AgentVO>
     * @author 何文浪
     * @时间 2017-5-27
     */
    public List<AgentVO> returnList(AgentDTO vo) {
        List<AgentVO> list = new ArrayList<AgentVO>();
        List<AgentVO> pList = agentMapper.getAgentChildList(vo);
        while (pList.size() > 0) {
            list.addAll(pList);
            for (AgentVO agentVo : pList) {
                AgentDTO agent = new AgentDTO();
                agent.setParentId(agentVo.getId());
                pList = agentMapper.getAgentChildList(agent);
            }
        }
        return list;
    }

    /**
     * 扩展代理商业修改
     *
     * @param agentDTO
     * @return Result
     * @author 何文浪
     * @时间 2017-6-2
     */
    public Result updateAgentExtends(AgentDTO agentDTO) {
        AgentDTO agentExtendsDTO = new AgentDTO();
        Agent agent = new Agent();
        Result result = new Result();
        Integer count = 0;
        //copy属性值
        BeanUtils.copyProperties(agentDTO, agent);
        //设置值
        agentExtendsDTO.setId(agentDTO.getId());
        //查询当前进来的代理商
        List<AgentVO> list = agentMapper.getAgentList(agentExtendsDTO);
        //验证是否存在当前代理商
        if (list.size() > 0) {
            //判断当前区域当前等级的代理是否已经存在，用于解冻和审核通过的操作
            count = getAgentCount(list.get(0));
            //代理商逻辑处理操作
            agent = agentLogicalProcessing(list.get(0), agent, count);
        }
        //当前级只有一个或者没有时操作，用于解冻和审核通过的操作
        if (count <= 1) {
            Integer id = agentMapper.updateByPrimaryKeySelective(agent);
            //更改成功
            if (id > 0) {
                result.setSuccess(true);
                result.setMessage("操作成功!");
            } else {
                result.setMessage(new SPMEnum(SPMEnum.agentExtion.THAW.getName(), SPMEnum.agentExtion.THAW.getIndex()).getMsg());
            }
        } else {
            result.setMessage(new SPMEnum(SPMEnum.agentExtion.ENTITY_HAVE.getName(), SPMEnum.agentExtion.ENTITY_HAVE.getIndex()).getMsg());
        }
        return result;
    }

    /**
     * 当是市级的时候自动挂社区数据
     * 市代被冻结，自动将社区的父级往上级关联
     * @param agent,agentDTO,count
     * @return Agent
     * @author 何文浪
     * @时间 2017-6-2
     */
    public void isChildAgent(AgentDTO dto,Agent agent){
       	if(dto.getAgentVoList() != null && dto.getAgentVoList().size()>0){
       		for(AgentVO voChild : dto.getAgentVoList()){
                if (voChild.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()) {
                    List<Long> parentIds = new ArrayList<Long>();
                    parentIds.add(voChild.getId());
                    dto = getAgentParentAndChild(voChild,agent);//找出社区的所属父级（社区没有子级）
       				dto.setParentIds(parentIds);
       				if (dto.getParentIds() != null && dto.getParentIds().size() > 0) {
	       			   //修改子级的父节点
                        agentMapper.updateAgentChildByParent(dto);
                    }
                }
       		}
       }
   }
    /**
     * 代理商业务逻辑处理
     *
     * @param vo 数据库查询的当前操作数据的信息
     * @param agent 前端传过来的参数，Copy的DTO
     * @param agent,count
     * @return Agent
     * @author 何文浪
     * @时间 2017-6-2
     */
    public Agent agentLogicalProcessing(AgentVO vo, Agent agent, Integer count) {
        MerchantDTO merchantDTO = new MerchantDTO();
        //获取当前代理商的子级和父级
        AgentDTO dto = getAgentParentAndChild(vo,agent);//设置当前代理的上下级，parentId和parentIds（这里代表子级）
        //当操作冻结状态时
        if (agent.getIsFrozen() != null && agent.getIsFrozen() == agentIsFrozen.ONE.getIndex()) {
        	isChildAgent(dto,agent);//如果是操作的代理时市级，并且有社区与其关联时，单独挂载上下级
            //如果子级存在
            if (dto.getParentIds() != null && dto.getParentIds().size() > 0) {
                //修改子级的父节点（将当前操作代理商的所有子级关联到他的父级上）
                agentMapper.updateAgentChildByParent(dto);
            }
            //根据代理商id查找商户
            merchantDTO = getMerchantByList(vo,agent);
            merchantDTO.setAgentId(vo.getParentId());
//            if(agent.getIsFrozen() == agentIsFrozen.ONE.getIndex()){
//            	agent.setParentId(0L);
//            }
            //商户是否存在
            if (merchantDTO.getIds() != null && merchantDTO.getIds().size() > 0 ) {
                merchantMapper.updateMerchantByAgent(merchantDTO);
            }
            //修改商户审核等级
            merchantDTO = getMerchatStatusLevel(vo,agent);
            //商户是否存在
            if (merchantDTO.getIsNotLevlIds() != null && merchantDTO.getIsNotLevlIds().size() > 0 ) {
                merchantMapper.updateMerchantByStatusLevle(merchantDTO);
            }
            //解冻、审核通过操作--查找有没有上级、下级
        } else if ((agent.getIsFrozen() != null && agent.getIsFrozen() == agentIsFrozen.ZERO.getIndex()) || (agent.getStatus() != null && agent.getStatus() == agentStatus.ONE.getIndex())) {
            if (count <= 1) {
            		isChildAgent(dto,agent);//如果是市级，自动挂载上下级
                if (dto.getParentId() != null) {
                    //商户
                    vo.setId(dto.getParentId());
                    //代理商
                    agent.setParentId(dto.getParentId());
                    dto.setParentId(agent.getId());
                    //判断是否存在子级
                    if (dto.getParentIds() != null && dto.getParentIds().size() > 0) {
                        agentMapper.updateAgentChildByParent(dto);
                    }
                    //商户
                    merchantDTO = getMerchantByList(vo,agent);
                    merchantDTO.setAgentId(agent.getId());
                    //判断所属商户是否存在
                    if (merchantDTO.getIds() != null && merchantDTO.getIds().size() > 0) {
                        merchantMapper.updateMerchantByAgent(merchantDTO);
                    }
                }
            }
        }
        return agent;
    }

    /**
     * 查找商户审核等级
     * @param vo
     * @param agent
     * @return
     */
    public MerchantDTO getMerchatStatusLevel(AgentVO vo, Agent agent){
    	AgentDTO agentDTO = new AgentDTO();
    	MerchantDTO merchantDTO = new MerchantDTO();
    	 merchantDTO.setProvinceId(vo.getProvinceId());
         merchantDTO.setCityId(vo.getCityId());
         if (vo.getAgentLevelId() != 1)
             merchantDTO.setCountyId(vo.getCountyId());//县id
         if (vo.getAgentLevelId() == 3)
             merchantDTO.setTownId(vo.getTownId());//社区id
    	merchantDTO.setStatusLevel(vo.getAgentLevelId().byteValue());
        List<MerchantVO> merchantList = merchantMapper.getMerchantList(merchantDTO);//首先以代理商去查找
        List<Long> idsIsNotLevl = new ArrayList<Long>();
        agentDTO.setId(vo.getParentId());
        List<AgentVO> list= agentMapper.getAgentList(agentDTO);
        if(merchantList!=null && merchantList.size()>0){
	        //代理商是冻结状态2017-7-1 hewl添加
			for(MerchantVO merchantVO :merchantList){
	    		if(agent.getIsFrozen() == agentIsFrozen.ONE.getIndex()){
				//判断当前商户没有审核通过和不是在管理员下等待审核时
		    		if(merchantVO.getStatus() != merchantStatus.ONE.getIndex() && merchantVO.getStatusLevel() != agentLevel.ADMIN.getIndex()){
		    			if(vo.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){
		    				merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
		        		}else if(vo.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()){
		        			if(list!=null && list.size()>0){
		        				if(list.get(0).getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){
		        					merchantDTO.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
		        				}else {
		        					merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
		        				}
		    				}else{
		    					merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
		    				}
		        		}else if(vo.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()){
		        			if(list!=null && list.size()>0){
		        				if(list.get(0).getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){
		        					merchantDTO.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
		        				}else if(list.get(0).getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()){
		        					merchantDTO.setStatusLevel(agentLevel.COUNTY_AGENT.getIndex());
		        				}else {
		        					merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
		        				}
		    				}else{
		    					merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
		    				}
		        		}
		        		//这是需要更改statusLevel
		        		idsIsNotLevl.add(merchantVO.getId());
			        	}
			        }
		        }
			merchantDTO.setIsNotLevlIds(idsIsNotLevl);
        }
        return merchantDTO;
    }
    /**
     * 根据代理商查找商户
     *
     * @param vo
     * @return MerchantDTO
     * @author 何文浪
     * @时间 2017-6-5
     */
    public MerchantDTO getMerchantByList(AgentVO vo,Agent agent) {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setAgentId(vo.getId());//查找所属商户
        merchantDTO.setProvinceId(vo.getProvinceId());
        merchantDTO.setCityId(vo.getCityId());
        if (vo.getAgentLevelId() != 1)
            merchantDTO.setCountyId(vo.getCountyId());//县id
        if (vo.getAgentLevelId() == 3)
            merchantDTO.setTownId(vo.getTownId());//社区id
        List<MerchantVO> merchantList = merchantMapper.getMerchantList(merchantDTO);//首先以代理商去查找
        List<Long> ids = new ArrayList<Long>();
        if (merchantList != null && merchantList.size() > 0) {
            for (MerchantVO merchantVO : merchantList) {
            	ids.add(merchantVO.getId());
            	//end 2017-7-1 hewl添加
            }
            merchantDTO.setIds(ids);
        } else {//否则则通过省市区查找
            merchantDTO.setAgentId(0L);
            List<MerchantVO> merchantVOList = merchantMapper.getMerchantList(merchantDTO);
            if (merchantList != null && merchantList.size() > 0) {
                for (MerchantVO merchantVO : merchantVOList) {
            		//这是需要改上下关联
            		ids.add(merchantVO.getId());
                	//end 2017-7-1 hewl添加
                }
                merchantDTO.setIds(ids);
            }
        }
        return merchantDTO;
    }
    /**
     * 判断是否存在当前级代理
     *
     * @param vo
     * @return Integer
     * @author 何文浪
     * @时间 2017-6-2
     */
    public Integer getAgentCount(AgentVO vo) {
        //给dto赋值
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setProvinceId(vo.getProvinceId());
        agentDTO.setCityId(vo.getCityId());
        agentDTO.setIsFrozen(agentIsFrozen.ZERO.getIndex());
        agentDTO.setIsDelete(agentIsDelete.ONE.getIndex());
        if (vo.getAgentLevelId() != 1) {
            agentDTO.setCountyId(vo.getCountyId());
        }
        if (vo.getAgentLevelId() == 3) {
            agentDTO.setTownId(vo.getTownId());
        }
        agentDTO.setAgentLevelId(vo.getAgentLevelId());
        //查询当前代理商
        List<AgentVO> returnList = agentMapper.getAgentList(agentDTO);
        if (returnList.size() > 0) {
            return returnList.size();
        } else {
            return 0;
        }
    }

    /**
     * 查询当前级子级、父级(只是这个类里面的方法调用)
     * 此方法只把当前操作的代理商的子级和父级找出来，设置到传入的代理商对象里
     * @param voOld/AgentDTO
     * @return AgentDTO
     * @author 何文浪
     * @时间 2017-6-1
     */
    public AgentDTO getAgentParentAndChild(AgentVO voOld, Agent oldAgent) {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setProvinceId(voOld.getProvinceId());
        agentDTO.setCityId(voOld.getCityId());
        agentDTO.setCountyId(voOld.getCountyId());//县id
        agentDTO.setTownId(voOld.getTownId());//社区id
        agentDTO.setIsDelete(agentIsDelete.ZERO.getIndex());//是否删除
        agentDTO.setStatus(agentStatus.ONE.getIndex());//是否审核通过
        agentDTO.setIsFrozen(agentIsFrozen.ZERO.getIndex());//是否暂冻或者冻结
        List<Long> ids = new ArrayList<Long>();
        //查找当前区域所有代理商
        List<AgentVO> agentAllList = agentMapper.getPositionGetAllAgentList(agentDTO);//父级
        if(agentAllList.size()>0){//当查找有数据时
        	List<AgentVO> listAgent = new ArrayList<AgentVO>();
        	for(AgentVO vo:agentAllList){
    			if(voOld.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){//市级代理
    				if(vo.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()){//县级代理
     					ids.add(vo.getId());//设置当前操作代理的县级子级
    				}
    				if(vo.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()){//社区代理
    					listAgent.add(vo);//操作市代，并且有社区与之关联时，后面单独处理
    					if(oldAgent.getIsFrozen() != null && oldAgent.getIsFrozen() == agentIsFrozen.ZERO.getIndex() && vo.getParentId()<=0){
    						ids.add(vo.getId());//设置当前操作代理的社区子级
    					}
    				}
        		}else if(voOld.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()){//县级代理
        			if(vo.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){//市级代理
        					agentDTO.setParentId(vo.getId());//设置上级为市级
    				}
        			if(vo.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()){//社区代理
    					ids.add(vo.getId());
    				}
        		}else if(voOld.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()){//社区代理
        			if(vo.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()){//县级代理
        				agentDTO.setParentId(vo.getId());//设置上级为县级
    				}else if(vo.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()){//市级代理
            				agentDTO.setParentId(vo.getId());//设置上级为市级
        			}
        		}
        	}
        	if(agentDTO.getParentId()==null){
        		agentDTO.setParentId(0L);//默认上级为平台
        	}
        	agentDTO.setParentIds(ids);//塞进子级id
        	agentDTO.setAgentVoList(listAgent);//市级的时候存在社区代理
        }else{
        	agentDTO.setParentId(0L);
        }
        return agentDTO;
    }

    @Transactional
    @Override
    public Result updateAgentAndImg(AgentDTO agentDTO) {
        Result result = new Result(false);
        //验证参数
        try {
            //验证修改人不能为空
            if (agentDTO.getUpdaterId() == null) {
                result.setMessage("修改人不能为空");
            } else {
                //验证图片不能为空
                Result validateAgentImg = this.validateAgentImg(agentDTO);
                if (validateAgentImg.isSuccess()) {
                    result.setMessage(validateAgentImg.getMessage());
                } else {
                    //查询当前区域是否已经有代理
                    AgentDTO existAgent = new AgentDTO();
                    existAgent.setTownId(agentDTO.getTownId());
                    existAgent.setCountyId(agentDTO.getCountyId());
                    existAgent.setCityId(agentDTO.getCityId());
                    List<AgentVO> agents = this.agentMapper.getAgentVoByPosition(existAgent);
                    if (agents.stream().anyMatch(agent -> agent.getAgentLevelId().equals(agentDTO.getAgentLevelId())
                            && !agent.getId().equals(agentDTO.getId()))) {
                        result.setMessage("该地区已经存在代理");
                    } else {
                        PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                        //更新代理
                        Agent agent = this.agentMapper.selectByPrimaryKey(agentDTO.getId());
                        permissionCommonUser.setTelphone(agent.getTelPhone());
                        PermissionCommonUser pcu = this.permissionCommonUserMapper.selectOne(permissionCommonUser);
                        pcu.setTelphone(agentDTO.getTelPhone());
                        this.permissionCommonUserMapper.updateByPrimaryKeySelective(pcu);
                        VoDtoEntityUtil.copyPropertiesNotNull(agentDTO, agent);
                        agent.basic(agent.getUpdaterId());
                        //设置为待审核
                        agent.setStatus(agentStatus.ZERO.getIndex());
                        //代理设置parentId
                        Long parentId = 0L;
                        // agents 是该区域所有代理 已经按照agentLevel从大到小排序
                        for (AgentVO agentVO : agents) {
                            if (agentVO.getAgentLevelId() < agentDTO.getAgentLevelId()) {
                                parentId = agentVO.getId();
                                break;
                            }
                        }
                        agent.setParentId(parentId);
                        //所有所有区域商户 重置agentId
                        this.resetMerchantAgentId(agent, agentDTO.getStatusLevel());
                        this.agentMapper.updateByPrimaryKey(agent);
                        //所有下级代理重新设置
                        this.resetAgent(agentDTO);
                        //删除之前的图片
                        AgentImage agentImage = new AgentImage();
                        agentImage.setAgentId(agent.getId());
                        this.agentImageMapper.delete(agentImage);
                        //保存代理图片
                        Date createTime = new Date();
                        for (AgentImageDTO agentImageDTO : agentDTO.getAgentImageDTOList()) {
                            this.agentImgInit(agentImageDTO, agent.getId(), createTime);
                        }

                        List<AgentImage> list = VoDtoEntityUtil.convertList(agentDTO.getAgentImageDTOList(), AgentImage.class);
                        this.agentImageMapper.insertList(list);
                        result.setMessage("代理修改成功");
                        result.setSuccess(true);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("修改代理异常", e);
            throw e;
        }
        return result;
    }

    //验证至少两种图片 营业执照 法人身份证 不满足条件result.success 设为true
    private Result validateAgentImg(AgentDTO agentDTO) {
        Result result = new Result(false);
        //验证图片不能为空
        if (agentDTO.getAgentImageDTOList() == null) {
            result.setSuccess(true);
            result.setMessage("代理商图片不能为空");
        } else {
            List<Integer> imgTypes = agentDTO.getAgentImageDTOList().stream().map(AgentImageDTO::getType).collect(Collectors.toList());
            for (SPMEnum.agentImageType agentImageType : SPMEnum.agentImageType.values()) {
                //放开门店图片
                if (agentImageType.equals(SPMEnum.agentImageType.THREE)) {
                    continue;
                }
                if (!imgTypes.contains(Integer.valueOf(agentImageType.getIndex() + ""))) {
                    result.setMessage(agentImageType.getName() + "不能为空");
                    result.setSuccess(true);
                    break;
                }
            }
        }
        return result;
    }

    private void agentImgInit(AgentImageDTO agentImageDTO, Long agentId, Date createTime) {
        agentImageDTO.setCreateTime(createTime);
        agentImageDTO.setUpdateTime(createTime);
        agentImageDTO.setSort(0);
        agentImageDTO.setAgentId(agentId);
    }

    @Override
    public List<AgentVO> getAgentVoByPosition(Long cityId, Long countyId, Long townId) {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setCityId(cityId);
        agentDTO.setCountyId(countyId);
        agentDTO.setTownId(townId);
        return this.agentMapper.getAgentVoByPosition(agentDTO);
    }

    /**
     * 清空代理商余额
     * @author hewl
     * @param userId
     * @return Integer
     */
	public Integer deleteUserBalance(Long userId) {
        return userBalanceMapper.delUserBalance(userId);
    }

    /**
     * 删除用户
	 * @author 何文浪复制舒豪
	 * @param user
	 * @return
	 */
  public Result deleteUser(User user) {
      try {
    	  UserRole userRole = new UserRole();
      	  logger.info("删除用户入参,user={}",JSON.toJSONString(user));
      	  permissionCommonUserMapper.deleteByPrimaryKey(user.getId());
      	  userRole.setUserId(user.getId());
      	  userRoleMapper.delete(userRole);
          return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
      } catch (Exception ex) {
          logger.error("删除用户异常，ex={}", ex);
          throw new PermissionBizException(PermissionEnum.DELETE_USER_ERROR);
      }
  }

    //修改代理重置该区域所有商户 代理id
    private void resetMerchantAgentId(Agent agent, Byte statusLevel) {
        //所有区域商户 重置agentId
        Merchant merchant = new Merchant();
        merchant.setCityId(agent.getCityId());
        List<Merchant> merchants = this.merchantMapper.select(merchant);
        for (Merchant _merchant : merchants) {
            AgentDTO parentAgent = new AgentDTO();
            parentAgent.setTownId(_merchant.getTownId());
            parentAgent.setCountyId(_merchant.getCountyId());
            parentAgent.setCityId(_merchant.getCityId());
            List<AgentVO> _parentAgent = this.agentMapper.getAgentVoByPosition(parentAgent);
            if (CollectionUtils.isEmpty(_parentAgent)) {
                _merchant.setAgentId(0L);
            } else {
                //如果商户未审核 重新设置审核等级
                if (!_merchant.getStatusLevel().equals(merchantStatus.ONE.getIndex())) {
                    _merchant.setStatusLevel(statusLevel);
                }
                _merchant.setAgentId(_parentAgent.get(0).getId());
            }
            this.merchantMapper.updateByPrimaryKey(_merchant);
        }
    }

    //修改下级代理 重置审核状态和上级id
    private void resetAgent(AgentDTO agentDTO) {
        agentDTO.setAgentLevelId(agentMapper.selectByPrimaryKey(agentDTO.getId()).getAgentLevelId());
        //所有待审核下级代理重新设置
        Agent childrenParams = new Agent();
        childrenParams.setCityId(agentDTO.getCityId());
        List<Agent> childrenAgents = this.agentMapper.select(childrenParams);
        for (Agent _agent : childrenAgents) {
            //代理等级大于等于当前代理不进行修改 只修改下级代理
            if (_agent.getAgentLevelId() <= agentDTO.getAgentLevelId()) {
                continue;
            }
            AgentDTO parent = new AgentDTO();
            parent.setTownId(_agent.getTownId());
            parent.setCountyId(_agent.getCountyId());
            parent.setCityId(_agent.getCityId());
            List<AgentVO> parents = this.agentMapper.getAgentVoByPosition(parent);
            if (!CollectionUtils.isEmpty(parents)) {
                for (AgentVO agentVO : parents) {
                    //如果寻找到agentLevel比当前更小的就设置为更小的
                    if (_agent.getAgentLevelId() > agentVO.getAgentLevelId()) {
                        if (!_agent.getStatus().equals(agentStatus.ONE.getIndex())) {
                            if (!(_agent.getStatusLevel() >= Byte.valueOf(agentVO.getAgentLevelId().toString()))) {
                                _agent.setStatusLevel(Byte.valueOf(agentVO.getAgentLevelId().toString()));
                            }
                        }
                        _agent.setParentId(agentVO.getId());
                        break;
                    }
                    _agent.setParentId(0L);
                    _agent.setStatusLevel(agentLevel.ADMIN.getIndex());
                }
            } else {
                _agent.setParentId(0L);
                _agent.setStatusLevel(agentLevel.ADMIN.getIndex());
            }
            this.agentMapper.updateByPrimaryKey(_agent);
        }
    }

    @Override
    public Agent getAgentById(Long id){
    	return this.agentMapper.getAgentById(id);
    }

}