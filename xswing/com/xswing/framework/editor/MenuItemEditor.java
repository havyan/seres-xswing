package com.xswing.framework.editor;

import javax.swing.JMenuItem;

import com.xswing.framework.action.Action;

@Component(name = "menuItem", types = { JMenuItem.class })
public class MenuItemEditor extends AbstractEditor<JMenuItem, String> {

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

	public void setAction(Action<?, ?, ?> action) {
		component.addActionListener(action);
	}

}