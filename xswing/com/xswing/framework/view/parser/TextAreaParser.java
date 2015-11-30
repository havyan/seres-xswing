package com.xswing.framework.view.parser;

import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TEXTAREA })
public class TextAreaParser extends ComponentParser<JTextArea> {
	@Override
	public JTextArea parseElement(Context context, Element source) {
		JTextArea textArea = super.parseElement(context, source);
		String columns = source.getAttributeValue(Const.COLUMNS);
		if (StringUtils.isNotEmpty(columns)) {
			textArea.setColumns(Integer.valueOf(columns));
		}
		String rows = source.getAttributeValue(Const.ROWS);
		if (StringUtils.isNotEmpty(rows)) {
			textArea.setRows(Integer.valueOf(rows));
		}
		return textArea;
	}
}
