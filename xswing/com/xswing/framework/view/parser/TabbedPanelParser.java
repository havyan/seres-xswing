/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.component.JCloseableTabbedPane;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.TABBEDPANEL })
public class TabbedPanelParser extends BeanParser<JTabbedPane> {

	@Override
	public JTabbedPane parseElement(Context context, Element source) {
		JCloseableTabbedPane tabbedPanel = new JCloseableTabbedPane();
		List<Element> components = source.getChildren(Const.TAB);
		for (Element e : components) {
			Component component = (Component) ParserEngine.parse(context, e);
			String title = e.getAttributeValue(Const.TITLE);
			if (StringUtils.isEmpty(title)) {
				title = component.getName();
			}
			boolean closable = true;
			String closableText = e.getAttributeValue(Const.CLOSABLE);
			if (StringUtils.isNotEmpty(closableText)) {
				closable = Boolean.valueOf(closableText);
			}
			tabbedPanel.addTab(title, component, closable);
		}
		return tabbedPanel;
	}

}
