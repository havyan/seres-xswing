/**
 * 
 */
package com.xswing.framework.editor;

import javax.swing.JCheckBox;

import com.xswing.framework.action.Action;

/**
 * @author think
 *
 */
@Component(types = { JCheckBox.class })
public class CheckBoxEditor extends AbstractEditor<JCheckBox, Boolean> {

	public void init() {
		component.addActionListener((e) -> writeBack());
	}

	@Override
	public void setValue(Object value) {
		component.setSelected((boolean) value);
	}

	@Override
	public Boolean getValue() {
		return component.isSelected();
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}

	public void registerAction(Action<?, ?, ?> action) {
		component.addActionListener(action);
	}

}
