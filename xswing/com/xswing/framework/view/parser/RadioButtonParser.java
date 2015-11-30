package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.RADIOBUTTON })
public class RadioButtonParser extends ComponentParser<JRadioButton> {
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
		radioButton.setSelected(getBoolean(source, Const.SELECTED, false));
		return radioButton;
	}
}
