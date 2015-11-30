package com.xswing.framework.view.parser;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

public abstract class ElementParser<T> implements Parser<T, Element> {

	public T parse(Context context, Element source) {
		T bean = parseElement(context, source);
		handle(context, bean, source);
		String id = getString(source, Const.ID, UUID.randomUUID().toString());
		bind(context, id, bean, source);
		return bean;
	}

	public abstract T parseElement(Context context, Element source);

	protected void handle(Context context, T obj, Element source) {

	}

	protected void bind(Context context, String id, T bean, Element source) {
		context.setBean(id, bean);
	}

	protected boolean getBoolean(Element source, String attrName, boolean defaultValue) {
		return getValue(source, attrName, Boolean.class, defaultValue);
	}

	protected String getString(Element source, String attrName) {
		return getString(source, attrName, null);
	}

	protected String getString(Element source, String attrName, String defaultValue) {
		return getValue(source, attrName, String.class, defaultValue);
	}

	protected int getInt(Element source, String attrName, int defaultValue) {
		return getValue(source, attrName, Integer.class, defaultValue);
	}

	protected double getDouble(Element source, String attrName, double defaultValue) {
		return getValue(source, attrName, Double.class, defaultValue);
	}

	protected float getFloat(Element source, String attrName, float defaultValue) {
		return getValue(source, attrName, Float.class, defaultValue);
	}

	protected long getLong(Element source, String attrName, long defaultValue) {
		return getValue(source, attrName, Long.class, defaultValue);
	}

	protected short getShort(Element source, String attrName, short defaultValue) {
		return getValue(source, attrName, Short.class, defaultValue);
	}

	@SuppressWarnings("unchecked")
	protected <V> V getValue(Element source, String attrName, V defaultValue) {
		if (defaultValue != null) {
			return getValue(source, attrName, (Class<V>) defaultValue.getClass(), defaultValue);
		} else {
			return defaultValue;
		}
	}

	protected Class<?> getClass(Element source, String attrName) {
		String text = source.getAttributeValue(attrName);
		if (StringUtils.isNotEmpty(text)) {
			return getClass(text);
		} else {
			return null;
		}
	}

	protected Class<?> getClass(String className) {
		return BaseUtils.getClass(className);
	}

	protected <V> V getValue(Element source, String attrName, Class<V> cls, V defaultValue) {
		String text = source.getAttributeValue(attrName);
		if (StringUtils.isNotEmpty(text)) {
			return BaseUtils.createObject(cls, text);
		} else {
			return defaultValue;
		}
	}

}
