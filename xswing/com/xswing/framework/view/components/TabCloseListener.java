package com.xswing.framework.view.components;

import java.awt.Component;
import java.util.EventListener;

import javax.swing.JTabbedPane;

/**
 * Listener on tab closing. This class is part of officially supported API.
 * 
 */
public interface TabCloseListener extends EventListener {
	/**
	 * Called when a tab is about to be closed.
	 * 
	 * @param tabbedPane
	 *            Tabbed pane.
	 * @param tabComponent
	 *            Tab component to be closed.
	 */
	default boolean tabClosing(JTabbedPane tabbedPane, Component tabComponent) {
		return true;
	}

	/**
	 * Called when a tab is closed.
	 * 
	 * @param tabbedPane
	 *            Tabbed pane.
	 * @param tabComponent
	 *            Tab component closed.
	 */
	public void tabClosed(JTabbedPane tabbedPane, Component tabComponent);
}
