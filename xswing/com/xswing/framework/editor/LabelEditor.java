package com.xswing.framework.editor;

import javax.swing.JLabel;

@Component(types = { JLabel.class })
public class LabelEditor extends AbstractEditor<JLabel, String> {

	@Override
	public void setValue(String value) {
		component.setText(value);
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
