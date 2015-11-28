package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.LABEL })
public class LabelParser extends BeanParser<JLabel> {

	@Override
	public JLabel parseElement(Context context, Element source) {
		JLabel label = super.parseElement(context, source);
		String icon = source.getAttributeValue(Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			label.setIcon(new ImageIcon(icon));
		}
		String text = source.getAttributeValue(Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			label.setText(text);
		}
		return label;
	}

}
