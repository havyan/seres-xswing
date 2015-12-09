package com.xswing.framework.validator;

import org.apache.commons.lang3.StringUtils;

public class EmptyValidator implements Validator<String> {

	@Override
	public String validate(String target) {
		if (StringUtils.isEmpty(target)) {
			return "Value can't be empty";
		}
		return null;
	}

}
