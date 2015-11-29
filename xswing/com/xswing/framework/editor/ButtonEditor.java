package com.xswing.framework.editor;

import javax.swing.JButton;

@Component(types = { JButton.class })
public class ButtonEditor extends AbstractEditor<JButton, String> {

	@Override
	public void setValue(Object value) {
		component.setText((String) value);
	}

	@Override
	public String getValue() {
		return component.getText();
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		component.setText("");
	}

}
