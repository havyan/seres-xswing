package com.xswing.framework.test;

import javax.swing.JComboBox;

import com.xswing.framework.processor.XProcessor;
import com.xswing.framework.view.XPanel;

public class TestProcessor implements XProcessor{

	@Override
	public void process(XPanel xpanel) {
		JComboBox projectComBox = xpanel.getBean("project", JComboBox.class);
		String[] projects = new String[] { "Project11", "Project22", "Project33" };
		if (projectComBox != null) {
			for (String project : projects) {
				projectComBox.addItem(project);
			}
		}
	}

}
