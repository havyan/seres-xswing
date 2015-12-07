/**
 * 
 */
package com.xswing.framework.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang3.ArrayUtils;

import com.framework.common.BaseUtils;
import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.component.PopupMessage;

/**
 * @author think
 *
 */
public abstract class AbstractEditor<T extends JComponent, V> implements Editor<T, V> {

	protected T component;

	protected List<Validator> validators = new ArrayList<Validator>();

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

	protected Object getDataProperty(String property) {
		return BaseUtils.getProperty(context.getData(), property);
	}

	public void reset() {

	}

	public void registerAction(Action<?, ?, ?> action) {

	}

	public void showErrors(String[] errors) {
		if (ArrayUtils.isNotEmpty(errors)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < errors.length; i++) {
				sb.append(i + 1).append(". ").append(errors[i]).append("\n");
			}
			PopupMessage.show(getComponent(), "Error", sb.toString(), Color.WHITE, Color.RED, Color.RED);
		}
	}
}
