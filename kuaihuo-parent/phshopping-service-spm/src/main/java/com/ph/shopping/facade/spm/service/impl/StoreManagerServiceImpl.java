package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.other.IdAuthService;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.StoreManagerMapper;
import com.ph.shopping.facade.member.dto.StoreDTO;
import com.ph.shopping.facade.member.dto.StoreImageDTO;
import com.ph.shopping.facade.member.dto.StoreManagerDTO;
import com.ph.shopping.facade.member.service.IStoreManagerService;
import com.ph.shopping.facade.member.vo.StoreManagerVO;
import com.ph.shopping.facade.member.vo.StoreMangerImageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;


/**
 * Created by wudi on 2017/9/25.
 */
@Service(version = "1.0.0")
public class StoreManagerServiceImpl implements IStoreManagerService {

    private static final Logger logger = LoggerFactory.getLogger(StoreManagerServiceImpl.class);

    @Autowired
    private IdAuthService idAuthService;
    @Autowired
    private StoreManagerMapper storeManagerMapper;
    @Override
    public Result getMerchantByPage(StoreManagerDTO dto,PageBean pageBean) {
        // 分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        Result result = new Result();
        try {
            List<Map> merchantByPage = storeManagerMapper.getMerchantByPage(dto);
            result.setSuccess(true);
            result.setData(merchantByPage);
            result.setCode("成功");
            PageInfo<Map> pageInfo = new PageInfo<Map>(merchantByPage);
            return new Result(true, "获取成功", "", pageInfo.getList(), pageInfo.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据异常："+e);
            result.setCode("失败");
            result.setSuccess(false);
            return result;
        }

    }

    @Override
    public StoreManagerVO getStoreManagerVODateilById(StoreManagerDTO dto) {
        List<StoreMangerImageVO> list = storeManagerMapper.getAgentListDateil(dto);

        List<StoreManagerVO> vo=storeManagerMapper.getStoreMangerBase(dto);
        if (vo.size() > 0) {
            vo.get(0).setStoreImageVOList(list);
           return vo.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Result updateAgentAndImg(StoreDTO agentDTO) {
        Result result = new Result();

        if(!idAuthService.idCertificatesAuth(agentDTO.getAgentName(), agentDTO.getPersonIdCardNo())){
            result.setSuccess(false);
            result.setMessage("身份验证失败");
            return result;
        }
        Long storeId = agentDTO.getId();
        Integer type =1;
        //删除图片中含此代理商的
        storeManagerMapper.deleteStoreMangerImage(storeId);
        try {
            List<StoreImageDTO> agentImageDTOList = agentDTO.getAgentImageDTOList();
            for (int i = 0; agentImageDTOList.size() >i ; i++) {
                logger.info("storedId:"+storeId+"type:"+agentImageDTOList.get(i).getType()+"url:"+agentImageDTOList.get(i).getUrl());
                storeManagerMapper.saveStoreMangeImage(storeId,agentImageDTOList.get(i).getType(),agentImageDTOList.get(i).getUrl());
            }
            storeManagerMapper.updateStoreManagerType(storeId,type,agentDTO.getAgentName(),agentDTO.getPersonIdCardNo(),agentDTO.getTelPhone());
            result.setSuccess(true);
            result.setMessage("成功");

            //保存到数据村总
        } catch (Exception e) {
            logger.info("数据异常："+e);
           e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setSuccess(false);
            result.setMessage("失败");
        }
        return result;


    }


    public Result updateWorkType(Long id, Integer type) {
        Result result = new Result();

        try {
            storeManagerMapper.updateStoreManagerTypeById(id,type);
            result.setSuccess(true);
            result.setMessage("成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据异常："+e);
            result.setSuccess(false);
            result.setMessage("失败");
        }
        return result;
    }


}
