/**
 * 
 */
package com.xswing.framework.model;

import java.util.Map;

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

	public Map<String, Object> getData();

	public Object getData(String name);

	public <V> V setData(String name, V data);

	public void setMainData(T data);

	public T getMainData();

}
