/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.BOXPANEL })
public class BoxPanelParser extends ComponentParser<JPanel> {

	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = createBean(context, source);
		int axis = BoxLayout.X_AXIS;
		String axisString = source.getAttributeValue("axis");
		if (StringUtils.isNotEmpty(axisString)) {
			axis = (int) BaseUtils.getStaticValue(BoxLayout.class, axisString);
		}
		panel.setLayout(new BoxLayout(panel, axis));

		List<Element> children = source.getChildren();
		for (Element child : children) {
			if (child.getName().equals(Const.COMPONENT)) {
				panel.add((Component) ParserEngine.parse(context, child));
			} else if (child.getName().equals(Const.STRUT)) {
				int size = getInt(child, "size", 0);
				if (axis == BoxLayout.X_AXIS) {
					panel.add(Box.createHorizontalStrut(size));
				} else if (axis == BoxLayout.Y_AXIS) {
					panel.add(Box.createVerticalStrut(size));
				}
			} else if (child.getName().equals(Const.GLUE)) {
				if (axis == BoxLayout.X_AXIS) {
					panel.add(Box.createHorizontalGlue());
				} else if (axis == BoxLayout.Y_AXIS) {
					panel.add(Box.createVerticalGlue());
				}
			}
		}

		Component[] components = panel.getComponents();
		String alignx = getString(source, Const.HALIGN);
		if (StringUtils.isNotEmpty(alignx)) {
			float alignxValue = (float) BaseUtils.getStaticValue(Component.class, alignx + Const.ALIGN_POSTFIX);
			for (Component component : components) {
				((JComponent) component).setAlignmentX(alignxValue);
			}
		}
		String aligny = getString(source, Const.VALIGN);
		if (StringUtils.isNotEmpty(aligny)) {
			float alignyValue = (float) BaseUtils.getStaticValue(Component.class, aligny + Const.ALIGN_POSTFIX);
			for (Component component : components) {
				((JComponent) component).setAlignmentY(alignyValue);
			}
		}

		return panel;
	}

}
