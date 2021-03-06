/**
 * 
 */
package com.xswing.framework.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang3.ArrayUtils;

import com.xswing.framework.editor.Editor;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.model.AppModel;

/**
 * @author yhw
 * 
 */
public class XPanel extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private Context context;

	public XPanel() {
		this.setLayout(new BorderLayout());
	}

	public Object getBean(String id) {
		return context.getBean(id);
	}

	public JComponent getComponent(String id) {
		return (JComponent) getBean(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String id, Class<T> cls) {
		return (T) getBean(id);
	}

	public Editor<? extends JComponent, ?> getEditor(String id) {
		return context.getEditor(id);
	}
	
	public Map<String, Editor<? extends JComponent, ?>> getEditors() {
		return context.getEditors();
	}

	public Object getValue(String id) {
		return getEditor(id).getValue();
	}

	public void setValue(String id, Object value) {
		getEditor(id).setValue(value);
	}

	@SuppressWarnings("unchecked")
	public <T extends JComponent> T getComponent(String id, Class<T> cls) {
		return (T) getComponent(id);
	}

	public Map<String, Object> getBeans() {
		return context.getBeans();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public AppModel<?> getModel() {
		return context.getModel();
	}

	@Override
	public void setModel(AppModel<?> model) {
		throw new IllegalArgumentException("setModel is not supported for XPanel");
	}

	@Override
	public void handleEvent(AppEvent event) {

	}

	public String[] check() {
		List<String> errors = new ArrayList<String>();
		for (Editor<? extends JComponent, ?> editor : this.context.getEditors().values()) {
			String[] editorErrors = editor.validate();
			if (ArrayUtils.isNotEmpty(editorErrors)) {
				errors.addAll(Arrays.asList(editorErrors));
				editor.highlight();
			}
		}
		return errors.toArray(new String[0]);
	}

	public void destroy() {
		this.context.unbind();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.destroy();
	}

	public void setWritebackable(boolean writebackable) {
		context.setWritebackable(writebackable);
	}

	public boolean isWritebackable() {
		return context.isWritebackable();
	}

	@Override
	public void flush() {
		boolean writebackable = this.isWritebackable();
		this.setWritebackable(true);
		for (Editor<? extends JComponent, ?> editor : this.context.getEditors().values()) {
			editor.writeback();
		}
		this.setWritebackable(writebackable);
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

}
