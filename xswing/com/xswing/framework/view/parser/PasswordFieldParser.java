package com.xswing.framework.view.parser;

import javax.swing.JPasswordField;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.PASSWORDFIELD })
public class PasswordFieldParser extends ComponentParser<JPasswordField> {
	@Override
	public JPasswordField parseElement(Context context, Element source) {
		JPasswordField field = super.parseElement(context, source);
		field.setColumns(getInt(source, Const.COLUMNS, 0));
		return field;
	}
}
