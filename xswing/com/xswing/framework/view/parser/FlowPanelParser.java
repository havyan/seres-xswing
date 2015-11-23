/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.FLOWPANEL })
public class FlowPanelParser extends ElementParser<JPanel> {

	@SuppressWarnings("unchecked")
	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		int align = FlowLayout.CENTER;
		String alignText = source.getAttributeValue(Const.ALIGN);
		if (alignText != null) {
			align = (int) BaseUtils.getStaticValue(FlowLayout.class, alignText);
		}
		String hgapText = source.getAttributeValue(Const.HGAP);
		if (StringUtils.isNotEmpty(hgapText)) {
			layout.setHgap(Integer.valueOf(hgapText));
		}
		String vgapText = source.getAttributeValue(Const.VGAP);
		if (StringUtils.isNotEmpty(vgapText)) {
			layout.setVgap(Integer.valueOf(vgapText));
		}
		layout.setAlignment(align);
		panel.setLayout(layout);

		List<Element> components = source.getChildren(Const.COMPONENT);
		for (Element component : components) {
			panel.add((Component) ParserEngine.parse(context, component));
		}
		return panel;
	}

}
