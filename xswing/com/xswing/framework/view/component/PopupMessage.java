package com.xswing.framework.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

public class PopupMessage {

	public static Popup show(Component from, String content) {
		return show(from, null, content, Color.WHITE, Color.BLACK, Color.GRAY, from.getWidth() / 2, from.getHeight() / 2);
	}

	public static Popup show(Component from, String title, String content) {
		return show(from, title, content, Color.WHITE, Color.BLACK, Color.GRAY, from.getWidth() / 2, from.getHeight() / 2);
	}

	public static Popup show(Component from, String content, Color background, Color foreground, Color border) {
		return show(from, null, content, background, foreground, border, from.getWidth(), from.getHeight() / 2);
	}

	public static Popup show(Component from, String title, String content, Color background, Color foreground, Color border) {
		return show(from, title, content, background, foreground, border, from.getWidth(), from.getHeight() / 2);
	}

	public static Popup show(Component from, String title, String content, Color background, Color foreground, Color border, int x, int y) {
		PopupPanel contents = new PopupPanel(background, border);
		contents.setBackground(background);
		contents.setForeground(foreground);
		contents.setLayout(new BorderLayout());
		JLabel closeLabel = new JLabel(new CloseIcon());
		closeLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
		if (title != null) {
			JPanel header = new JPanel(new BorderLayout());
			header.setBackground(background);
			header.setOpaque(false);
			JLabel titleLabel = new JLabel();
			titleLabel.setOpaque(false);
			titleLabel.setBackground(background);
			titleLabel.setForeground(foreground);
			titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
			titleLabel.setText(title);
			titleLabel.setPreferredSize(new Dimension(150, 25));
			titleLabel.setBackground(background);
			header.add(titleLabel);
			header.add(closeLabel, BorderLayout.EAST);
			contents.add(header, BorderLayout.NORTH);
		}
		JLabel contentLabel = new JLabel();
		contentLabel.setBorder(new EmptyBorder(10, 20, 10, 10));
		contentLabel.setText(content);
		contentLabel.setOpaque(false);
		contentLabel.setBackground(background);
		contentLabel.setForeground(foreground);
		contents.setOpaque(false);
		contents.add(contentLabel);
		PopupFactory popupFactory = PopupFactory.getSharedInstance();
		if (from != null) {
			Point location = from.getLocationOnScreen();
			x = location.x + x;
			y = location.y + y - contents.getPreferredSize().height / 2;
		}
		Popup popup = popupFactory.getPopup(from, contents, x, y);
		popup.show();
		JWindow root = (JWindow) SwingUtilities.getRoot(contents);
		AWTUtilities.setWindowOpaque(root, false);
		closeLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				popup.hide();
			}

			public void mouseEntered(MouseEvent e) {
				closeLabel.setIcon(new CloseIcon(Color.BLUE));
			}

			public void mouseExited(MouseEvent e) {
				closeLabel.setIcon(new CloseIcon());
			}
		});
		return popup;
	}

	private static class PopupPanel extends JPanel {
		Color background = Color.WHITE;
		Color border = Color.GRAY;

		public PopupPanel() {
			this.setOpaque(false);
		}

		public PopupPanel(Color background, Color border) {
			this();
			this.background = background;
			this.border = border;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Color oldColor = g.getColor();
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(background);
			int width = this.getWidth();
			int height = this.getHeight();
			int[] xpoints = new int[] { 0, 12, 12, width - 1, width - 1, 12, 12 };
			int[] ypoints = new int[] { height / 2, height / 2 - 5, 0, 0, height - 1, height - 1, height / 2 + 5 };
			Polygon p = new Polygon(xpoints, ypoints, 7);
			g2.fillPolygon(p);
			g2.setColor(border);
			g2.drawPolygon(p);
			g2.setColor(oldColor);
		}

	}

}
