package com.xswing.framework.view.parser;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.CARDPANEL })
public class CardPanelParser extends ElementParser<JPanel> {

	@SuppressWarnings("unchecked")
	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = new JPanel();
		CardLayout layout = new CardLayout();
		String hgapText = source.getAttributeValue(Const.HGAP);
		if (StringUtils.isNotEmpty(hgapText)) {
			layout.setHgap(Integer.valueOf(hgapText));
		}
		String vgapText = source.getAttributeValue(Const.VGAP);
		if (StringUtils.isNotEmpty(vgapText)) {
			layout.setVgap(Integer.valueOf(vgapText));
		}
		panel.setLayout(layout);

		List<Element> cards = source.getChildren(Const.CARD);
		for (Element card : cards) {
			panel.add((Component) ParserEngine.parse(context, card));
		}
		return panel;
	}

}
