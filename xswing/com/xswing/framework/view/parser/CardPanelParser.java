package com.xswing.framework.view.parser;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.CARDPANEL })
public class CardPanelParser extends ComponentParser<JPanel> {

	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = createBean(context, source);
		CardLayout layout = new CardLayout();
		layout.setHgap(getInt(source, Const.HGAP, 0));
		layout.setVgap(getInt(source, Const.VGAP, 0));
		panel.setLayout(layout);
		List<Element> cards = source.getChildren(Const.CARD);
		for (Element card : cards) {
			panel.add((Component) ParserEngine.parse(context, card));
		}
		return panel;
	}

}
