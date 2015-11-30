/**
 * 
 */
package com.xswing.framework.model;

import java.util.ArrayList;
import java.util.List;

import com.framework.proxy.DynamicObjectFactory2;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public abstract class AbstractAppModel<T> implements AppModel<T> {

	protected T data;

	private List<AppListener> appListeners = new ArrayList<AppListener>();

	public AbstractAppModel(T data) {
		this.data = asBean(data);
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

	public <V> V asBean(V target) {
		return DynamicObjectFactory2.createDynamicObject(target);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = asBean(data);
	}

}
