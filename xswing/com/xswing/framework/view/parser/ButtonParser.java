package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.BUTTON })
public class ButtonParser extends ComponentParser<JButton> {

	@Override
	public JButton parseElement(Context context, Element source) {
		JButton button = super.parseElement(context, source);
		String icon = getString(source, Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			button.setIcon(new ImageIcon(icon));
		}
		String text = getString(source, Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			bindSet(context, button, text, value -> {
				button.setText(value == null ? "" : value.toString());
			});
		}
		return button;
	}

}
