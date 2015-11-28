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
		this(path, null);
	}

	public XContainer(URL url) {
		this(url, null);
	}

	public XContainer(String path, Object data) {
		initXPanel(path, data);
		initReferences();
	}

	public XContainer(URL url, Object data) {
		initXPanel(url, data);
		initReferences();
	}

	private void initXPanel(URL url, Object data) {
		add(XPanelBuilder.buildWithData(url, data), BorderLayout.CENTER);
	}

	private void initXPanel(String path, Object data) {
		setLayout(new BorderLayout());
		if (path.trim().startsWith(".")) {
			String caller = null;
			for (StackTraceElement trace : new Throwable().getStackTrace()) {
				if (!trace.getClassName().equals(XContainer.class.getName())) {
					caller = trace.getClassName();
					break;
				}
			}
			String contextPath = null;
			try {
				contextPath = Class.forName(caller).getResource("").toString();
			} catch (ClassNotFoundException e) {
				Logger.error(e);
			}
			xpanel = XPanelBuilder.buildWithData(contextPath, path, data);
		} else {
			xpanel = XPanelBuilder.buildWithData(path, data);
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
