package com.xswing.framework.editor;

import javax.swing.JComponent;

import com.xswing.framework.view.XPanel;

@Component(name = "xpanel", types = { XPanel.class })
public class XPanelEditor extends AbstractEditor<XPanel, Object> {

	public void setComponent(JComponent component) {
		super.setComponent(component);
	}

	@Override
	public void setValue(Object value) {
		component.getModel().setData(value);
	}

	@Override
	public Object getValue() {
		return component.getModel().getData();
	}

}
