/**
 * 
 */
package com.xswing.framework.editor;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.Popup;
import javax.swing.border.Border;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.framework.common.BaseUtils;
import com.xswing.framework.action.Action;
import com.xswing.framework.event.AppEvent;
import com.xswing.framework.validator.Validator;
import com.xswing.framework.view.Context;
import com.xswing.framework.view.component.PopupMessage;

/**
 * @author think
 *
 */
public abstract class AbstractEditor<T extends JComponent, V> implements Editor<T, V> {

	protected T component;

	protected List<Validator<?>> validators = new ArrayList<Validator<?>>();

	protected Context context;

	protected String valueProperty;

	private Border border;

	protected Popup popup;

	public void init() {

	}

	public T getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(JComponent component) {
		this.component = (T) component;
		border = component.getBorder();
		component.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				showErrors();
			}

			public void mouseExited(MouseEvent e) {
				hideErrors();
			}
		});
		component.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				String[] errors = check();
				if (errors != null && errors.length > 0) {
					highlight();
				} else {
					if (border != null) {
						component.setBorder(border);
					}
				}
			}

		});
	}

	public List<Validator<?>> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator<?>> validators) {
		this.validators = validators;
	}

	public Context getContext() {
		return context;
	}

	public void handleEvent(AppEvent event) {
	}

	public void setContext(Context context) {
		if (this.context != null && this.context.getModel() != null) {
			this.context.getModel().removeAppListener(this);
		}
		this.context = context;
		if (context.getModel() != null) {
			context.getModel().addAppListener(this);
		}
	}

	public void setValueProperty(String property) {
		this.valueProperty = property;
	}

	public String getValueProperty() {
		return valueProperty;
	}

	protected Object getDataProperty(String property) {
		return BaseUtils.getProperty(context.getData(), property);
	}

	public void reset() {

	}

	public void registerAction(Action<?, ?, ?> action) {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] check() {
		List<String> errors = new ArrayList<String>();
		List<Validator<?>> validators = this.getValidators();
		if (validators != null && validators.size() > 0) {
			V value = this.getValue();
			for (Validator validator : validators) {
				String result = validator.validate(value);
				if (StringUtils.isNotEmpty(result)) {
					errors.add(result);
				}
			}
		}
		return errors.toArray(new String[0]);
	}

	public void showErrors() {
		hideErrors();
		String[] errors = this.check();
		if (ArrayUtils.isNotEmpty(errors)) {
			StringBuilder sb = new StringBuilder();
			if (errors.length == 1) {
				sb.append(errors[0]);
			} else {
				sb.append("<html><body>");
				for (int i = 0; i < errors.length; i++) {
					sb.append(i + 1).append(". ").append(errors[i]).append("<br>");
				}
				sb.append("</body></html>");
			}
			popup = PopupMessage.show(getComponent(), sb.toString(), Color.WHITE, Color.RED, Color.RED);
		}
	}

	public void hideErrors() {
		if (popup != null) {
			popup.hide();
		}
	}

	public void highlight() {
		if (component != null) {
			component.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
	}
}
