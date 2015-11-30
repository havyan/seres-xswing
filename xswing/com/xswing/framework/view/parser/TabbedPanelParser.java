/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import org.jdom2.Element;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.component.JCloseableTabbedPane;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.TABBEDPANEL })
public class TabbedPanelParser extends ComponentParser<JCloseableTabbedPane> {

	@Override
	public JCloseableTabbedPane parseElement(Context context, Element source) {
		JCloseableTabbedPane tabbedPanel = createBean(context, source);
		List<Element> components = source.getChildren(Const.TAB);
		for (Element e : components) {
			Component component = (Component) ParserEngine.parse(context, e);
			String title = getString(e, Const.TITLE, component.getName());
			tabbedPanel.addTab(title, component, getBoolean(e, Const.CLOSABLE, true));
		}
		return tabbedPanel;
	}

}
