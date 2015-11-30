/**
 * 
 */
package com.xswing.framework.view.parser;

import javax.swing.JComponent;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.CENTER, Const.NORTH, Const.SOUTH, Const.EAST, Const.WEST, Const.CELL, Const.COMPONENT, Const.BEFORE, Const.AFTER, Const.VIEW, Const.ITEM, Const.TAB,
		Const.CARD })
public class WrapperParser<T extends JComponent> extends ElementParser<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T parseElement(Context context, Element source) {
		T component = (T) ParserEngine.parse(context, (Element) source.getChildren().get(0));
		return component;
	}

	protected void bind(Context context, String id, T bean, Element source) {
	}

}
