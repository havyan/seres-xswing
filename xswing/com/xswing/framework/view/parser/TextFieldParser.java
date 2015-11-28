package com.xswing.framework.view.parser;

import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TEXTFIELD })
public class TextFieldParser extends BeanParser<JTextField> {
	@Override
	public JTextField parseElement(Context context, Element source) {
		JTextField textField = super.parseElement(context, source);
		String columns = source.getAttributeValue(Const.COLUMNS);
		if (StringUtils.isNotEmpty(columns)) {
			textField.setColumns(Integer.valueOf(columns));
		}
		return textField;
	}
}
