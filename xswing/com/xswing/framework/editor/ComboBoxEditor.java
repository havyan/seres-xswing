package com.xswing.framework.editor;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

import javax.swing.JComboBox;

import com.xswing.framework.view.parser.Const;

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
	protected void propertyChanged(String type, PropertyChangeEvent e) {
		super.propertyChanged(type, e);
		if (type.equals(Const.ITEMS)) {
			resetItems(e.getNewValue());
		}
	}

	@Override
	public void reset() {
		if (component.getItemCount() > 0) {
			component.setSelectedIndex(0);
		}
	}

	protected void initComponent(JComboBox comboBox) {
		comboBox.addActionListener((e) -> writeBack());
	}

	@Override
	public void setBindValue(String type, Object value) {
		super.setBindValue(type, value);
		if (type.equals(Const.ITEMS)) {
			resetItems(value);
		}
	}

	@SuppressWarnings("unchecked")
	private void resetItems(Object value) {
		component.removeAllItems();
		if (value != null) {
			if (value instanceof Collection) {
				for (Object e : (Collection<?>) value) {
					component.addItem(e);
				}
			} else if (value instanceof Object[]) {
				for (Object e : (Object[]) value) {
					component.addItem(e);
				}
			}
		}
	}

}
