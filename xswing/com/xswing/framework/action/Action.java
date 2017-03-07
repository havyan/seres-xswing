/**
 * 
 */
package com.xswing.framework.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.view.View;

/**
 * @author yhw
 *
 */
public abstract class Action<M extends AppModel<?>, V extends View, C extends JComponent> extends AbstractAction implements AppListener, PropertyChangeListener {

	protected M model;

	protected V view;

	protected C component;

	@Override
	public void handleEvent(AppEvent event) {
		setEnabled(isEnabled());
		setVisible(isVisible());
	}

	public void propertyChange(PropertyChangeEvent e) {
		setEnabled(isEnabled());
		setVisible(isVisible());
	}

	public M getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setModel(AppModel<?> model) {
		this.model = (M) model;
		if (model != null) {
			model.addAppListener(this);
			model.bind(this);
		}
	}

	public V getView() {
		return view;
	}

	@SuppressWarnings("unchecked")
	public void setView(View view) {
		this.view = (V) view;
	}

	public C getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(JComponent component) {
		this.component = (C) component;
	}

	public Object $(String id) {
		return view.getBean(id);
	}

	public <T> T $(String id, Class<T> cls) {
		return view.getBean(id, cls);
	}

	public void setEnabled(boolean enabled) {
		if (component != null) {
			component.setEnabled(enabled);
		}
	}

	public void setVisible(boolean visible) {
		if (component != null) {
			component.setVisible(visible);
		}
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return true;
	}

}
