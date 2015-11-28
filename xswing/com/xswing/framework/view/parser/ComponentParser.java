/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.CENTER, Const.NORTH, Const.SOUTH, Const.EAST, Const.WEST, Const.CELL, Const.COMPONENT, Const.BEFORE, Const.AFTER, Const.VIEW, Const.ITEM,
		Const.TAB, Const.CARD })
public class ComponentParser<T extends JComponent> extends BeanParser<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T parseElement(Context context, Element source) {
		T component = (T) ParserEngine.parse(context, (Element) source.getChildren().get(0));
		double width = component.getPreferredSize().getWidth();
		String widthText = source.getAttributeValue(Const.WIDTH);
		if (StringUtils.isNotEmpty(widthText)) {
			width = Double.valueOf(widthText);
		}
		double height = component.getPreferredSize().getHeight();
		String heightText = source.getAttributeValue(Const.HEIGHT);
		if (StringUtils.isNotEmpty(heightText)) {
			height = Double.valueOf(heightText);
		}
		Dimension size = new Dimension();
		size.setSize(width, height);
		component.setPreferredSize(size);
		String toolTip = source.getAttributeValue(Const.TOOLTIP);
		if (StringUtils.isNotEmpty(toolTip)) {
			component.setToolTipText(toolTip);
		}
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
		if (source.getName() != Const.TAB) {
			String title = source.getAttributeValue(Const.TITLE);
			if (title != null && title.length() > 0) {
				if (border != null) {
					border = BorderFactory.createTitledBorder(border, title);
				} else {
					border = BorderFactory.createTitledBorder(title);
				}
			}
		}
		return border;
	}

}
