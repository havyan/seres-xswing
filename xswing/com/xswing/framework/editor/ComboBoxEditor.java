package com.xswing.framework.editor;

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
	public void reset() {
		if (component.getItemCount() > 0) {
			component.setSelectedIndex(0);
		}
	}

	public void init() {
		component.addActionListener((e) -> writeBack());
	}

	@Override
	public void setBindValue(String type, Object value) {
		super.setBindValue(type, value);
		if (type.equals(Const.ITEMS)) {
			setItems(value);
		}
	}

	@SuppressWarnings("unchecked")
	private void setItems(Object value) {
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
