package com.xswing.framework.view.parser;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

public abstract class ElementParser<T> implements Parser<T, Element> {

	public T parse(Context context, Element source) {
		T bean = parseElement(context, source);
		handle(bean, context, source);
		String id = source.getAttributeValue(Const.ID);
		if (id != null) {
			context.setBean(id, bean);
		}
		return bean;
	}

	public abstract T parseElement(Context context, Element source);
	
	protected void handle(T obj,Context context, Element source){
		
	}

}
