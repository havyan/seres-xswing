/**
 * 
 */
package com.xswing.framework.view.parser;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.SPLITPANEL })
public class SplitPanelParser extends ComponentParser<JSplitPane> {

	@Override
	public JSplitPane parseElement(Context context, Element source) {
		JSplitPane panel = createBean(context, source);
		int orientation = JSplitPane.HORIZONTAL_SPLIT;
		String orientationString = source.getAttributeValue(Const.ORIENTATION);
		if (orientationString != null && orientationString.equals(Const.VERTICAL)) {
			orientation = JSplitPane.VERTICAL_SPLIT;
		}
		panel.setOrientation(orientation);

		JComponent com1 = (JComponent) ParserEngine.parse(context, source.getChild(Const.BEFORE));
		JComponent com2 = (JComponent) ParserEngine.parse(context, source.getChild(Const.AFTER));
		if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
			if (com1 != null) {
				panel.setLeftComponent(com1);
			}
			if (com2 != null) {
				panel.setRightComponent(com2);
			}
		} else {
			if (com1 != null) {
				panel.setTopComponent(com1);
			}
			if (com2 != null) {
				panel.setBottomComponent(com2);
			}
		}
		return panel;
	}

}
