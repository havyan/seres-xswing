package com.xswing.framework.view.parser;

import javax.swing.JTextArea;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TEXTAREA })
public class TextAreaParser extends ComponentParser<JTextArea> {
	@Override
	public JTextArea parseElement(Context context, Element source) {
		JTextArea textArea = super.parseElement(context, source);
		textArea.setColumns(getInt(source, Const.COLUMNS, 0));
		textArea.setRows(getInt(source, Const.ROWS, 0));
		return textArea;
	}
}
