package com.xswing.framework.view.parser;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.BORDERPANEL })
public class BorderPanelParser extends BeanParser<JPanel> {

	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = createPanel(context, source);
		Element e = source.getChild(Const.CENTER);
		if (e != null) {
			panel.add((Component) ParserEngine.parse(context, e), BorderLayout.CENTER);
		}

		e = source.getChild(Const.EAST);
		if (e != null) {
			panel.add((Component) ParserEngine.parse(context, e), BorderLayout.EAST);
		}

		e = source.getChild(Const.WEST);
		if (e != null) {
			panel.add((Component) ParserEngine.parse(context, e), BorderLayout.WEST);
		}

		e = source.getChild(Const.NORTH);
		if (e != null) {
			panel.add((Component) ParserEngine.parse(context, e), BorderLayout.NORTH);
		}

		e = source.getChild(Const.SOUTH);
		if (e != null) {
			panel.add((Component) ParserEngine.parse(context, e), BorderLayout.SOUTH);
		}
		return panel;
	}

	protected JPanel createPanel(Context context, Element source) {
		JPanel panel = createBean(context, source);
		panel.setLayout(new BorderLayout());
		return panel;
	}

}
