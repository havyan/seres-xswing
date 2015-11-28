package com.xswing.framework.view;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.editor.Editor;

public class XContainer extends JPanel {
	private static final long serialVersionUID = 1L;

	protected XPanel xpanel;

	public XContainer(String path) {
		initXPanel(path);
		initReferences();
	}

	public XContainer(URL url) {
		initXPanel(url);
		initReferences();
	}

	private void initXPanel(URL url) {
		add(XPanelBuilder.build(url), BorderLayout.CENTER);
	}

	private void initXPanel(String path) {
		setLayout(new BorderLayout());
		if (path.trim().startsWith(".")) {
			String caller = new Throwable().getStackTrace()[2].getClassName();
			String contextPath = null;
			try {
				contextPath = Class.forName(caller).getResource("").toString();
			} catch (ClassNotFoundException e) {
				Logger.error(e);
			}
			xpanel = XPanelBuilder.build(contextPath, path);
		} else {
			xpanel = XPanelBuilder.build(path);
		}
		add(xpanel, BorderLayout.CENTER);
	}

	private void initReferences() {
		Map<String, Object> beans = xpanel.getBeans();
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			if (BaseUtils.hasField(this, entry.getKey())) {
				BaseUtils.setField(this, entry.getKey(), entry.getValue());
			}
		}
	}

	public Editor<? extends JComponent, ?> getEditor(String id) {
		return xpanel.getEditor(id);
	}

	public Object getValue(String id) {
		return xpanel.getValue(id);
	}

	public Object getBean(String id) {
		return xpanel.getBean(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String id, Class<T> cls) {
		return (T) $(id);
	}

	public Object $(String id) {
		return getBean(id);
	}

	public <T> T $(String id, Class<T> cls) {
		return getBean(id, cls);
	}

}
