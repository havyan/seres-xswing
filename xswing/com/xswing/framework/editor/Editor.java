package com.xswing.framework.editor;

import java.util.List;

import javax.swing.JComponent;

import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;

public interface Editor<T extends JComponent, V> {

	public void setValue(Object value);

	public V getValue();

	public void setEnabled(boolean enabled);

	public void reset();

	public T getComponent();

	public void setComponent(JComponent component);

	public void setContext(Context context);

	public Context getContext();
	
	public String validate();

	public void setBind(String property);

	public void setValidators(List<Validator> validators);

	public List<Validator> getValidators();

}
