/**
 * 
 */
package com.xswing.framework.bean;

/**
 * @author HWYan
 * 
 */
public class DefaultBean implements Bean {

	private String ID;

	private Object source;

	private int status = NEW;

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void setID(String id) {
		// 必须马上立刻变更，持久层的ID TODO
		this.ID = id;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public void setProperty(Object bean, String propertyName, Object value) {
		if (source != null) {
			BeanUtils.setProperty(source, propertyName, value);
		}
	}

	@Override
	public Object getProperty(String propertyName) {
		if (source != null) {
			return BeanUtils.getProperty(source, propertyName);
		}
		return null;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

}
