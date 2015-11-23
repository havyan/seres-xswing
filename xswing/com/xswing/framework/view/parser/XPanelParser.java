/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JPanel;

import org.jdom.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.processor.XProcessor;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;

/**
 * @author HWYan
 * 
 */
public class XPanelParser extends BorderPanelParser {

	@SuppressWarnings("unchecked")
	@Override
	public JPanel parseElement(Context context, Element source) {
		XPanel xPanel = (XPanel) super.parseElement(context, source);
		xPanel.setContext(context);
		String processorClassName = source.getAttributeValue(Const.PROCESSOR);
		if (processorClassName != null) {
			XProcessor processor = (XProcessor) BaseUtils.newInstance(processorClassName, new Class<?>[] { XPanel.class }, new Object[] { xPanel });
			processor.process();
		}
		List<Element> processors = source.getChildren(Const.PROPERTY);
		if (processors != null && processors.size() > 0) {
			for (Element e : processors) {
				XProcessor processor = (XProcessor) BaseUtils.newInstance(e.getText(), new Class<?>[] { XPanel.class }, new Object[] { xPanel });
				processor.process();
			}
		}
		List<Element> beans = source.getChildren(Const.BEAN);
		if (beans != null && beans.size() > 0) {
			for (Element e : beans) {
				ParserEngine.parse(context, e);
			}
		}
		return xPanel;
	}

	public JPanel createPanel() {
		return new XPanel();
	}

}
