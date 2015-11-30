/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.processor.XProcessor;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;

/**
 * @author HWYan
 * 
 */
public class XPanelParser extends BorderPanelParser {

	@Override
	public JPanel parseElement(Context context, Element source) {
		XPanel xPanel = (XPanel) super.parseElement(context, source);
		xPanel.setContext(context);
		String processorClassName = source.getAttributeValue(Const.PROCESSOR);
		if (processorClassName != null) {
			XProcessor processor = (XProcessor) BaseUtils.newInstance(processorClassName);
			processor.process(xPanel);
		}
		List<Element> processors = source.getChildren(Const.PROPERTY);
		if (processors != null && processors.size() > 0) {
			for (Element e : processors) {
				String className = e.getAttributeValue(Const.CLASS);
				if (StringUtils.isEmpty(className)) {
					className = e.getText().trim();
				}
				XProcessor processor = (XProcessor) BaseUtils.newInstance(className);
				processor.process(xPanel);
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

	public JPanel createPanel(Context context, Element source) {
		XPanel xpanel = new XPanel();
		if (context.getView() == null) {
			context.setView(xpanel);
		}
		return xpanel;
	}

}
