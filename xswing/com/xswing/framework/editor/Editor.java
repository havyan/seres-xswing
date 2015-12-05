package com.xswing.framework.editor;

import java.util.List;

import javax.swing.JComponent;

import com.framework.common.BaseUtils;
import com.framework.log.Logger;
import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;

public interface Editor<T extends JComponent, V> extends AppListener {

	public void init();

	public void setValue(Object value);

	public V getValue();

	public void reset();

	public void setValueProperty(String property);

	public String getValueProperty();

	public T getComponent();

	public void setComponent(JComponent component);

	public void setContext(Context context);

	public Context getContext();

	public void setValidators(List<Validator> validators);

	public List<Validator> getValidators();

	public void registerAction(Action<?, ?, ?> action);

	default void setEnabled(boolean enabled) {
		T component = this.getComponent();
		if (component != null) {
			component.setEnabled(enabled);
		}
	}

	default String check() {
		List<Validator> validators = this.getValidators();
		if (validators != null && validators.size() > 0) {
			V value = this.getValue();
			StringBuilder sb = new StringBuilder();
			for (Validator validator : validators) {
				String result = validator.validate(value);
				if (result != null) {
					sb.append(result).append("\n");
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

	default void writeBack() {
		Context context = this.getContext();
		String result = this.check();
		if (result == null && context != null && context.getData() != null) {
			String property = this.getValueProperty();
			Logger.debug("Write back value to: " + property);
			BaseUtils.setProperty(context.getData(), property, this.getValue());
		}
	}

}
