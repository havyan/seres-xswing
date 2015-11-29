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

	protected List<Validator<?>> validators;

	protected Context context;

	protected String bind;

	public T getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(JComponent component) {
		this.component = (T) component;
	}

	public List<Validator<?>> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator<?>> validators) {
		this.validators = validators;
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
				Bean bean = (Bean) data;
				String tempProperty = property;
				while (tempProperty != null) {
					bean.addPropertyChangeListener(tempProperty, (e) -> {
						valueChanged(e);
					});
					int index = tempProperty.lastIndexOf(".");
					if (index >= 0) {
						tempProperty = tempProperty.substring(0, index);
					} else {
						tempProperty = null;
					}
				}
			}
			this.setValue(value);
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

}
