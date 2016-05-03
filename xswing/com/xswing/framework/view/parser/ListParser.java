package com.xswing.framework.view.parser;

import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

@SuppressWarnings("rawtypes")
@XElement(names = { Const.LIST })
public class ListParser extends ComponentParser<JList> {

	@SuppressWarnings({ "unchecked" })
	@Override
	public JList<?> parseElement(Context context, Element source) {
		JList list = super.parseElement(context, source);
		List<Element> elements = source.getChildren(Const.ELEMENT);
		if (elements != null && elements.size() > 0) {
			DefaultListModel<Object> listModel = new DefaultListModel<Object>();
			list.setModel(listModel);
			for (Element element : elements) {
				String elementValue = element.getAttributeValue(Const.VALUE);
				if (StringUtils.isEmpty(elementValue)) {
					Element valueElement = element.getChild(Const.VALUE);
					if (valueElement != null) {
						listModel.addElement(ParserEngine.parse(context, valueElement));
					} else {
						elementValue = element.getText();
						if (StringUtils.isNotEmpty(elementValue)) {
							listModel.addElement(elementValue);
						}
					}
				} else {
					listModel.addElement(elementValue);
				}
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void bind(Context context, String id, JList bean, Element source) {
		bindSet(context, bean, getString(source, Const.ELEMENTS), value -> {
			if (value != null) {
				DefaultListModel<Object> listModel = new DefaultListModel<Object>();
				bean.setModel(listModel);
				if (value instanceof Collection) {
					for (Object e : (Collection<?>) value) {
						listModel.addElement(e);
					}
				} else if (value instanceof Object[]) {
					for (Object e : (Object[]) value) {
						listModel.addElement(e);
					}
				} else if (value instanceof String) {
					String text = (String) value;
					if (BaseUtils.isClass(text)) {
						Class<?> cls = BaseUtils.getClass(text);
						for (Object e : cls.getEnumConstants()) {
							listModel.addElement(e);
						}
					}
				}
			}
		});
		super.bind(context, id, bean, source);
	}
}
