package com.xswing.framework.editor;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

@Component(types = { JTextComponent.class })
public class TextEditor extends AbstractEditor<JTextComponent, String> {

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
		component.setEditable(enabled);
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		component.setText("");
	}

}
