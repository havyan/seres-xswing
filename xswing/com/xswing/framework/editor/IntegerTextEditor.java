package com.xswing.framework.editor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;

@Component(name = "integerText")
public class IntegerTextEditor extends AbstractEditor<JTextComponent, Integer> {

	@Override
	public void setValue(Object value) {
		if (value != null) {
			component.setText(value + "");
		}
	}

	@Override
	public void setComponent(JComponent component) {
		super.setComponent(component);
		JTextComponent textComponent = (JTextComponent) component;
		textComponent.setDocument(new IntegerDocument());
		this.component.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				writeback();
			}
		});
	}

	@Override
	public Integer getValue() {
		String text = component.getText();
		if (StringUtils.isNoneEmpty(text)) {
			return Integer.valueOf(text);
		} else {
			return 0;
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEditable(enabled);
		component.setEnabled(enabled);
	}
	
	public void setEditable(boolean editable) {
		component.setEditable(editable);
	}

	@Override
	public void reset() {
		component.setText("");
	}

}
