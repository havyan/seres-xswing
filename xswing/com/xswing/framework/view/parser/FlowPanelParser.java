/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.FLOWPANEL })
public class FlowPanelParser extends ComponentParser<JPanel> {

	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = createBean(context, source);
		FlowLayout layout = new FlowLayout();
		int align = FlowLayout.CENTER;
		String alignText = source.getAttributeValue(Const.ALIGN);
		if (alignText != null) {
			align = (int) BaseUtils.getStaticValue(FlowLayout.class, alignText);
		}
		layout.setHgap(getInt(source, Const.HGAP, 5));
		layout.setVgap(getInt(source, Const.VGAP, 5));
		layout.setAlignment(align);
		panel.setLayout(layout);

		List<Element> components = source.getChildren(Const.COMPONENT);
		for (Element component : components) {
			panel.add((Component) ParserEngine.parse(context, component));
		}
		return panel;
	}

}
