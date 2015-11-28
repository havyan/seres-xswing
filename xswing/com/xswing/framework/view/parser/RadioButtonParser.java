package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.RADIOBUTTON })
public class RadioButtonParser extends BeanParser<JRadioButton> {
	@Override
	public JRadioButton parseElement(Context context, Element source) {
		JRadioButton radioButton = super.parseElement(context, source);
		String icon = source.getAttributeValue(Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			radioButton.setIcon(new ImageIcon(icon));
		}
		String text = source.getAttributeValue(Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			radioButton.setText(text);
		}
		String selected = source.getAttributeValue(Const.SELECTED);
		if (StringUtils.isNotEmpty(text)) {
			radioButton.setSelected(Boolean.valueOf(selected));
		}
		return radioButton;
	}
}