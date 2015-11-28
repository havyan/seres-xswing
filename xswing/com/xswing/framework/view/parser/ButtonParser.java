package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.BUTTON })
public class ButtonParser extends BeanParser<JButton> {

	@Override
	public JButton parseElement(Context context, Element source) {
		JButton button = super.parseElement(context, source);
		String icon = source.getAttributeValue(Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			button.setIcon(new ImageIcon(icon));
		}
		String text = source.getAttributeValue(Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			button.setText(text);
		}
		return button;
	}

}
