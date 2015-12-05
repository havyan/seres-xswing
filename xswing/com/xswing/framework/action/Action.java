/**
 * 
 */
package com.xswing.framework.action;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.view.View;

/**
 * @author yhw
 *
 */
public abstract class Action<M extends AppModel<?>, V extends View, C extends JComponent> extends AbstractAction implements AppListener {

	protected M model;

	protected V view;

	protected Editor<? extends JComponent, ?> editor;

	@Override
	public void handleEvent(AppEvent event) {
	}

	public M getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setModel(AppModel<?> model) {
		this.model = (M) model;
	}

	public V getView() {
		return view;
	}

	@SuppressWarnings("unchecked")
	public void setView(View view) {
		this.view = (V) view;
	}

	@SuppressWarnings("unchecked")
	public C getComponent() {
		if (editor != null) {
			return (C) editor.getComponent();
		}
		return null;
	}

	public Object $(String id) {
		return view.getBean(id);
	}

	public <T> T $(String id, Class<T> cls) {
		return view.getBean(id, cls);
	}

	public Editor<? extends JComponent, ?> getEditor() {
		return editor;
	}

	public void setEditor(Editor<? extends JComponent, ?> editor) {
		this.editor = editor;
	}

	public void setEnabled(boolean enabled) {
		if (editor != null) {
			editor.setEnabled(enabled);
		}
	}

}
