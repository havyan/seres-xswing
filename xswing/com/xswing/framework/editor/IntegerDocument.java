package com.xswing.framework.editor;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class IntegerDocument extends PlainDocument {

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
