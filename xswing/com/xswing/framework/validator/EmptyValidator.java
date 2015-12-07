package com.xswing.framework.validator;

import org.apache.commons.lang3.StringUtils;

public class EmptyValidator implements Validator {

	@Override
	public String validate(Object target) {
		if (target instanceof String && StringUtils.isEmpty((String) target)) {
			return "Value can't be empty";
		}
		return null;
	}

}
