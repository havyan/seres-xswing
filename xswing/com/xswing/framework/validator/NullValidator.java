package com.xswing.framework.validator;

public class NullValidator implements Validator<Object> {

	@Override
	public String validate(Object target) {
		if (target == null) {
			return "Value can't be null";
		}
		return null;
	}

}
