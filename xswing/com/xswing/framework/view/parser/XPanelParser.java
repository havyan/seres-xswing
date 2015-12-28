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
		List<Element> components = source.getChildren(Const.COMPONENT);
		if (components != null) {
			components.stream().forEach(e -> ParserEngine.parse(context, e));
		}
		List<Element> beans = source.getChildren(Const.BEAN);
		if (beans != null) {
			beans.stream().forEach(e -> ParserEngine.parse(context, e));
		}
		XPanel xPanel = (XPanel) super.parseElement(context, source);
		Class<?> processorClass = getClass(source, Const.PROCESSOR);
		if (processorClass != null) {
			XProcessor processor = (XProcessor) BaseUtils.newInstance(processorClass);
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
		return xPanel;
	}

	protected Class<?> findClass(Element source) {
		return XPanel.class;
	}

	public JPanel createPanel(Context context, Element source) {
		XPanel xpanel = (XPanel) createBean(context, source);
		xpanel.setContext(context);
		if (context.getView() == null) {
			context.setView(xpanel);
		}
		return xpanel;
	}

}
