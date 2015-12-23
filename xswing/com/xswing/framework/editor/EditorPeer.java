package com.xswing.framework.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;

public interface EditorPeer {

	public void setValue(Object value);

	public Object getValue();

	public JComponent getComponent();

	public String[] check();

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

			@Override
			public String[] validate() {
				List<String> errors = new ArrayList<String>();
				errors.addAll(Arrays.asList(super.validate()));
				errors.addAll(Arrays.asList(EditorPeer.this.check()));
				return errors.toArray(new String[0]);
			}
		};
	}

}
