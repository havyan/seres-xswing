package com.xswing.framework.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.EmptyBorder;

public class PopupMessage {

	public static void show(Component from, String title, String content) {
		show(from, title, content, Color.WHITE, Color.BLACK, Color.GRAY, from.getWidth() / 2, from.getHeight() / 2);
	}

	public static void show(Component from, String title, String content, Color background, Color foreground, Color border) {
		show(from, title, content, background, foreground, border, from.getWidth() / 2, from.getHeight() / 2);
	}

	public static void show(Component from, String title, String content, Color background, Color foreground, Color border, int x, int y) {
		JPanel contents = new JPanel();
		contents.setBackground(background);
		contents.setForeground(foreground);
		contents.setBorder(BorderFactory.createLineBorder(border));
		contents.setLayout(new BorderLayout());
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(background);
		header.setOpaque(false);
		JLabel titleLabel = new JLabel();
		titleLabel.setOpaque(false);
		titleLabel.setBackground(background);
		titleLabel.setForeground(foreground);
		titleLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
		titleLabel.setText(title);
		titleLabel.setPreferredSize(new Dimension(200, 25));
		JLabel closeLabel = new JLabel(new CloseIcon());
		closeLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
		titleLabel.setBackground(background);
		header.add(titleLabel);
		header.add(closeLabel, BorderLayout.EAST);
		JLabel contentLabel = new JLabel();
		contentLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contents.add(header, BorderLayout.NORTH);
		contentLabel.setText(content);
		contentLabel.setOpaque(false);
		contentLabel.setBackground(background);
		contentLabel.setForeground(foreground);
		contents.setOpaque(true);
		contents.add(contentLabel);
		PopupFactory popupFactory = PopupFactory.getSharedInstance();
		if (from != null) {
			Point location = from.getLocationOnScreen();
			x = location.x + x;
			y = location.y + y;
		}
		Popup popup = popupFactory.getPopup(from, contents, x, y);
		popup.show();
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
	}

}
