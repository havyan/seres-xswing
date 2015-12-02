package com.xswing.framework.editor;

import java.util.List;

import javax.swing.JComponent;

import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;

public interface Editor<T extends JComponent, V> extends AppListener {

	public void init();

	public void setValue(Object value);

	public V getValue();

	public void setEnabled(boolean enabled);

	public void reset();
	
	public void setValueProperty(String property);
	
	public String getValueProperty();

	public T getComponent();

	public void setComponent(JComponent component);

	public void setContext(Context context);

	public Context getContext();

	public String validate();

	public void setValidators(List<Validator> validators);

	public List<Validator> getValidators();

	public void registerAction(Action<?, ?, ?> action);

}
