/**
 * 
 */
package com.xswing.framework.view;

import java.util.Map;

import javax.swing.JComponent;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.model.AppModel;

/**
 * @author HWYan
 *
 */
public interface View {

	public Object getBean(String id);

	public JComponent getComponent(String id);

	public <T> T getBean(String id, Class<T> cls);

	public Editor<? extends JComponent, ?> getEditor(String id);

	public Object getValue(String id);

	public void setValue(String id, Object value);

	public <T extends JComponent> T getComponent(String id, Class<T> cls);

	public Map<String, Object> getBeans();
	
	public AppModel<?> getModel();
	
	public void setModel(AppModel<?> model);

	public Context getContext();

	public void setContext(Context context);

}