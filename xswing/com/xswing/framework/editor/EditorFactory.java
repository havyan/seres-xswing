package com.xswing.framework.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.commons.lang3.StringUtils;

import com.framework.common.BaseUtils;

@SuppressWarnings("unchecked")
public class EditorFactory {

	public static final Map<Class<?>, Class<Editor<? extends JComponent, ?>>> EDITORS = new HashMap<Class<?>, Class<Editor<? extends JComponent, ?>>>();

	public static void register(Class<Editor<? extends JComponent, ?>> cls) {
		Component component = cls.getAnnotation(Component.class);
		if (component != null) {
			for (Class<?> type : component.types()) {
				EDITORS.put(type, cls);
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

	public static Editor<? extends JComponent, ?> create(JComponent component, String editorClass) {
		if(component instanceof Editor) {
			return (Editor<? extends JComponent, ?>) component;
		}
		Editor<? extends JComponent, ?> editor = null;
		if (StringUtils.isNotEmpty(editorClass)) {
			editor = (Editor<? extends JComponent, ?>) BaseUtils.newInstance(editorClass);
			if (editor != null) {
				editor.setComponent(component);
			}
		} else {
			editor = createDefault(component);
		}
		return editor;
	}

	public static Editor<? extends JComponent, ?> createDefault(JComponent component) {
		Editor<? extends JComponent, ?> editor = null;
		for (Map.Entry<Class<?>, Class<Editor<? extends JComponent, ?>>> entry : EDITORS.entrySet()) {
			if (entry.getKey().isInstance(component)) {
				editor = (Editor<? extends JComponent, ?>) BaseUtils.newInstance(entry.getValue());
				break;
			}
		}
		if (editor == null) {
			editor = new DefaultEditor();
		}
		editor.setComponent(component);
		return editor;
	}

}
