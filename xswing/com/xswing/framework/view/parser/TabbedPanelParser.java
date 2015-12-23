/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.components.ClosableTabbedPanel;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.TABBEDPANEL })
public class TabbedPanelParser extends ComponentParser<ClosableTabbedPanel> {

	@Override
	public ClosableTabbedPanel parseElement(Context context, Element source) {
		ClosableTabbedPanel tabbedPanel = createBean(context, source);
		List<Element> components = source.getChildren(Const.TAB);
		for (Element e : components) {
			Component component = (Component) ParserEngine.parse(context, e);
			String title = getString(e, Const.TITLE, component.getName());
			String iconPath = getString(e, Const.ICON, component.getName());
			String tooltip = getString(e, Const.TOOLTIP);
			Icon icon = null;
			if (StringUtils.isNotEmpty(iconPath)) {
				icon = new ImageIcon(iconPath);
			}
			tabbedPanel.addTab(title, icon, component, tooltip, getBoolean(e, Const.CLOSABLE, true));
		}
		return tabbedPanel;
	}

}
