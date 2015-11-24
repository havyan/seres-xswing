package com.xswing.framework.editor;

import javax.swing.JLabel;

@Component(types = { JLabel.class })
public class LabelEditor extends AbstractEditor<JLabel, String> {

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
