/**
 * 
 */
package com.xswing.framework.editor;

import javax.swing.JCheckBox;

/**
 * @author think
 *
 */
@Component(types = { JCheckBox.class })
public class CheckBoxEditor extends AbstractEditor<JCheckBox, Boolean> {

	protected void initComponent(JCheckBox checkBox) {
		checkBox.addActionListener((e) -> writeBack());
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

}
