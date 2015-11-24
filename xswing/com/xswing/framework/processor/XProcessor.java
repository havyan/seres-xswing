package com.xswing.framework.processor;

import com.xswing.framework.view.XPanel;

/**
 * 负责增加坚听 处理Model事件，界面全部由View处理，但是View不引用Model
 * 
 * @author yhw
 * 
 */
public interface XProcessor {

	public void process(XPanel xpanel);

}
