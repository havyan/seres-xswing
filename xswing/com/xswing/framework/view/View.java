/**
 * 
 */
package com.xswing.framework.view;

import java.util.Map;

import javax.swing.JComponent;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.event.AppListener;
import com.xswing.framework.model.AppModel;

/**
 * @author HWYan
 *
 */
public interface View extends AppListener {

	public Object getBean(String id);

	public JComponent getComponent();

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

	public String[] check();

	public void setWritebackable(boolean writebackable);

	public boolean isWritebackable();

	public void flush();

}
