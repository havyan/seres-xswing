package com.xswing.framework.view.parser;

import java.util.List;

import javax.swing.JComboBox;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

@SuppressWarnings("rawtypes")
@XElement(names = { Const.COMBOBOX })
public class ComboBoxParser extends BeanParser<JComboBox> {
	@SuppressWarnings({ "unchecked" })
	@Override
	public JComboBox<?> parseElement(Context context, Element source) {
		JComboBox comboBox = super.parseElement(context, source);
		String itemsText = source.getAttributeValue(Const.ITEMS);
		if (StringUtils.isNotEmpty(itemsText)) {
			for (Object e : BaseUtils.getClass(itemsText).getEnumConstants()) {
				comboBox.addItem(e);
			}
		} else {
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
		}

		return comboBox;
	}
}
