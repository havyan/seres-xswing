package com.xswing.framework.editor;

import javax.swing.JComponent;

public interface Editor<T extends JComponent> {
	
	public void setValue(T value);
	
	public T getValue();
	
	public void setEditable(boolean editable);
	
	public void reset();
	
	public T getComponent();
	
}
