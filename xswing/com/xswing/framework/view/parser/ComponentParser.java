/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

import org.jdom2.Element;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.CENTER, Const.NORTH, Const.SOUTH, Const.EAST, Const.WEST, Const.CELL, Const.COMPONENT,
		Const.LEFTORTOP, Const.RIGHTORBOTTOM, Const.VIEW, Const.ITEM, Const.TAB, Const.CARD })
public class ComponentParser<T extends JComponent> extends BeanParser<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T parseElement(Context context, Element source) {
		T component = (T) ParserEngine.parse(context, (Element) source.getChildren().get(0));
		return component;
	}

	protected void handle(T component, Context context, Element source) {
		super.handle(component, context, source);
		if (component != null) {
			Border border = createBorder(source);
			if (border != null) {
				component.setBorder(border);
			}
			String name = source.getAttributeValue(Const.NAME);
			if (name != null) {
				component.setName(name);
			}
		}
	}

	protected Editor<T, ?> createEditor(T component, Context context, Element source) {
		String editor = source.getAttributeValue(Const.EDITOR);
		// TODO
		return null;
	}

	protected Border createBorder(Element source) {
		String borderText = source.getAttributeValue(Const.BORDER);
		Border border = null;
		if (borderText != null && borderText.length() > 0) {
			if (borderText.equals(Const.LINE)) {
				border = BorderFactory.createLineBorder(Color.BLACK);
			} else if (borderText.equals(Const.ETCHED)) {
				border = BorderFactory.createEtchedBorder();
			} else if (borderText.equals(Const.RAISEDBEVEL)) {
				border = BorderFactory.createRaisedBevelBorder();
			} else if (borderText.equals(Const.LOWEREDBEVEL)) {
				border = BorderFactory.createLoweredBevelBorder();
			}
		}
		String title = source.getAttributeValue(Const.TITLE);
		if (title != null && title.length() > 0) {
			if (border != null) {
				border = BorderFactory.createTitledBorder(border, title);
			} else {
				border = BorderFactory.createTitledBorder(title);
			}
		}
		return border;
	}

}
