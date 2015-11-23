/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import javax.swing.JToolBar;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.TOOLBAR })
public class ToolBarParser extends ElementParser<JToolBar> {

	@SuppressWarnings("unchecked")
	@Override
	public JToolBar parseElement(Context context, Element source) {
		JToolBar toolBar = new JToolBar();

		List<Element> components = source.getChildren(Const.COMPONENT);
		for (Element component : components) {
			toolBar.add((Component) ParserEngine.parse(context, component));
		}
		return toolBar;
	}

}