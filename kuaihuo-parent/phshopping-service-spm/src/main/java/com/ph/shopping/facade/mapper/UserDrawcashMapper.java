package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.UserDrawcash;

public interface UserDrawcashMapper extends BaseMapper<UserDrawcash> {

	Long getCashMoney(Long userId);
}