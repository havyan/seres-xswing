package com.xswing.framework.view.parser;

import java.util.Collection;
import java.util.List;

import javax.swing.JComboBox;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

@SuppressWarnings("rawtypes")
@XElement(names = { Const.COMBOBOX })
public class ComboBoxParser extends ComponentParser<JComboBox> {
	@SuppressWarnings({ "unchecked" })
	@Override
	public JComboBox<?> parseElement(Context context, Element source) {
		JComboBox comboBox = super.parseElement(context, source);
		List<Element> items = source.getChildren(Const.ITEM);
		for (Element item : items) {
			String itemValue = item.getAttributeValue(Const.VALUE);
			if (StringUtils.isEmpty(itemValue)) {
				Element valueElement = item.getChild(Const.VALUE);
				if (valueElement != null) {
					comboBox.addItem(ParserEngine.parse(context, valueElement));
				} else {
					itemValue = item.getText();
					if (StringUtils.isNotEmpty(itemValue)) {
						comboBox.addItem(itemValue);
					}
				}
			} else {
				comboBox.addItem(itemValue);
			}
		}

		return comboBox;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void bind(Context context, String id, JComboBox bean, Element source) {
		super.bind(context, id, bean, source);
		bindSet(context, bean, getString(source, Const.ITEMS), value -> {
			bean.removeAllItems();
			if (value != null) {
				if (value instanceof Collection) {
					for (Object e : (Collection<?>) value) {
						bean.addItem(e);
					}
				} else if (value instanceof Object[]) {
					for (Object e : (Object[]) value) {
						bean.addItem(e);
					}
				} else if (value instanceof String) {
					String text = (String) value;
					if (BaseUtils.isClass(text)) {
						Class<?> cls = BaseUtils.getClass(text);
						for (Object e : cls.getEnumConstants()) {
							bean.addItem(e);
						}
					}
				}
			}
		});
	}
}
