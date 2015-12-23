/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.BEAN, Const.VALUE, Const.VALIDATOR })
public class BeanParser<T> extends ElementParser<T> {

	protected Class<?> findClass(Element source) {
		Class<?> genericType = BaseUtils.getClassGenricType(this.getClass());
		Class<?> cls = getClass(source, Const.CLASS);
		if (cls != null) {
			if (genericType.isAssignableFrom(cls)) {
				return cls;
			} else {
				throw new IllegalArgumentException(cls.getName() + " is not a sub class of " + genericType.getName());
			}
		} else {
			return genericType;
		}
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
				Class<?> cls = null;
				if (StringUtils.isNotEmpty(type)) {
					cls = BaseUtils.getPrimitiveClass(type);
					if (cls == null) {
						try {
							cls = Class.forName(type);
						} catch (ClassNotFoundException ex) {
							Logger.error(ex);
						}
					}
				}
				clses.add(cls);
				String value = e.getAttributeValue(Const.VALUE);
				if (value != null) {
					values.add(BaseUtils.createObject(cls, value));
				} else {
					String ref = getString(e, Const.REF);
					if (StringUtils.isNotEmpty(ref)) {
						Object refBean = context.getBean(ref);
						values.add(refBean);
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
				for (int i = 0; i < clses.size(); i++) {
					if (clses.get(i) == null) {
						Object paramValue = values.get(i);
						if (paramValue != null) {
							clses.set(i, paramValue.getClass());
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
					Object refBean = context.getBean(ref);
					if (refBean != null) {
						BaseUtils.setProperty(bean, propertyName, refBean);
					} else {
						context.addBeanRef(bean, propertyName, ref);
					}
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

	protected void handle(Context context, T obj, Element source) {
		if (obj != null) {
			setProperties(context, obj.getClass(), obj, source);
		}
	}

}
