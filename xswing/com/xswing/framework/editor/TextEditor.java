package com.xswing.framework.editor;

import javax.swing.text.JTextComponent;

@Component(name = "text", types = { JTextComponent.class })
public class TextEditor extends AbstractEditor<JTextComponent, String> {

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
		component.setEditable(enabled);
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		component.setText("");
	}

}
