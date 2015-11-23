/**
 * 
 */
package com.xswing.framework.test;

import javax.swing.JComboBox;

import com.xswing.framework.processor.AbstractXProcessor;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;

/**
 * @author HWYan
 *
 */
public class TestProcessor extends AbstractXProcessor {

	public TestProcessor(XPanel xpanel) {
		super(xpanel);
	}

	@Override
	public void process() {
		JComboBox projectComBox = xpanel.getComponent("project", JComboBox.class);
		String[] projects = new String[] { "Project11", "Project22", "Project33" };
		if (projectComBox != null) {
			for (String project : projects) {
				projectComBox.addItem(project);
			}
		}
	}

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub

	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
