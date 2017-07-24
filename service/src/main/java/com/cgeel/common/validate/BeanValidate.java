/**
 * 
 */
package com.cgeel.common.validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidate {

	public static void validate(Object object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(object);

		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			throw new ValidateException(constraintViolation.getMessage() + " ["
					+ constraintViolation.getPropertyPath() + "]");
		}

	}

	public static String validateToMsg(Object object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(object);

		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			return constraintViolation.getMessage();
		}

		return "";
	}
}
