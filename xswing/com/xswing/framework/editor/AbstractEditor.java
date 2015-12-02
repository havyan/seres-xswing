/**
 * 
 */
package com.xswing.framework.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import com.framework.common.BaseUtils;
import com.framework.events.PropertyChangeListenerProxy;
import com.framework.log.Logger;
import com.framework.proxy.interfaces.Bean;
import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.model.AppModel;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.parser.Const;

/**
 * @author think
 *
 */
public abstract class AbstractEditor<T extends JComponent, V> implements Editor<T, V> {

	protected T component;

	protected List<Validator> validators;

	protected Context context;

	protected Map<String, String> binds = new HashMap<String, String>();

	public void init() {

	}

	public T getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(JComponent component) {
		this.component = (T) component;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public String validate() {
		if (this.validators != null && this.validators.size() > 0) {
			V value = this.getValue();
			StringBuilder sb = new StringBuilder();
			for (Validator validator : this.validators) {
				String result = validator.validate(value);
				if (result != null) {
					sb.append(result);
				}
			}
			if (sb.length() > 0) {
				return sb.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Context getContext() {
		return context;
	}

	public void handleEvent(AppEvent event) {
		if (AppModel.DATA_CHANGED.equals(event.getName())) {
			this.rebind();
		}
	}

	protected void rebind() {
		unbind();
		for (Map.Entry<String, String> entry : binds.entrySet()) {
			addBind(entry.getKey(), entry.getValue());
		}
	}

	public void setContext(Context context) {
		if (this.context != null && this.context.getModel() != null) {
			unbind();
			this.context.getModel().removeAppListener(this);
		}
		this.context = context;
		if (context.getModel() != null) {
			context.getModel().addAppListener(this);
		}
	}

	protected void unbind() {
		if (context.getData() != null && context.getData() instanceof Bean) {
			Bean bean = (Bean) context.getData();
			bean.removePropertyChangeListenerFrom(this);
		}
	}

	public void addBind(String type, String property) {
		this.binds.put(type, property);
		this.bind(property, new PropertyChangeListenerProxy(this) {
			public void propertyChange(PropertyChangeEvent e) {
				propertyChanged(type, e);
			}
		});
		if (context.getData() != null) {
			this.setBindValue(type, BaseUtils.getProperty(context.getData(), property));
		}
	}

	public void setBindValue(String type, Object value) {
		if (type.equals(Const.VALUE)) {
			this.setValue(value);
		}
	}

	protected void bind(String property, PropertyChangeListener listener) {
		if (context.getData() != null && context.getData() instanceof Bean) {
			Bean bean = (Bean) context.getData();
			bean.addPropertyChangeListener(property, listener);
		}
	}

	protected void propertyChanged(String type, PropertyChangeEvent e) {
		this.setBindValue(type, getDataProperty(this.binds.get(type)));
	}

	protected void writeBack() {
		if (binds.get(Const.VALUE) != null) {
			String result = this.validate();
			if (result == null && context.getData() != null) {
				String bind = this.binds.get(Const.VALUE);
				Logger.debug("Write back value to: " + bind);
				BaseUtils.setProperty(context.getData(), bind, this.getValue());
			}
		}
	}

	protected Object getDataProperty(String property) {
		return BaseUtils.getProperty(context.getData(), property);
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}

	public void reset() {

	}

	public void registerAction(Action<?, ?, ?> action) {

	}
}
