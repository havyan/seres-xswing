/**
 * 
 */
package com.xswing.framework.bean;

import java.io.Serializable;

import net.sf.cglib.proxy.Callback;

/**
 * @author HWYan
 * 
 */
public interface Bean extends Serializable, Cloneable, Callback {

	public String getID();

	public void setID(String id);

	public int getStatus();

	public void setStatus(int status);

	public void setProperty(Object bean, String propertyName, Object value);

	public Object getProperty(String propertyName);
	
	public Object getSource();
	
	public void setSource(Object source);

	public static final int NEW = 0;

	public static final int UPDATED = 1;

	public static final int DELETED = 2;

}
