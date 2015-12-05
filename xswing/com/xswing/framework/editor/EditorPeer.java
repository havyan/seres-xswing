package com.xswing.framework.editor;

import javax.swing.JComponent;

public interface EditorPeer {

	public void setValue(Object value);

	public Object getValue();
	
	public JComponent getComponent();

	default Editor<?, ?> getEditor() {
		return new DefaultEditor() {
			@Override
			public void setValue(Object value) {
				EditorPeer.this.setValue(value);
			}

			@Override
			public Object getValue() {
				return EditorPeer.this.getValue();
			}
		};
	}

}
