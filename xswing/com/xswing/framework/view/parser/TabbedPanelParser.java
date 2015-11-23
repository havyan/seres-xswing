/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import org.jdom2.Element;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.component.JCloseableTabbedPane;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.TABBEDPANEL })
public class TabbedPanelParser extends ElementParser<JTabbedPane> {

	@SuppressWarnings("unchecked")
	@Override
	public JTabbedPane parseElement(Context context, Element source) {
		JTabbedPane tabbedPanel = null;
		String closeable = source.getAttributeValue(Const.CLOSEABLE);
		if (closeable != null && Boolean.valueOf(closeable)) {
			tabbedPanel = new JCloseableTabbedPane();
		} else {
			tabbedPanel = new JTabbedPane();
		}

		List<Element> components = source.getChildren(Const.TAB);
		for (Element component : components) {
			tabbedPanel.add((Component) ParserEngine.parse(context, component));
		}
		return tabbedPanel;
	}

}
