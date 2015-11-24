package com.xswing.framework.editor;

import javax.swing.JComponent;

public interface Editor<T extends JComponent, V> {

	public void setValue(V value);

	public V getValue();

	public void setEnabled(boolean enabled);

	public void reset();

	public T getComponent();

	public void setComponent(JComponent component);

}
