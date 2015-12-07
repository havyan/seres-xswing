package com.xswing.framework.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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

	public void showErrors(String[] errors);

	public void registerAction(Action<?, ?, ?> action);

	default void setEnabled(boolean enabled) {
		T component = this.getComponent();
		if (component != null) {
			component.setEnabled(enabled);
		}
	}

	default String[] check() {
		List<String> errors = new ArrayList<String>();
		List<Validator> validators = this.getValidators();
		if (validators != null && validators.size() > 0) {
			V value = this.getValue();
			for (Validator validator : validators) {
				String result = validator.validate(value);
				if (StringUtils.isNotEmpty(result)) {
					errors.add(result);
				}
			}
		}
		return errors.toArray(new String[0]);
	}

	default void writeBack() {
		Context context = this.getContext();
		String[] errors = this.check();
		if (ArrayUtils.isEmpty(errors)) {
			String property = this.getValueProperty();
			if (context != null && context.getData() != null && StringUtils.isNoneEmpty(property)) {
				Logger.debug("Write back value to: " + property);
				BaseUtils.setProperty(context.getData(), property, this.getValue());
			}
		} else {
			this.showErrors(errors);
		}
	}

}
