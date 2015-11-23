package com.xswing.framework.processor;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;

public abstract class AbstractXProcessor implements XProcessor {
	
	protected XPanel xpanel;
	
	protected Context context;

	public AbstractXProcessor(XPanel xpanel) {
		super();
		this.xpanel = xpanel;
	}

	public Context getContext() {
		return context;
	}
	
	public XPanel getXPanel() {
		return xpanel;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
