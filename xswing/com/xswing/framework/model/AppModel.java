/**
 * 
 */
package com.xswing.framework.model;

import java.beans.PropertyChangeListener;

import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public interface AppModel<T> {

	public void addAppListener(AppListener l);

	public void removeAppListener(AppListener l);
	
	public void addAppListener(String eventName, AppListener l);

	public void removeAppListener(String eventName, AppListener l);

	public void fireAppEvent(AppEvent e);

	public void bind(String dataPath, PropertyChangeListener l);
	
	public void bind(PropertyChangeListener l);

	public void setDataProperty(String property, Object value);

	public Object getDataProperty(String property);

	public T getData();

	public T setData(Object data);
	
	public final String DATA_CHANGED = "DATA_CHANGED";
	
	public final String OLD_DATA = "OLD_DATA";
	
	public final String NEW_DATA = "NEW_DATA";

}
