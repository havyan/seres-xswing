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
public class ToolBarParser extends ComponentParser<JToolBar> {

	@Override
	public JToolBar parseElement(Context context, Element source) {
		JToolBar toolBar = createBean(context, source);

		List<Element> components = source.getChildren(Const.COMPONENT);
		for (Element component : components) {
			toolBar.add((Component) ParserEngine.parse(context, component));
		}
		return toolBar;
	}

}