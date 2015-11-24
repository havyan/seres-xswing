package com.xswing.framework.editor;

import javax.swing.JComboBox;

public class ComboBoxEditor extends AbstractEditor<JComboBox<?>, Object> {

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
		// TODO Auto-generated method stub

	}

}
