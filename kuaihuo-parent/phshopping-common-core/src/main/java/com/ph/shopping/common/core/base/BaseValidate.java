/**
 * 
 */
package com.ph.shopping.common.core.base;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

/**
 * @项目：phshopping-common-core
 *
 * @描述：实体校验类
 *
 * 			@作者： 杨颜光
 *
 * @创建时间：2017年5月22日
 *
 */
public class BaseValidate implements Serializable {

	
	private static final long serialVersionUID = -5300113985007593228L;

	// 验证FormBean
	public String validateForm() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BaseValidate>> constraintViolation = validator.validate(this);
		Optional<ConstraintViolation<BaseValidate>> first = constraintViolation.stream().findFirst();
		return first.map(ConstraintViolation::getMessage).orElse(null);
	}

}
