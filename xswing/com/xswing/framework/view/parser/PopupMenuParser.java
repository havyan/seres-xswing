package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.POPUPMENU })
public class PopupMenuParser extends ComponentParser<JPopupMenu> {

	@Override
	public JPopupMenu parseElement(Context context, Element source) {
		JPopupMenu poupuMenu = createBean(context, source);
		List<Element> menus = source.getChildren(Const.ITEM);
		for (Element e : menus) {
			poupuMenu.add((JComponent) ParserEngine.parse(context, e));
		}
		return poupuMenu;
	}

}