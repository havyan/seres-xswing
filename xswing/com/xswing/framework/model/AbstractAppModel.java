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
public abstract class AbstractAppModel {
	
	private List<AppListener> appListeners = new ArrayList<AppListener>();

	public void addAppListener(AppListener l){
		appListeners.add(l);
	}
	
	public void removeAppListener(AppListener l){
		appListeners.remove(l);
	}
	
	public void fireAppEvent(AppEvent e){
		for(AppListener l : appListeners){
			l.handleEvent(e);
		}
	}
	
	public <T> T asBean(T target) {
		return DynamicObjectFactory2.createDynamicObject(target);
	}
	
}
