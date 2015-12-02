package com.xswing.framework.editor;

import javax.swing.JComboBox;

@SuppressWarnings("rawtypes")
@Component(types = { JComboBox.class })
public class ComboBoxEditor extends AbstractEditor<JComboBox, Object> {

	@Override
	public void setValue(Object value) {
		component.setSelectedItem(value);
	}

	@Override
	public Object getValue() {
		return component.getSelectedItem();
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEditable(enabled);
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		if (component.getItemCount() > 0) {
			component.setSelectedIndex(0);
		}
	}

	public void init() {
		component.addActionListener((e) -> writeBack());
	}

}
