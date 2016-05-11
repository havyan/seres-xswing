package com.xswing.framework.editor;

@Component(name = "untrimText", types = {})
public class UntrimTextEditor extends TextEditor {

	@Override
	public String getValue() {
		return component.getText();
	}

}
