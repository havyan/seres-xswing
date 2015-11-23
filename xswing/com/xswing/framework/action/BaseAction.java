/**
 * 
 */
package com.xswing.framework.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.xswing.framework.event.AppEvent;
import com.xswing.framework.event.AppListener;

/**
 * @author yhw
 *
 */
public class BaseAction extends AbstractAction implements AppListener{
	
	@Override
	public void handleEvent(AppEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
