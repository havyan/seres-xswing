package com.xswing.framework.view.parser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.framework.events.PropertyChangeListenerProxy;
import com.framework.proxy.interfaces.Bean;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.view.Context;

public abstract class ElementParser<T> implements Parser<T, Element> {

	protected static Pattern RE_PROPERTY = Pattern.compile("^\\{\\{(\\S+)\\}\\}$");

	protected static Pattern RE_HAS_PROPERTY = Pattern.compile("\\{\\{(\\S+)\\}\\}");

	public T parse(Context context, Element source) {
		T bean = parseElement(context, source);
		String id = getString(source, Const.ID, UUID.randomUUID().toString());
		bind(context, id, bean, source);
		handle(context, bean, source);
		return bean;
	}

	public abstract T parseElement(Context context, Element source);

	protected void handle(Context context, T obj, Element source) {

	}

	protected void bind(Context context, String id, T bean, Element source) {
		context.setBean(id, bean);
		context.getModel().addAppListener(AppModel.DATA_CHANGED, e -> {
			BaseUtils.takeBinds(e.getParam(AppModel.OLD_DATA), e.getParam(AppModel.NEW_DATA), bean);
		});
	}

	protected void bindProperty(Object data, String property, PropertyChangeListener l) {
		if (data != null && data instanceof Bean) {
			((Bean) data).addPropertyChangeListener(property, l);
		}
	}

	protected void bindSet(Context context, Object from, String text, Setter setter) {
		if (StringUtils.isNotEmpty(text) && hasProperty(text)) {
			List<String> properties = findProperties(text);
			for (String property : properties) {
				bindProperty(context.getData(), property, new PropertyChangeListenerProxy(from) {
					public void propertyChange(PropertyChangeEvent e) {
						setter.set(compile(context, text));
					}
				});
			}
			setter.set(compile(context, text));
		} else {
			setter.set(text);
		}
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

	protected Object compile(Context context, String text) {
		Matcher matcher = RE_PROPERTY.matcher(text);
		if (matcher.find()) {
			return getProperty(context, matcher.group(1));
		} else {
			matcher = RE_HAS_PROPERTY.matcher(text);
			while (matcher.find()) {
				String find = matcher.group();
				Object value = getProperty(context, matcher.group(1));
				if (value == null) {
					value = "";
				}
				text = text.replace(find, value.toString());
			}
		}
		return text;
	}

	protected Object getProperty(Context context, String property) {
		if (context != null && context.getData() != null) {
			return BaseUtils.getProperty(context.getData(), property);
		}
		return null;
	}

	protected boolean isProperty(String text) {
		return RE_PROPERTY.matcher(text).find();
	}

	protected boolean hasProperty(String text) {
		return RE_HAS_PROPERTY.matcher(text).find();
	}

	protected String findProperty(String text) {
		List<String> properties = findProperties(text);
		if (properties.size() > 0) {
			return properties.get(0);
		}
		return null;
	}

	protected List<String> findProperties(String text) {
		List<String> properties = new ArrayList<String>();
		Matcher matcher = RE_HAS_PROPERTY.matcher(text);
		while (matcher.find()) {
			properties.add(matcher.group(1));
		}
		return properties;
	}

}
