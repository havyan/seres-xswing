package com.xswing.framework.test;

import javax.swing.JComponent;

import org.apache.commons.lang3.ClassUtils;

public class TestClass {

	public static void main(String[] args) {
		Iterable<Class<?>> iterator = ClassUtils.hierarchy(JComponent.class);
		iterator.forEach((cls) -> {
			System.out.println(cls.getName());
		});
	}

}
