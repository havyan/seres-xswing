package com.xswing.framework.validator;

public class NameValidator implements Validator<String> {

	private static String RE_NAME = "[A-Za-z][\\w\\s_-]*";

	@Override
	public String validate(String target) {
		String error = "Not a valid name";
		if (target != null) {
			if (!target.matches(RE_NAME)) {
				return error;
			}
		} else {
			return error;
		}
		return null;
	}

}
