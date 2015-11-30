/**
 * 
 */
package com.xswing.framework.view.parser;

import javax.swing.JSeparator;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

/**
 * @author think
 *
 */
@XElement(names = { Const.SEPARATOR })
public class SeparatorParser extends ComponentParser<JSeparator> {

	@Override
	public JSeparator parseElement(Context context, Element source) {
		JSeparator separator = super.parseElement(context, source);
		int orientation = JSeparator.HORIZONTAL;
		String orientationText = source.getAttributeValue(Const.ORIENTATION);
		if (orientationText != null && orientationText.equals(Const.VERTICAL)) {
			orientation = JSeparator.VERTICAL;
		}
		separator.setOrientation(orientation);
		return separator;
	}

	public Class<?> findClass(Element source) {
		String type = source.getAttributeValue(Const.TYPE);
		if (type != null) {
			if (type.equals(Const.MENU_UP)) {
				return javax.swing.JPopupMenu.Separator.class;
			} else if (type.equals(Const.TOOLBAR_UP)) {
				return javax.swing.JToolBar.Separator.class;
			}
		}
		return JSeparator.class;
	}

}