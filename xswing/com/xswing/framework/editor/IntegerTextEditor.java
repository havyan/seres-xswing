package com.xswing.framework.editor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.text.AttributeSet;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang3.StringUtils;

@Component(name = "integerText")
public class IntegerTextEditor extends AbstractEditor<JTextComponent, Long> {

	@Override
	public void setValue(Object value) {
		if (value != null) {
			component.setText(value + "");
		}
	}

	@Override
	public void setComponent(JComponent component) {
		super.setComponent(component);
		JTextComponent textComponent = (JTextComponent) component;
		textComponent.setDocument(new IntegerDocument());
		this.component.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				writeback();
			}
		});
	}

	@Override
	public Long getValue() {
		String text = component.getText();
		if (StringUtils.isNoneEmpty(text)) {
			return Long.valueOf(text);
		} else {
			return 0L;
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		component.setEditable(enabled);
		component.setEnabled(enabled);
	}

	@Override
	public void reset() {
		component.setText("");
	}

	public static class IntegerDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		public IntegerDocument() {
			super();
		}

		public void insertString(int offset, String str, AttributeSet attr) throws javax.swing.text.BadLocationException {
			if (str == null) {
				return;
			}
			char[] upper = str.toCharArray();
			int length = 0;
			for (int i = 0; i < upper.length; i++) {
				if (upper[i] >= '0' && upper[i] <= '9') {
					upper[length++] = upper[i];
				}
			}
			super.insertString(offset, new String(upper, 0, length), attr);
		}

	}

}
