/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.BEAN, Const.VALUE })
public class BeanParser<T> extends ElementParser<T> {

	public static final Map<String, Class<?>> CLASS_MAP = new HashMap<String, Class<?>>();

	static {
		CLASS_MAP.put(Const.CHECKBOX, JCheckBox.class);
	}

	public Class<?> findClass(Element source) {
		String name = source.getName();
		Class<?> cls = CLASS_MAP.get(name);
		if (cls != null) {
			return cls;
		} else {
			Class<?> genericType = BaseUtils.getClassGenricType(this.getClass());
			String className = source.getAttributeValue(Const.CLASS);
			if (StringUtils.isNotEmpty(className)) {
				try {
					cls = Class.forName(className);
					if (genericType.isAssignableFrom(cls)) {
						return cls;
					} else {
						throw new IllegalArgumentException(className + "is not a sub class of " + genericType.getName());
					}
				} catch (Exception e) {
					Logger.error(e);
				}
			} else {
				return genericType;
			}
		}
		return null;
	}

	@Override
	public T parseElement(Context context, Element source) {
		return createBean(context, source);
	}

	@SuppressWarnings("unchecked")
	public T createBean(Context context, Element source) {
		T bean = null;
		List<Element> arguments = source.getChildren(Const.ARGUMENT);
		if (arguments != null && arguments.size() > 0) {
			List<Class<?>> clses = new ArrayList<Class<?>>();
			List<Object> values = new ArrayList<Object>();
			for (Element e : arguments) {
				String type = e.getAttributeValue(Const.TYPE);
				Class<?> cls = BaseUtils.getPrimitiveClass(type);
				if (cls == null) {
					try {
						cls = Class.forName(type);
					} catch (ClassNotFoundException ex) {
						Logger.error(ex);
					}
				}
				clses.add(cls);
				String value = e.getAttributeValue(Const.VALUE);
				if (value != null) {
					values.add(BaseUtils.createObject(cls, value));
				} else {
					Element valueElement = e.getChild(Const.VALUE);
					if (valueElement != null) {
						values.add(ParserEngine.parse(context, valueElement));
					} else {
						value = e.getText();
						if (StringUtils.isNotEmpty(value)) {
							values.add(BaseUtils.createObject(cls, value.trim()));
						} else {
							values.add(null);
						}
					}
				}
			}
			bean = (T) BaseUtils.newInstance(findClass(source), clses.toArray(new Class<?>[0]), values.toArray());
		} else {
			bean = (T) BaseUtils.newInstance(findClass(source));
		}
		return bean;
	}

	public void setProperties(Context context, Class<?> cl, Object bean, Element e) {
		List<Element> properties = e.getChildren(Const.PROPERTY);
		if (properties != null && properties.size() > 0) {
			for (Element property : properties) {
				String propertyName = property.getAttributeValue(Const.NAME);
				String value = property.getAttributeValue(Const.VALUE);
				String ref = property.getAttributeValue(Const.REF);
				if (value != null) {
					Class<?> cls = BaseUtils.getWriteMethod(bean.getClass(), propertyName).getParameterTypes()[0];
					BaseUtils.setProperty(bean, propertyName, BaseUtils.createObject(cls, value));
				} else if (ref != null) {
					context.addBeanRef(bean, propertyName, ref);
				} else {
					Element valueElement = property.getChild(Const.VALUE);
					if (valueElement != null) {
						BaseUtils.setProperty(bean, propertyName, ParserEngine.parse(context, valueElement));
					} else {
						value = property.getText();
						if (StringUtils.isNotEmpty(value)) {
							Class<?> cls = BaseUtils.getWriteMethod(bean.getClass(), propertyName).getParameterTypes()[0];
							BaseUtils.setProperty(bean, propertyName, BaseUtils.createObject(cls, value.trim()));
						}
					}
				}
			}
		}
	}

	protected void handle(T obj, Context context, Element source) {
		if (obj != null) {
			setProperties(context, obj.getClass(), obj, source);
		}
	}

}
