package com.xswing.framework.view.parser;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.LABEL })
public class LabelParser extends ComponentParser<JLabel> {

	@Override
	public JLabel parseElement(Context context, Element source) {
		JLabel label = super.parseElement(context, source);
		String icon = source.getAttributeValue(Const.ICON);
		if (StringUtils.isNotEmpty(icon)) {
			label.setIcon(new ImageIcon(icon));
		}
		return label;
	}

	@Override
	protected void bind(Context context, String id, JLabel bean, Element source) {
		super.bind(context, id, bean, source);
		String text = getString(source, Const.TEXT);
		if (StringUtils.isNotEmpty(text)) {
			bindSet(context, bean, text, value -> {
				bean.setText(value.toString());
			});
		}
	}

}
