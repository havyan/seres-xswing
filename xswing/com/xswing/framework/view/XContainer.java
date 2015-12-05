package com.xswing.framework.view;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.editor.Editor;
import com.xswing.framework.editor.EditorPeer;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.model.AppModel;

public class XContainer<M extends AppModel<?>> extends JPanel implements View, EditorPeer {
	private static final long serialVersionUID = 1L;

	protected XPanel xpanel;

	protected M model;

	public XContainer(String path) {
		this(path, (M) null, (Map<String, Object>) null);
	}

	public XContainer(URL url) {
		this(url, (M) null, (Map<String, Object>) null);
	}

	public XContainer(String path, M model) {
		this(path, model, (Map<String, Object>) null);
	}

	public XContainer(URL url, M model) {
		this(url, model, (Map<String, Object>) null);
	}

	public XContainer(String path, M model, Map<String, Object> prdefinedBeans) {
		this.model = model;
		initXPanel(path, prdefinedBeans);
		initReferences();
	}

	public XContainer(URL url, M model, Map<String, Object> prdefinedBeans) {
		this.model = model;
		initXPanel(url, prdefinedBeans);
		initReferences();
	}

	private void initXPanel(URL url, Map<String, Object> prdefinedBeans) {
		add(XPanelBuilder.build(url, model, this, prdefinedBeans), BorderLayout.CENTER);
	}

	private void initXPanel(String path, Map<String, Object> prdefinedBeans) {
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
			xpanel = XPanelBuilder.build(contextPath, path, model, this, prdefinedBeans);
		} else {
			xpanel = XPanelBuilder.build(path, model, this, prdefinedBeans);
		}
		add(xpanel, BorderLayout.CENTER);
	}

	private void initReferences() {
		Map<String, Object> beans = xpanel.getBeans();
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			if (BaseUtils.hasField(this, entry.getKey())) {
				BaseUtils.setFieldValue(this, entry.getKey(), entry.getValue());
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

	@Override
	public JComponent getComponent(String id) {
		return xpanel.getComponent(id);
	}

	@Override
	public void setValue(String id, Object value) {
		xpanel.setValue(id, value);
	}

	@Override
	public <T extends JComponent> T getComponent(String id, Class<T> cls) {
		return xpanel.getComponent(id, cls);
	}

	@Override
	public Map<String, Object> getBeans() {
		return xpanel.getBeans();
	}

	@Override
	public Context getContext() {
		return xpanel.getContext();
	}

	@Override
	public M getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(AppModel<?> model) {
		this.model = (M) model;
	}

	@Override
	public void handleEvent(AppEvent event) {

	}

	public void destroy() {
		this.xpanel.destroy();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.destroy();
	}

	public void setValue(Object value) {
	}

	public Object getValue() {
		return null;
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

}
