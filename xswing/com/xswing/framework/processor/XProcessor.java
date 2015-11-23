package com.xswing.framework.processor;

import com.xswing.framework.view.Context;
import com.xswing.framework.view.XPanel;

/**
 * 负责增加坚听 处理Model事件，界面全部由View处理，但是View不引用Model
 * 
 * @author yhw
 * 
 */
public interface XProcessor {

	public void setContext(Context context);

	public Context getContext();

	public XPanel getXPanel();

	public void process();

}
