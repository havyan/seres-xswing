package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.CHECKBOX })
public class CheckBoxParser extends ComponentParser<JCheckBox> {
	@Override
	public JCheckBox parseElement(Context context, Element source) {
		JCheckBox checkBox = super.parseElement(context, source);
		String icon = getString(source, Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			checkBox.setIcon(new ImageIcon(icon));
		}
		String text = getString(source, Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			checkBox.setText(text);
		}
		checkBox.setSelected(getBoolean(source, Const.SELECTED, false));
		return checkBox;
	}
}
