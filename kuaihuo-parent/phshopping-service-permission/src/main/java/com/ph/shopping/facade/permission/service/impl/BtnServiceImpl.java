package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.BtnMapper;
import com.ph.shopping.facade.permission.service.IBtnService;
import com.ph.shopping.facade.permission.vo.BtnVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述：按钮实现层
 * @作者：Mr.Shu
 * @创建时间：2017-05-18
 * @Copyright @2017 by Mr.Shu
 */
@Component
@Service(version = "1.0.0")
public class BtnServiceImpl implements IBtnService {

    private static final Logger logger = LoggerFactory.getLogger(BtnServiceImpl.class);

    @Autowired
    private BtnMapper btnMapper;

    @Override
    public List<BtnVO> getBtnListByMenuId(List<Long> menuIds, Long roleId) {
        return btnMapper.getBtnListByMenuIdsAndRoleId(menuIds, roleId);
    }

    @Override
    public List<BtnVO> getBtnList() {
        return btnMapper.getAllBtn();
    }
}
