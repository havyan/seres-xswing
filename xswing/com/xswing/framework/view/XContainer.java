package com.xswing.framework.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.framework.log.Logger;

public class XContainer extends JPanel {
	private static final long serialVersionUID = 1L;

	protected XPanel xpanel;

	public XContainer(String path) {
		setLayout(new BorderLayout());
		String caller = new Throwable().getStackTrace()[1].getClassName();
		String contextPath = null;
		try {
			contextPath = Class.forName(caller).getResource("").toString();
		} catch (ClassNotFoundException e) {
			Logger.error(e);
		}
		xpanel = XPanelBuilder.build(contextPath, path);
		add(xpanel, BorderLayout.CENTER);
	}

	public Object $(String id) {
		return xpanel.getBean(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T $(String id, Class<T> cls) {
		return (T) $(id);
	}

}
