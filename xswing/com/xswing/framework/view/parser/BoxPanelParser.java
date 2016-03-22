/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.exception.ExceptionUtils;
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
			Class<BoxLayout> cl = BoxLayout.class;
			try {
				Field f = cl.getField(axisString);
				axis = f.getInt(cl);
			} catch (Exception e1) {
				ExceptionUtils.logAndShowException(e1);
				return null;
			}
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

		return panel;
	}

}
