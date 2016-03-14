/**
 * 
 */
package com.xswing.framework.view.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.model.AppModel;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;
import com.xswing.framework.view.XPanelBuilder;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.IMPORT })
public class ImportParser extends ComponentParser<XPanel> {

	@Override
	public XPanel parseElement(Context context, Element source) {
		AppModel<?> model = context.getModel();
		String modelRef = getString(source, Const.MODEL);
		if (StringUtils.isNotEmpty(modelRef)) {
			Object bean = context.getBean(modelRef);
			if (bean != null) {
				model = (AppModel<?>) bean;
			}
		}
		Element modelElement = source.getChild(Const.MODEL);
		if (modelElement != null) {
			model = (AppModel<?>) ParserEngine.parse(context, modelElement);
		}
		String path = getString(source, Const.PATH);
		if (path != null) {
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("parent", context.getView());
			return XPanelBuilder.build(context.getPath(), path, model, null, beans);
		} else {
			return null;
		}
	}

}
