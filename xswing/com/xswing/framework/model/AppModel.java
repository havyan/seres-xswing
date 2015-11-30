/**
 * 
 */
package com.xswing.framework.model;

import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public interface AppModel<T> {

	public void addAppListener(AppListener l);

	public void removeAppListener(AppListener l);

	public void fireAppEvent(AppEvent e);

	public T getData();
	
	public void setData(T data);

}
