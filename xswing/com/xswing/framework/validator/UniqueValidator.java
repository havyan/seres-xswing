package com.xswing.framework.validator;

import java.util.Arrays;

public class UniqueValidator implements Validator<Object> {

	private Object[] exists;

	public UniqueValidator(Object[] exists) {
		super();
		this.exists = exists;
	}

	@Override
	public String validate(Object target) {
		if (exists != null && Arrays.asList(exists).contains(target)) {
			return "Duplicated";
		}
		return null;
	}

}
