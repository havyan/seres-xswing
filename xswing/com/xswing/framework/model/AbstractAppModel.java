/**
 * 
 */
package com.xswing.framework.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.events.PropertyChangeListenerProxy;
import com.framework.proxy.DynamicObjectFactory2;
import com.framework.proxy.interfaces.Bean;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public abstract class AbstractAppModel<T> implements AppModel<T> {

	protected T data;

	private List<AppListener> appListeners = new ArrayList<AppListener>();

	private Map<String, List<AppListener>> appListenersMap = new HashMap<String, List<AppListener>>();

	public AbstractAppModel() {

	}

	public AbstractAppModel(T data) {
		this.setData(data);
	}

	public void addAppListener(AppListener l) {
		appListeners.add(l);
	}

	public void removeAppListener(AppListener l) {
		appListeners.remove(l);
	}

	public void addAppListener(String eventName, AppListener l) {
		List<AppListener> listeners = appListenersMap.get(eventName);
		if (listeners == null) {
			listeners = new ArrayList<AppListener>();
			appListenersMap.put(eventName, listeners);
		}
		listeners.add(l);
	}

	public void removeAppListener(String eventName, AppListener l) {
		List<AppListener> listeners = appListenersMap.get(eventName);
		if (listeners != null) {
			listeners.remove(l);
		}
	}

	public void bind(String dataPath, PropertyChangeListener l) {
		((Bean) data).addPropertyChangeListener(dataPath, new PropertyChangeListenerProxy(this, l));
	}

	protected void unbindAll() {
		if (data != null && data instanceof Bean) {
			((Bean) data).removeAllPropertyChangeListenerFrom(this);
		}
	}

	protected void rebindTo(Bean bean) {
		if (data != null && data instanceof Bean && bean != null) {
			Bean current = (Bean) data;
			PropertyChangeListenerProxy[] listeners = current.getPropertyChangeListenersFrom(this);
			for (PropertyChangeListenerProxy listener : listeners) {
				bean.addPropertyChangeListener(listener);
			}
			Map<String, PropertyChangeListenerProxy[]> map = current.getPropertyChangeListenersMapFrom(this);
			for (Map.Entry<String, PropertyChangeListenerProxy[]> entry : map.entrySet()) {
				String propertyName = entry.getKey();
				listeners = entry.getValue();
				for (PropertyChangeListenerProxy listener : listeners) {
					bean.addPropertyChangeListener(propertyName, listener);
				}
			}
		}
	}

	public void fireAppEvent(AppEvent e) {
		for (AppListener l : appListeners) {
			l.handleEvent(e);
		}
		List<AppListener> listeners = appListenersMap.get(e.getName());
		if (listeners != null) {
			for (AppListener l : listeners) {
				l.handleEvent(e);
			}
		}
	}

	public <V> V asDynamicObject(V target) {
		return DynamicObjectFactory2.createDynamicObject(target);
	}

	public T getData() {
		return data;
	}

	public T setData(T data) {
		if (data != null) {
			data = asDynamicObject(data);
			if (data instanceof Bean) {
				rebindTo((Bean) data);
			}
		}
		unbindAll();
		this.data = data;
		fireAppEvent(new AppEvent(DATA_CHANGED));
		return data;
	}

	public void setDataProperty(String property, Object value) {
		if (data != null && data instanceof Bean) {
			((Bean) data).setProperty(property, value);
		}
	}

	public Object getDataProperty(String property) {
		if (data != null && data instanceof Bean) {
			return ((Bean) data).getProperty(property);
		} else {
			return null;
		}
	}

}
