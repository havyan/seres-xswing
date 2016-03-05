package com.xswing.framework.editor;

import javax.swing.JComboBox;
import javax.swing.JComponent;

@SuppressWarnings("rawtypes")
@Component(name = "comboBox", types = { JComboBox.class })
public class ComboBoxEditor extends AbstractEditor<JComboBox, Object> {

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) {
		if(component.getItemCount() == 0) {
			component.addItem(value);
		}
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

	public void setComponent(JComponent component) {
		super.setComponent(component);
		this.component.addActionListener((e) -> writeback());
	}

}
