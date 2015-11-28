/**
 * 
 */
package com.xswing.framework.editor;

import java.util.List;

import javax.swing.JComponent;

import com.framework.common.BaseUtils;
import com.framework.proxy.interfaces.Bean;
import com.framework.proxy.interfaces.DynamicCollection;
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
			String[] splits = property.split("\\.");
			Object value = null;
			for (int i = 0; i < splits.length; i++) {
				String split = splits[i];
				value = BaseUtils.getProperty(data, split);
				if (data instanceof Bean) {
					((Bean) data).addPropertyChangeListener(split, (e) -> {
						reload();
					});
				}
				if (value instanceof DynamicCollection) {
					((DynamicCollection) data).addChangeListener((e) -> {
						reload();
					});
				}
				if (i < splits.length - 1) {
					data = value;
				}
			}

			this.setValue(value);

		}
	}

	protected void reload() {
		Object data = context.getData();
		if (data != null) {
			this.setValue(BaseUtils.getProperty(data, bind));
		}
	}

}
