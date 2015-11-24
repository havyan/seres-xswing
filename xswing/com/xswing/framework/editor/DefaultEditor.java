package com.xswing.framework.editor;

import javax.swing.JComponent;

public class DefaultEditor extends AbstractEditor<JComponent, Object> {

	@Override
	public void setValue(Object value) {
		
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		
	}

}
