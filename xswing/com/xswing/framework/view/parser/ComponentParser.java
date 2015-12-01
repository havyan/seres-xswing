package com.xswing.framework.view.parser;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.xswing.framework.action.Action;
import com.xswing.framework.editor.Editor;
import com.xswing.framework.editor.EditorFactory;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;

@XElement(names = { Const.TREE })
public class ComponentParser<T extends JComponent> extends BeanParser<T> {

	public static final Map<String, Class<?>> CLASS_MAP = new HashMap<String, Class<?>>();

	static {
		CLASS_MAP.put(Const.TREE, JTree.class);
	}

	public Class<?> findClass(Element source) {
		Class<?> cls = CLASS_MAP.get(source.getName());
		if (cls != null) {
			return cls;
		} else {
			return super.findClass(source);
		}
	}

	protected void handle(Context context, T component, Element source) {
		super.handle(context, component, source);
		if (component != null) {
			Dimension size = component.getPreferredSize();
			double width = getDouble(source, Const.WIDTH, size.getWidth());
			double height = getDouble(source, Const.HEIGHT, size.getHeight());
			size = new Dimension();
			size.setSize(width, height);
			component.setPreferredSize(size);
			String toolTip = getString(source, Const.TOOLTIP);
			if (StringUtils.isNotEmpty(toolTip)) {
				component.setToolTipText(toolTip);
			}
			Border border = createBorder(source);
			if (border != null) {
				component.setBorder(border);
			}
			String name = getString(source, Const.NAME);
			if (name != null) {
				component.setName(name);
			}
		}
	}

	protected Border createBorder(Element source) {
		String borderText = source.getAttributeValue(Const.BORDER);
		Border border = null;
		if (borderText != null && borderText.length() > 0) {
			if (borderText.equals(Const.LINE)) {
				border = BorderFactory.createLineBorder(Color.BLACK);
			} else if (borderText.equals(Const.ETCHED)) {
				border = BorderFactory.createEtchedBorder();
			} else if (borderText.equals(Const.RAISEDBEVEL)) {
				border = BorderFactory.createRaisedBevelBorder();
			} else if (borderText.equals(Const.LOWEREDBEVEL)) {
				border = BorderFactory.createLoweredBevelBorder();
			}
		}
		if (source.getName() != Const.TAB) {
			String title = source.getAttributeValue(Const.TITLE);
			if (title != null && title.length() > 0) {
				if (border != null) {
					border = BorderFactory.createTitledBorder(border, title);
				} else {
					border = BorderFactory.createTitledBorder(title);
				}
			}
		}
		String paddingText = source.getAttributeValue(Const.PADDING);
		if (StringUtils.isNotEmpty(paddingText)) {
			int padding = Integer.valueOf(paddingText);
			Border paddingBorder = new EmptyBorder(padding, padding, padding, padding);
			border = new CompoundBorder(border, paddingBorder);
		}
		return border;
	}

	@Override
	protected void bind(Context context, String id, T bean, Element source) {
		super.bind(context, id, bean, source);
		JComponent component = (JComponent) bean;
		String editorClass = source.getAttributeValue(Const.EDITOR);
		Editor<? extends JComponent, ?> editor = EditorFactory.create(component, editorClass);
		editor.setContext(context);
		bindEditor(editor, source);
		editor.setValidators(parseValidators(context, source));
		Action<?, ?, ?> action = createAction(context, bean, source);
		if (action != null) {
			editor.registerAction(action);
		}
		context.setEditor(id, editor);
	}

	protected void bindEditor(Editor<? extends JComponent, ?> editor, Element source) {
		String bind = source.getAttributeValue(Const.BIND);
		if (StringUtils.isNotEmpty(bind)) {
			editor.addBind(Const.VALUE, bind.trim());
		}
	}

	protected List<Validator> parseValidators(Context context, Element source) {
		List<Element> children = source.getChildren(Const.VALIDATOR);
		List<Validator> validtors = new ArrayList<Validator>();
		if (children != null && children.size() > 0) {
			for (Element child : children) {
				validtors.add((Validator) ParserEngine.parse(context, child));
			}
		}
		return validtors;
	}

	protected Action<?, ?, ?> createAction(Context context, T bean, Element source) {
		String actionClass = source.getAttributeValue(Const.ACTION);
		if (StringUtils.isNotEmpty(actionClass)) {
			Action<?, ?, ?> action = (Action<?, ?, ?>) BaseUtils.newInstance(actionClass);
			action.setModel(context.getModel());
			action.setView(context.getView());
			action.setComponent(bean);
			if (context.getModel() != null) {
				context.getModel().addAppListener(action);
			}
			return action;
		}
		return null;
	}

}
