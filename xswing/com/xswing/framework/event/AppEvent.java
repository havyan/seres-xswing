/**
 * 
 */
package com.xswing.framework.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yhw
 *
 */
public class AppEvent {

	private String name;

	private Map<String, Object> params = new HashMap<String, Object>();

	public AppEvent() {
		super();
	}

	public AppEvent(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParam(String name, Object value) {
		params.put(name, value);
	}

	public Object getParam(String name) {
		return params.get(name);
	}

}
