
package com.ph.shopping.common.core.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @项目：phshopping-common-core
 *
 * @描述：
 *
 * @作者： chang
 *
 * @创建时间：2017年3月8日
 *
 * @ Copyright @2017 by Mr.chang
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
  
