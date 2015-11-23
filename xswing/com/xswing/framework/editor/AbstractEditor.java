/**
 * 
 */
package com.xswing.framework.editor;

import javax.swing.JComponent;

/**
 * @author think
 *
 */
public abstract class AbstractEditor<T extends JComponent> implements Editor<T>{
	
	protected T component;

	public AbstractEditor(T component) {
		this.component = component;
	}
	
	public T getComponent() {
		return component;
	}
	
}
