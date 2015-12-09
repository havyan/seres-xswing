package com.xswing.framework.validator;

import javax.swing.JComponent;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.view.View;

public abstract class XValidator<M extends AppModel<?>, V extends View, T> implements Validator<T> {

	protected M model;

	protected V view;

	protected Editor<? extends JComponent, ?> editor;

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

	public Editor<? extends JComponent, ?> getEditor() {
		return editor;
	}

	public void setEditor(Editor<? extends JComponent, ?> editor) {
		this.editor = editor;
	}

}
