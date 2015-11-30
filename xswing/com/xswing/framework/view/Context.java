/**
 * 
 */
package com.xswing.framework.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.jdom2.Document;

import com.framework.common.BaseUtils;
import com.xswing.framework.editor.Editor;
import com.xswing.framework.model.AppModel;

/**
 * @author HWYan
 * 
 */
public class Context {

	private Map<String, Object> beans = new HashMap<String, Object>();

	private Map<Object, Map<String, String>> beanRefs = new HashMap<Object, Map<String, String>>();

	private Map<String, Editor<? extends JComponent, ?>> editors = new HashMap<String, Editor<? extends JComponent, ?>>();

	private Document doc;

	private String path;

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
			XPanel xPanel = (XPanel) beans.get(strs[0]);
			return xPanel.getBean(strs[1]);
		}
		return beans.get(id);
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

	public void setModel(AppModel<?> model) {
		this.model = model;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

}
