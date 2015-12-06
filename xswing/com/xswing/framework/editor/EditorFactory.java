package com.xswing.framework.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.commons.lang3.StringUtils;

import com.framework.common.BaseUtils;
import com.xswing.framework.view.Context;

@SuppressWarnings("unchecked")
public class EditorFactory {

	public static final Map<String, Class<Editor<? extends JComponent, ?>>> NAME_EDITORS = new HashMap<String, Class<Editor<? extends JComponent, ?>>>();

	public static final Map<Class<?>, Class<Editor<? extends JComponent, ?>>> CLASS_EDITORS = new HashMap<Class<?>, Class<Editor<? extends JComponent, ?>>>();

	public static void register(Class<Editor<? extends JComponent, ?>> cls) {
		Component component = cls.getAnnotation(Component.class);
		if (component != null) {
			NAME_EDITORS.put(component.name(), cls);
			for (Class<?> type : component.types()) {
				CLASS_EDITORS.put(type, cls);
			}
		}
	}

	static {
		Set<Class<?>> clses = BaseUtils.getClasses("com.xswing.framework.editor");
		for (Class<?> cls : clses) {
			if (Editor.class.isAssignableFrom(cls)) {
				register((Class<Editor<? extends JComponent, ?>>) cls);
			}
		}
	}

	public static Editor<? extends JComponent, ?> create(Context context, JComponent component, String editorText) {
		if (component instanceof Editor) {
			return (Editor<? extends JComponent, ?>) component;
		}
		Editor<? extends JComponent, ?> editor = null;
		if (StringUtils.isNotEmpty(editorText)) {
			Class<Editor<? extends JComponent, ?>> cls = NAME_EDITORS.get(editorText);
			if (cls != null) {
				editor = (Editor<? extends JComponent, ?>) BaseUtils.newInstance(cls);
			} else {
				editor = (Editor<? extends JComponent, ?>) BaseUtils.newInstance(editorText);
			}
			if (editor != null) {
				editor.setContext(context);
				editor.setComponent(component);
			}
		} else if (component instanceof Editor) {
			return (Editor<? extends JComponent, ?>) component;
		} else if (component instanceof EditorPeer) {
			return ((EditorPeer) component).getEditor();
		} else {
			editor = createDefault(context, component);
		}
		return editor;
	}

	public static Editor<? extends JComponent, ?> createDefault(Context context, JComponent component) {
		Editor<? extends JComponent, ?> editor = null;
		for (Map.Entry<Class<?>, Class<Editor<? extends JComponent, ?>>> entry : CLASS_EDITORS.entrySet()) {
			if (entry.getKey().isInstance(component)) {
				editor = (Editor<? extends JComponent, ?>) BaseUtils.newInstance(entry.getValue());
				break;
			}
		}
		if (editor == null) {
			editor = new DefaultEditor();
		}
		editor.setContext(context);
		editor.setComponent(component);
		return editor;
	}

}
