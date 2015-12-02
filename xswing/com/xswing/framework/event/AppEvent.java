/**
 * 
 */
package com.xswing.framework.event;

/**
 * @author yhw
 *
 */
public class AppEvent {
	
	private String name;

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

}
