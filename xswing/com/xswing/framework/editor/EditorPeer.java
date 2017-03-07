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
	
	default void setEnabled(boolean enabled) {
		JComponent component = this.getComponent();
		if (component != null) {
			component.setEnabled(enabled);
		}
	}
	
	default void setEditable(boolean editable) {}

	default Editor<?, ?> createEditor() {
		return new DefaultEditor() {
			@Override
			public void setValue(Object value) {
				EditorPeer.this.setValue(value);
			}

			@Override
			public Object getValue() {
				return EditorPeer.this.getValue();
			}
			
			public JComponent getComponent() {
				return EditorPeer.this.getComponent();
			}

			@Override
			public String[] validate() {
				List<String> errors = new ArrayList<String>();
				errors.addAll(Arrays.asList(super.validate()));
				errors.addAll(Arrays.asList(EditorPeer.this.check()));
				return errors.toArray(new String[0]);
			}
			
			public void setEnabled(boolean enabled) {
				EditorPeer.this.setEnabled(enabled);
			}
			
			public void setEditable(boolean editable) {
				EditorPeer.this.setEditable(editable);
			}
		};
	}

}
