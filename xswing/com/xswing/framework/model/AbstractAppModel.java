/**
 * 
 */
package com.xswing.framework.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.proxy.DynamicObjectFactory2;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public abstract class AbstractAppModel<T> implements AppModel<T> {

	protected static final String MAIN_DATA_NAME = "data";

	protected static final String UI_DATA_NAME = "ui";

	protected final Map<String, Object> data = asDynamicObject(new HashMap<String, Object>());

	private List<AppListener> appListeners = new ArrayList<AppListener>();

	public AbstractAppModel(T data) {
		this.setMainData(data);
	}

	public void addAppListener(AppListener l) {
		appListeners.add(l);
	}

	public void removeAppListener(AppListener l) {
		appListeners.remove(l);
	}

	public void fireAppEvent(AppEvent e) {
		for (AppListener l : appListeners) {
			l.handleEvent(e);
		}
	}

	public <V> V asDynamicObject(V target) {
		return DynamicObjectFactory2.createDynamicObject(target);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Object getData(String name) {
		return data.get(name);
	}

	@SuppressWarnings("unchecked")
	public <V> V getData(String name, Class<V> cls) {
		return (V) data.get(name);
	}

	public <V> V setData(String name, V data) {
		V dynamicObject = asDynamicObject(data);
		this.data.put(name, dynamicObject);
		return dynamicObject;
	}

	public void setMainData(T data) {
		this.setData(MAIN_DATA_NAME, data);
	}

	@SuppressWarnings("unchecked")
	public T getMainData() {
		return (T) this.getData(MAIN_DATA_NAME);
	}

}
