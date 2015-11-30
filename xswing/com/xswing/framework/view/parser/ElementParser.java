package com.xswing.framework.view.parser;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

public abstract class ElementParser<T> implements Parser<T, Element> {

	public T parse(Context context, Element source) {
		T bean = parseElement(context, source);
		handle(context, bean, source);
		String id = source.getAttributeValue(Const.ID);
		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
		}
		bind(context, id, bean, source);
		return bean;
	}

	public abstract T parseElement(Context context, Element source);

	protected void handle(Context context, T obj, Element source) {

	}

	protected void bind(Context context, String id, T bean, Element source) {
		context.setBean(id, bean);
	}

}
