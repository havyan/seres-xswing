package com.xswing.framework.view.parser;

import javax.swing.JTree;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TREE })
public class TreeParser extends ComponentParser<JTree> {

	@Override
	public JTree parseElement(Context context, Element source) {
		JTree tree = super.parseElement(context, source);
		tree.setRootVisible(getBoolean(source, Const.ROOTVISIBLE, true));
		return tree;
	}

	@Override
	protected void bind(Context context, String id, JTree bean, Element source) {
		super.bind(context, id, bean, source);
		String text = getString(source, Const.ROWHEIGHT);
		if (StringUtils.isNotEmpty(text)) {
			bindSet(context, bean, text, value -> {
				if (value != null) {
					if (value instanceof Integer) {
						bean.setRowHeight((int) value);
					} else {
						bean.setRowHeight(Integer.valueOf(value.toString()));
					}
				}
			});
		}
	}

}