package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.MENUBAR })
public class MenuBarParser extends ComponentParser<JMenuBar> {

	@Override
	public JMenuBar parseElement(Context context, Element source) {
		JMenuBar menuBar = createBean(context, source);
		List<Element> menus = source.getChildren();
		for (Element e : menus) {
			menuBar.add((JComponent) ParserEngine.parse(context, e));
		}
		return menuBar;
	}

}
