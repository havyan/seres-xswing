package com.xswing.framework.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class CloseIcon implements Icon {

	private Color color = Color.BLACK;

	public CloseIcon() {
		super();
	}

	public CloseIcon(Color color) {
		super();
		this.color = color;
	}

	public synchronized void paintIcon(Component c, Graphics g, int x1, int y1) {
		int x = x1 + 5, y = y1;
		Color oldColor = g.getColor();
		g.setColor(color);
		// draw X
		// left top
		g.drawRect(x + 4, y + 4, 1, 1);
		g.drawRect(x + 5, y + 5, 1, 1);
		g.drawRect(x + 5, y + 9, 1, 1);
		g.drawRect(x + 4, y + 10, 1, 1);
		// center
		g.drawRect(x + 6, y + 6, 1, 1);
		g.drawRect(x + 8, y + 6, 1, 1);
		g.drawRect(x + 6, y + 8, 1, 1);
		g.drawRect(x + 8, y + 8, 1, 1);
		// right top
		g.drawRect(x + 10, y + 4, 1, 1);
		g.drawRect(x + 9, y + 5, 1, 1);
		// right bottom
		g.drawRect(x + 9, y + 9, 1, 1);
		g.drawRect(x + 10, y + 10, 1, 1);
		g.setColor(oldColor);
	}

	@Override
	public int getIconWidth() {
		return 18;
	}

	@Override
	public int getIconHeight() {
		return 16;
	}
};