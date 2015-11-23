/**
 * 
 */
package com.xswing.framework.view.parser;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.jdom.Element;

import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.SCROLLPANEL })
public class ScrollPanelParser extends ElementParser<JScrollPane> {

	@Override
	public JScrollPane parseElement(Context context, Element source) {
		JComponent view = (JComponent) ParserEngine.parse(context, source.getChild(Const.VIEW)); 
		JScrollPane scrollPanel = new JScrollPane(view);
		return scrollPanel;
	}

}
