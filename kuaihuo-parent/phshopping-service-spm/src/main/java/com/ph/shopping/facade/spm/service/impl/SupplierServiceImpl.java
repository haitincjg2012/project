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
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.dto.SupplierImageDTO;
import com.ph.shopping.facade.spm.entity.PermissionCommonUser;
import com.ph.shopping.facade.spm.entity.Supplier;
import com.ph.shopping.facade.spm.entity.SupplierImage;
import com.ph.shopping.facade.spm.entity.UserBalance;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.supplierExtion;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.supplierIsDelete;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.supplierIsFrozen;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.supplierStatus;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 何文浪
 * @version 2.1
 * @项目 phshopping-service-spm
 * @描述 供应商业务层
 * @时间 2017-5-12
 */
@Component
@Service(version = "1.0.0")
public class SupplierServiceImpl implements ISupplierService {
    // 日志
    private final static Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);
    //初始化供应商持久化
    @Autowired
    private SupplierMapper supplierMapper;
    //初始化供应商图片持久化
    @Autowired
    private SupplierImageMapper supplierImageMapper;
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
	private IProductService iProductService;
	//登录人业务层
	@Reference(version = "1.0.0")
	private IUserService userService;

    @Override
    public List<SupplierVO> getSupplierListDateil(SupplierDTO supplierDTO) {
        return supplierMapper.getSupplierListDateil(supplierDTO);
    }

    @Override
    public List<SupplierVO> getSupplierList(SupplierDTO supplierDTO) {
        return supplierMapper.getSupplierList(supplierDTO);
    }

    @Override
    public Result getSupplierPage(PageBean pageBean, SupplierDTO supplierDTO) {
        //分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Map> list = supplierMapper.getSupplierListMap(supplierDTO);
       // List<SupplierVO> list = supplierMapper.getSupplierList(supplierDTO);获取
//        if(list == null || list.size()<=0){
//        	return new Result(false, supplierExtion.GET.getIndex(),supplierExtion.GET.getName(), list,
//                    0);
//        }else{
	        //PageInfo<SupplierVO> pageInfo = new PageInfo<SupplierVO>(list);
           PageInfo<Map> pageInfo = new PageInfo<Map>(list);
	        return new Result(true, supplierExtion.GET.getIndex(), "", pageInfo.getList(),
	        		pageInfo.getTotal());
//        }
    }

    @Override
    public SupplierVO getSupplierListDateilById(SupplierDTO supplierDTO) {
        List<SupplierVO> list = supplierMapper.getSupplierListDateil(supplierDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SupplierVO getHunterListDateilById(SupplierDTO supplierDTO) {
        List<SupplierVO> list = supplierMapper.getHunterListDateil(supplierDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SupplierVO getSupplierListById(SupplierDTO supplierDTO) {
        List<SupplierVO> list = supplierMapper.getSupplierList(supplierDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    //验证至少三张图片 营业执照 法人身份证 不满足条件result.success 设为true
    private Result validateSupplierImg(SupplierDTO supplierDTO) {
        Result result = new Result(false);
        //验证图片不能为空
        if (supplierDTO.getSupplierImageDTOList() == null) {
            result.setSuccess(true);
            result.setMessage("供应商图片不能为空");
        } else {
            //至少三张图片 营业执照 法人身份证 门店照片
            List<Byte> imgTypes = supplierDTO.getSupplierImageDTOList().stream().map(SupplierImageDTO::getType).collect(Collectors.toList());
            for (SPMEnum.supplierImageType supplierImageType : SPMEnum.supplierImageType.values()) {
                if (!imgTypes.contains(supplierImageType.getIndex())) {
                    result.setMessage(supplierImageType.getName() + "不能为空");
                    result.setSuccess(true);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Result updateSupplier(SupplierDTO supplierDTO) {
        Result result = new Result();
        try {
            //验证登录人不能为空
            if (supplierDTO.getUpdaterId() == null) {
                result.setMessage("修改人不能为空");
            } else {
                //验证图片不能为空
                Result validateMerchantImg = this.validateSupplierImg(supplierDTO);
                if (validateMerchantImg.isSuccess()) {
                    result.setMessage(validateMerchantImg.getMessage());
                } else {
                    //更新供应商
                    Supplier supplier = this.supplierMapper.selectByPrimaryKey(supplierDTO.getId());
                    VoDtoEntityUtil.copyPropertiesNotNull(supplierDTO, supplier);
                    supplier.basic(supplierDTO.getUpdaterId());
                    supplier.setStatus(SPMEnum.supplierStatus.ZERO.getIndex());
                    supplier.setStatusLevel(SPMEnum.agentLevel.ADMIN.getIndex());
                    this.supplierMapper.updateByPrimaryKey(supplier);
                    //删除之前的图片
                    SupplierImage supplierImage = new SupplierImage();
                    supplierImage.setSupplierId(supplier.getId());
                    this.supplierImageMapper.delete(supplierImage);
                    //保存供应商图片
                    Date createTime = new Date();
                    for (SupplierImageDTO supplierImageDTO : supplierDTO.getSupplierImageDTOList()) {
                        this.supplierImgInit(supplierImageDTO, supplierDTO.getId(), createTime);
                    }

                    List<SupplierImage> list = VoDtoEntityUtil.convertList(supplierDTO.getSupplierImageDTOList(), SupplierImage.class);
                    this.supplierImageMapper.insertList(list);
                    result.setMessage("供应商修改成功");
                    result.setSuccess(true);
                }
            }
        } catch (Exception e) {
            result.setMessage(new SPMEnum(SPMEnum.supplierExtion.UPDATE.getName(), SPMEnum.supplierExtion.UPDATE.getIndex()).getMsg());
            throw e;
        }
        return result;
    }
    @Override
    public Result updateSupplierByStatus(SupplierDTO supplierDTO) {
        Result result = new Result();
        Supplier supplier = new Supplier();
        try {
            //验证登录人不能为空
            if (supplierDTO.getUpdaterId() == null || supplierDTO.getUpdaterId() <= 0 ) {
                result.setMessage("修改人不能为空");
            } else {
            		BeanUtils.copyProperties(supplierDTO, supplier);
                //修改供应商
                Integer returnValue = supplierMapper.updateByPrimaryKeySelective(supplier);
            		if(returnValue>0){
                        //当是冻结和暂冻的时候下架商品
                        if((supplierDTO.getIsFrozen() != null && supplierDTO.getIsFrozen() == supplierIsFrozen.ONE.getIndex()) || (supplierDTO.getIsFrozen() != null && supplierDTO.getIsFrozen() == supplierIsFrozen.TWO.getIndex())){
            				   ProductDTO productDto =new ProductDTO();
            		    	   productDto.setSupplierId(supplierDTO.getId());
                            //下架商品
                            Integer product =  iProductService.updateForSupplier(productDto);
            		    	 if(product>0){
            		    		 result.setMessage("操作成功!");
                                 result.setSuccess(true);
            		    	 }
                            //当删除的时候删除登录账号和供应商余额
                        }else if(supplierDTO.getIsDelete() != null && supplierDTO.getIsDelete() == supplierIsDelete.ONE.getIndex()){
            				 User user = new User();
	        				  user.setId(supplierDTO.getUserId());
                            //删除登录账号
                            result = deleteUser(user);
	        				  if(result.isSuccess()){
                                  //删除余额
                                  deleteUserBalance(supplierDTO.getUserId());
	        				  }
                            //审核通过的时候初始化密码
                        }else if(supplierDTO.getStatus() != null && supplierDTO.getStatus() == supplierStatus.ONE.getIndex()){
            				  User user = new User();
            				  user.setUpdaterId(supplierDTO.getUpdaterId());
            				  user.setId(supplierDTO.getUserId());
            				  result = userService.resetPassword(user, SmsSourceEnum.SUPPLIER, "uu");
            			}
            		}else{
            			result.setMessage(supplierExtion.OPERATING.getName());
            		}
                }
        } catch (Exception e) {
            result.setMessage(new SPMEnum(supplierExtion.OPERATING.getName(), supplierExtion.OPERATING.getIndex()).getMsg());
        }
        return result;
    }
    @Override
    public Result addSupplier(SupplierDTO supplierDTO) {
        Result result = new Result(false);
        //验证参数
        try {
            String validateMessage = supplierDTO.validateForm();
            if (validateMessage != null) {
                result.setMessage(validateMessage);
            } else {
                //验证创建人不能为空
                if (supplierDTO.getCreaterId() == null) {
                    result.setMessage("创建人不能为空");
                } else {
                    //验证图片不能为空
                    Result validateMerchantImg = this.validateSupplierImg(supplierDTO);
                    PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                    if (validateMerchantImg.isSuccess()) {
                        return validateMerchantImg;
                    } else {
                        permissionCommonUser.setTelphone(supplierDTO.getTelPhone());
                        if (this.permissionCommonUserMapper.selectOne(permissionCommonUser) != null) {
                            result.setMessage("该电话号码已经被创建");
                        } else {
                            //保存到登录表
                            permissionCommonUser.basic(supplierDTO.getCreaterId());
                            permissionCommonUser.setUserName(supplierDTO.getSupplierName());
                            permissionCommonUser.setIsable(UserConstant.START);
                            this.permissionCommonUserMapper.insertUseGeneratedKeys(permissionCommonUser);
                            //保存供应商
                            Supplier supplier = VoDtoEntityUtil.convert(supplierDTO, Supplier.class);
                            supplier.setUserId(permissionCommonUser.getId());
                            supplier.basic(supplierDTO.getCreaterId());
                            //待审核
                            supplier.setStatus(SPMEnum.supplierStatus.ZERO.getIndex());
                            //未冻结
                            supplier.setIsFrozen(SPMEnum.supplierIsFrozen.ZERO.getIndex());
                            //未删除
                            supplier.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
                            supplier.setStatusLevel(SPMEnum.agentLevel.ADMIN.getIndex());
                            this.supplierMapper.insertUseGeneratedKeys(supplier);
                            //保存商户图片
                            Date createTime = new Date();
                            for (SupplierImageDTO supplierImageDTO : supplierDTO.getSupplierImageDTOList()) {
                                this.supplierImgInit(supplierImageDTO, supplier.getId(), createTime);
                            }

                            List<SupplierImage> list = VoDtoEntityUtil.convertList(supplierDTO.getSupplierImageDTOList(), SupplierImage.class);
                            this.supplierImageMapper.insertList(list);
                            //余额初始化
                            UserBalance userBalance = new UserBalance();
                            userBalance.setScore(0L);
                            userBalance.setUserId(supplier.getUserId());
                            userBalance.setUserType(Integer.valueOf(RoleEnum.SUPPLIER.getCode()));
                            userBalance.basic(supplier.getCreaterId());
                            this.userBalanceMapper.insert(userBalance);
                            //权限初始化
                            UserRole userRole = new UserRole();
                            userRole.setRoleId(Long.valueOf(RoleEnum.SUPPLIER.getCode()));
                            userRole.setUserId(supplier.getUserId());
                            userRole.basic(supplier.getCreaterId());
                            this.userRoleMapper.insert(userRole);
                            result.setMessage("供应商保存成功");
                            result.setSuccess(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("新增供应商异常", e);
            throw e;
        }
        return result;
    }

    private void supplierImgInit(SupplierImageDTO supplierImageDTO, Long supplierId, Date createTime) {
        supplierImageDTO.setCreateTime(createTime);
        supplierImageDTO.setSort((byte) 0);
        supplierImageDTO.setCreateTime(createTime);
        supplierImageDTO.setUpdateTime(createTime);
        supplierImageDTO.setSupplierId(supplierId);
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

}
