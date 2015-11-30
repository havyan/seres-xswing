/**
 * 
 */
package com.xswing.framework.action;

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
public abstract class Action<M extends AppModel<?>, V extends View, C extends JComponent> extends AbstractAction implements AppListener {

	protected M model;

	protected V view;

	protected C component;

	@Override
	public void handleEvent(AppEvent event) {
	}

	public M getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setModel(AppModel<?> model) {
		this.model = (M) model;
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

}