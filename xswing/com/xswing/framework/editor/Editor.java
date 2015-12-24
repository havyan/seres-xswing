package com.xswing.framework.editor;

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

	public void setValidators(List<Validator<?>> validators);

	public List<Validator<?>> getValidators();

	public void setAction(Action<?, ?, ?> action);

	public void showErrors();

	public void hideErrors();

	public void highlight();

	default void setEnabled(boolean enabled) {
		T component = this.getComponent();
		if (component != null) {
			component.setEnabled(enabled);
		}
	}

	public String[] validate();

	default void writeback() {
		if (this.getContext().isWritebackable()) {
			Context context = this.getContext();
			String[] errors = this.validate();
			if (ArrayUtils.isEmpty(errors)) {
				String property = this.getValueProperty();
				if (context != null && context.getData() != null && StringUtils.isNoneEmpty(property)) {
					Logger.debug("Write back value to: " + property);
					BaseUtils.setProperty(context.getData(), property, this.getValue());
				}
			}
		}
	}

}
