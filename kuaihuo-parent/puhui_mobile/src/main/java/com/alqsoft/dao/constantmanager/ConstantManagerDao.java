package com.alqsoft.dao.constantmanager;

import java.util.List;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.constantmanager.ConstantManager;

/**
 * 
 * @Title: ConstantManagerDao.java
 * @Description: 常量管理
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月14日 下午5:34:02 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 *
 */
@MyBatisRepository
public interface ConstantManagerDao{

	public List<ConstantManager> findConstantManagerByIsMemory(Integer isMemory);
}
