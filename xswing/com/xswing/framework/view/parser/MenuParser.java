/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jdom.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.MENU })
public class MenuParser extends ElementParser<JMenuItem> {

	@SuppressWarnings("unchecked")
	@Override
	public JMenuItem parseElement(Context context, Element source) {
		String text = source.getAttributeValue(Const.TEXT);
		JMenuItem menu = null;
		List<Element> childMenus = source.getChildren();
		if ((childMenus != null && childMenus.size() > 0) || source.getParentElement().getName().equals(Const.MENUBAR)) {
			menu = new JMenu(text);
			for (Element e : childMenus) {
				menu.add((JComponent) ParserEngine.parse(context, e));
			}
		} else {
			menu = new JMenuItem(text);
		}
		return menu;
	}

}
