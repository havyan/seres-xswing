/**
 * 
 */
package com.xswing.framework.view.parser;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.SPLITPANEL })
public class SplitPanelParser extends BeanParser<JSplitPane> {

	@Override
	public JSplitPane parseElement(Context context, Element source) {
		JSplitPane panel = new JSplitPane();
		int orientation = JSplitPane.HORIZONTAL_SPLIT;
		String orientationString = source.getAttributeValue(Const.ORIENTATION);
		if (orientationString != null && orientationString.length() > 0) {
			orientation = (int) BaseUtils.getStaticValue(JSplitPane.class, orientationString);
		}
		panel.setOrientation(orientation);

		JComponent com1 = (JComponent) ParserEngine.parse(context, source.getChild(Const.LEFTORTOP));
		JComponent com2 = (JComponent) ParserEngine.parse(context, source.getChild(Const.RIGHTORBOTTOM));
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
