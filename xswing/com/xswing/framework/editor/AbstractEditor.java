/**
 * 
 */
package com.xswing.framework.editor;

import java.util.List;

import javax.swing.JComponent;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppEvent;
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

	protected String valueProperty;

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
	}

	public void setContext(Context context) {
		if (this.context != null && this.context.getModel() != null) {
			this.context.getModel().removeAppListener(this);
		}
		this.context = context;
		if (context.getModel() != null) {
			context.getModel().addAppListener(this);
		}
	}

	public void setValueProperty(String property) {
		this.valueProperty = property;
	}

	public String getValueProperty() {
		return valueProperty;
	}

	protected void writeBack() {
		String result = this.validate();
		if (result == null && context.getData() != null) {
			String property = this.getValueProperty();
			Logger.debug("Write back value to: " + property);
			BaseUtils.setProperty(context.getData(), property, this.getValue());
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
