package com.xswing.framework.view.parser;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
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
import com.xswing.framework.validator.XValidator;
import com.xswing.framework.view.Context;

@XElement(names = { Const.CBEAN })
public class ComponentParser<T extends JComponent> extends BeanParser<T> {

	protected void handle(Context context, T component, Element source) {
		super.handle(context, component, source);
		if (component != null) {
			String name = getString(source, Const.NAME);
			if (name != null) {
				component.setName(name);
			}
			Border border = createBorder(source);
			if (border != null) {
				component.setBorder(border);
			}
			Color background = createColor(getString(source, Const.BACKGROUND));
			if (background != null) {
				component.setBackground(background);
			}
			Color foreground = createColor(getString(source, Const.FOREGROUND));
			if (foreground != null) {
				component.setForeground(foreground);
			}
			String tooltip = getString(source, Const.TOOLTIP);
			if (StringUtils.isNotEmpty(tooltip)) {
				component.setToolTipText(tooltip);
			}
			String widthText = getString(source, Const.WIDTH);
			String heightText = getString(source, Const.HEIGHT);
			if (StringUtils.isNotEmpty(widthText) || StringUtils.isNotEmpty(heightText)) {
				component.setPreferredSize(createSize(widthText, heightText, component.getPreferredSize()));
			}
			widthText = getString(source, Const.MAXWIDTH);
			heightText = getString(source, Const.MAXHEIGHT);
			if (StringUtils.isNotEmpty(widthText) || StringUtils.isNotEmpty(heightText)) {
				component.setMaximumSize(createSize(widthText, heightText, component.getMaximumSize()));
			}
			widthText = getString(source, Const.MINWIDTH);
			heightText = getString(source, Const.MINHEIGHT);
			if (StringUtils.isNotEmpty(widthText) || StringUtils.isNotEmpty(heightText)) {
				component.setMinimumSize(createSize(widthText, heightText, component.getMinimumSize()));
			}
			String opaque = getString(source, "opaque");
			if (StringUtils.isNoneEmpty(opaque)) {
				component.setOpaque(Boolean.valueOf(opaque));
			}
		}
	}

	private Dimension createSize(String widthText, String heightText, Dimension size) {
		double width = StringUtils.isNotEmpty(widthText) ? Double.valueOf(widthText) : size.width;
		double height = StringUtils.isNotEmpty(heightText) ? Double.valueOf(heightText) : size.height;
		size = new Dimension();
		size.setSize(width, height);
		return size;
	}

	protected Color createColor(String colorText) {
		Color color = null;
		if (StringUtils.isNotEmpty(colorText)) {
			if (colorText.contains(",")) {
				String[] splits = colorText.split(",");
				if (splits.length >= 3) {
					int r = Integer.valueOf(splits[0]);
					int g = Integer.valueOf(splits[1]);
					int b = Integer.valueOf(splits[2]);
					int a = splits.length > 3 ? Integer.valueOf(splits[3]) : 255;
					color = new Color(r, g, b, a);
				}
			} else {
				color = (Color) BaseUtils.getStaticValue(Color.class, colorText);
			}
		}
		return color;
	}

	protected Border createBorder(Element source) {
		String borderText = source.getAttributeValue(Const.BORDER);
		Border border = null;
		if (borderText != null && borderText.length() > 0) {
			if (borderText.equals(Const.NONE)) {
				border = BorderFactory.createEmptyBorder();
			} else if (borderText.equals(Const.LINE)) {
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
			int top = 0, right = 0, bottom = 0, left = 0;
			String[] splits = paddingText.split("\\s+");
			if (splits.length == 1) {
				int padding = Integer.valueOf(splits[0]);
				top = right = bottom = left = padding;
			} else if (splits.length == 4) {
				top = Integer.valueOf(splits[0]);
				right = Integer.valueOf(splits[1]);
				bottom = Integer.valueOf(splits[2]);
				left = Integer.valueOf(splits[3]);
			}
			Border paddingBorder = new EmptyBorder(top, left, bottom, right);
			border = new CompoundBorder(border, paddingBorder);
		}
		return border;
	}

	@Override
	protected void bind(Context context, String id, T bean, Element source) {
		super.bind(context, id, bean, source);
		JComponent component = (JComponent) bean;
		String editorText = source.getAttributeValue(Const.EDITOR);
		Editor<? extends JComponent, ?> editor = EditorFactory.create(context, component, editorText);
		String valueText = getString(source, Const.VALUE);
		if (StringUtils.isNotEmpty(valueText)) {
			editor.setValueProperty(findProperty(valueText));
			bindSet(context, bean, valueText, value -> {
				editor.setValue(value);
			});
		}
		editor.setValidators(parseValidators(context, source, editor));
		Action<?, ?, ?> action = createAction(context, editor, source);
		if (action != null) {
			editor.setAction(action);
		}
		context.setEditor(id, editor);
	}

	protected List<Validator<?>> parseValidators(Context context, Element source, Editor<? extends JComponent, ?> editor) {
		List<Element> children = source.getChildren(Const.VALIDATOR);
		List<Validator<?>> validtors = new ArrayList<Validator<?>>();
		if (children != null && children.size() > 0) {
			for (Element child : children) {
				Validator<?> validator = (Validator<?>) ParserEngine.parse(context, child);
				if (validator instanceof XValidator) {
					XValidator<?, ?, ?> xvalidator = (XValidator<?, ?, ?>) validator;
					xvalidator.setEditor(editor);
					xvalidator.setModel(context.getModel());
					xvalidator.setView(context.getView());
				}
				validtors.add(validator);
			}
		}
		return validtors;
	}

	protected Action<?, ?, ?> createAction(Context context, Editor<? extends JComponent, ?> editor, Element source) {
		String actionClass = source.getAttributeValue(Const.ACTION);
		if (StringUtils.isNotEmpty(actionClass)) {
			Action<?, ?, ?> action = (Action<?, ?, ?>) BaseUtils.newInstance(actionClass);
			action.setModel(context.getModel());
			action.setView(context.getView());
			action.setComponent(editor.getComponent());
			return action;
		}
		return null;
	}

}
