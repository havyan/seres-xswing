/**
 * 
 */
package com.xswing.framework.editor;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JComponent;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.framework.proxy.interfaces.Bean;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;

/**
 * @author think
 *
 */
public abstract class AbstractEditor<T extends JComponent, V> implements Editor<T, V> {

	protected T component;

	protected List<Validator> validators;

	protected Context context;

	protected String bind;

	public T getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(JComponent component) {
		this.component = (T) component;
		initComponent(this.component);
	}

	protected void initComponent(T component) {

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

	public void setContext(Context context) {
		this.context = context;
	}

	public void setBind(String property) {
		this.bind = property;
		Object data = context.getData();
		if (data != null) {
			Object value = BaseUtils.getProperty(data, property);
			if (data instanceof Bean) {
				bind((Bean) data, property);
			}
			this.setValue(value);
		}
	}

	protected void bind(Bean bean, String property) {
		while (property != null) {
			bean.addPropertyChangeListener(property, (e) -> {
				valueChanged(e);
			});
			int index = property.lastIndexOf(".");
			if (index >= 0) {
				property = property.substring(0, index);
			} else {
				property = null;
			}
		}
	}

	protected void writeBack() {
		String result = this.validate();
		if (result == null) {
			Logger.debug("Write back value to: " + this.bind);
			BaseUtils.setProperty(context.getData(), bind, this.getValue());
		}
	}

	protected void valueChanged(PropertyChangeEvent e) {
		Logger.debug("Property changed: " + e.getPropertyName());
		reload();
	}

	protected void reload() {
		Object data = context.getData();
		if (data != null) {
			this.setValue(BaseUtils.getProperty(data, bind));
		}
	}

	public void reset() {

	}

}
