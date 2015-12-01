/**
 * 
 */
package com.xswing.framework.view;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.model.AppModel;

/**
 * @author yhw
 * 
 */
public class XPanel extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	protected AppModel<?> model;

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

	public Editor<? extends JComponent, ?> getEditor(String id) {
		return context.getEditor(id);
	}

	public Object getValue(String id) {
		return getEditor(id).getValue();
	}

	public void setValue(String id, Object value) {
		getEditor(id).setValue(value);
	}

	@SuppressWarnings("unchecked")
	public <T extends JComponent> T getComponent(String id, Class<T> cls) {
		return (T) getComponent(id);
	}

	public Map<String, Object> getBeans() {
		return context.getBeans();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public AppModel<?> getModel() {
		return model;
	}

	@Override
	public void setModel(AppModel<?> model) {
		this.model = model;
	}

	@Override
	public void handleEvent(AppEvent event) {
		
	}
}
