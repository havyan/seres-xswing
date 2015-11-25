/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jdom2.Element;

import com.framework.exception.ExceptionUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.BOXPANEL })
public class BoxPanelParser extends BeanParser<JPanel> {

	@SuppressWarnings("unchecked")
	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = new JPanel();
		int axis = BoxLayout.X_AXIS;
		String axisString = source.getAttributeValue("axis");
		if (axisString != null && axisString.length() > 0) {
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

		List<Element> components = source.getChildren(Const.COMPONENT);
		for (Element component : components) {
			panel.add((Component) ParserEngine.parse(context, component));
		}

		return panel;
	}

}
