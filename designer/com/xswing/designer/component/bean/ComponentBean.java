/**
 * 
 */
package com.xswing.designer.component.bean;

import java.util.List;

/**
 * @author HWYan
 * 
 */
public class ComponentBean {

	private String name;

	private String componentClass;

	private String handlerClass;

	private List<CustomProperty> customProperties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponentClass() {
		return componentClass;
	}

	public void setComponentClass(String componentClass) {
		this.componentClass = componentClass;
	}

	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}

	public List<CustomProperty> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(List<CustomProperty> customProperties) {
		this.customProperties = customProperties;
	}

	class CustomProperty {

		private String name;

		private String sourceProperty;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSourceProperty() {
			return sourceProperty;
		}

		public void setSourceProperty(String sourceProperty) {
			this.sourceProperty = sourceProperty;
		}

	}

}
