/**
 * 
 */
package com.xswing.framework.view.parser;

import org.jdom2.Element;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;
import com.xswing.framework.view.XPanelBuilder;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.IMPORT })
public class ImportParser extends ElementParser<XPanel> {

	@Override
	public XPanel parseElement(Context context, Element source) {
		String path = source.getAttributeValue(Const.PATH);
		if (path != null) {
			return XPanelBuilder.build(context.getPath(), path);
		} else {
			return null;
		}
	}

}
