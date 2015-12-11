/**
 * 
 */
package com.xswing.framework.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.jdom2.Document;

import com.framework.common.BaseUtils;
import com.framework.proxy.DynamicObjectFactory2;
import com.framework.proxy.interfaces.Bean;
import com.xswing.framework.editor.Editor;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.model.AppModel;

/**
 * @author HWYan
 * 
 */
public class Context implements AppListener {

	private Map<String, Object> beans = new HashMap<String, Object>();

	private Map<Object, Map<String, String>> beanRefs = new HashMap<Object, Map<String, String>>();

	private Map<String, Editor<? extends JComponent, ?>> editors = new HashMap<String, Editor<? extends JComponent, ?>>();

	private Document doc;

	private String path;

	private Object tempData;

	private AppModel<?> model;

	private View view;

	public Document getDoc() {
		return doc;
	}

	public Map<String, Object> getBeans() {
		return beans;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public void setBean(String id, Object bean) {
		if (!beans.containsKey(id)) {
			this.beans.put(id, bean);
		} else {
			throw new IllegalArgumentException("Duplicated bean for id: " + id);
		}
	}

	public void unsetBean(String id) {
		this.beans.remove(id);
	}

	public Object getBean(String id) {
		if (id.contains(".")) {
			String[] strs = id.split("\\.", 2);
			Object bean = beans.get(strs[0]);
			if (bean != null) {
				if (bean instanceof XPanel) {
					return ((XPanel) bean).getBean(strs[1]);
				} else {
					return BaseUtils.getProperty(bean, strs[1]);
				}
			} else {
				return null;
			}
		}
		return beans.get(id);
	}

	public Map<String, Editor<? extends JComponent, ?>> getEditors() {
		return editors;
	}

	public boolean setEditor(String id, Editor<? extends JComponent, ?> editor) {
		if (!editors.containsKey(id)) {
			this.editors.put(id, editor);
			return true;
		} else {
			return false;
		}
	}

	public Editor<? extends JComponent, ?> getEditor(String id) {
		return editors.get(id);
	}

	public void unsetEditor(String id) {
		this.editors.remove(id);
	}

	public void addBeanRef(Object bean, String property, String ref) {
		Map<String, String> refs = beanRefs.get(bean);
		if (refs == null) {
			refs = new HashMap<String, String>();
			beanRefs.put(bean, refs);
		}
		refs.put(property, ref);
	}

	public void processRefs() {
		for (Map.Entry<Object, Map<String, String>> entry : beanRefs.entrySet()) {
			Object bean = entry.getKey();
			Map<String, String> refs = entry.getValue();
			if (refs != null) {
				for (Map.Entry<String, String> refEntry : refs.entrySet()) {
					BaseUtils.setProperty(bean, refEntry.getKey(), beans.get(refEntry.getValue()));
				}
			}
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AppModel<?> getModel() {
		return model;
	}

	public Object getData() {
		if (model != null && model.getData() != null) {
			return model.getData();
		} else {
			return tempData;
		}
	}

	public void setModel(AppModel<?> model) {
		Object oldData = getData();
		if (this.model != null) {
			this.model.removeAppListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addAppListener(this);
		}
		if (this.model == null || this.model.getData() == null) {
			tempData = DynamicObjectFactory2.createDynamicObject(new HashMap<String, Object>());
		}
		Object newData = getData();
		if (oldData != null && newData != null) {
			for (Object bean : beans.values()) {
				BaseUtils.takeBinds(oldData, newData, bean);
			}
		}
	}

	public void unbind() {
		Object data = getData();
		if (data != null && data instanceof Bean) {
			for (Object bean : beans.values()) {
				((Bean) data).removeAllPropertyChangeListenerFrom(bean);
			}
		}
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void handleEvent(AppEvent event) {
		if (AppModel.DATA_CHANGED.equals(event.getName())) {
			Object oldData = event.getParam(AppModel.OLD_DATA);
			Object newData = event.getParam(AppModel.NEW_DATA);
			if (oldData != null || newData != null) {
				if (oldData == null) {
					oldData = tempData;
				}
				if (newData == null) {
					tempData = DynamicObjectFactory2.createDynamicObject(new HashMap<String, Object>());
					newData = tempData;
				} else {
					tempData = null;
				}
			}
			for (Object bean : beans.values()) {
				BaseUtils.takeBinds(oldData, newData, bean);
			}
		}
	}

}
