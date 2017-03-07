package com.xswing.framework.editor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

@Component(name = "text", types = { JTextComponent.class })
public class TextEditor extends AbstractEditor<JTextComponent, String> {

	@Override
	public void setValue(Object value) {
		component.setText((String) value);
	}

	@Override
	public String getValue() {
		return component.getText().trim();
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

	public void setComponent(JComponent component) {
		super.setComponent(component);
		this.component.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				writeback();
			}
		});
	}
	
	public void setEditable(boolean editable) {
		component.setEditable(editable);
	}

}
