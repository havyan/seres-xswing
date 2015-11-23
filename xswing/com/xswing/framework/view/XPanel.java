/**
 * 
 */
package com.xswing.framework.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author yhw
 * 
 */
public class XPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Context context;

	public XPanel() {
		this.setLayout(new BorderLayout());
	}

	public Object getBean(String id) {
		return context.getBean(id);
	}

	public JComponent getComponent(String id) {
		return (JComponent) getBean(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String id, Class<T> cls) {
		return (T) getBean(id);
	}

	@SuppressWarnings("unchecked")
	public <T extends JComponent> T getComponent(String id, Class<T> cls) {
		return (T) getComponent(id);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
