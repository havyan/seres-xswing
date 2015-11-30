package com.xswing.framework.view.parser;

import javax.swing.JTextField;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TEXTFIELD })
public class TextFieldParser extends ComponentParser<JTextField> {
	@Override
	public JTextField parseElement(Context context, Element source) {
		JTextField textField = super.parseElement(context, source);
		textField.setColumns(getInt(source, Const.COLUMNS, 0));
		return textField;
	}
}
