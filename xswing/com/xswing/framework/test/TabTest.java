package com.xswing.framework.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


public class TabTest {

	public static void main(String[] args) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
			UIManager.getInsets("TabbedPane.tabInsets").right = 5;
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Cobra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		JTabbedPane tabPanel = new JTabbedPane();
		JPanel tab1 = new JPanel();
		tab1.setPreferredSize(new Dimension(800, 500));
		tabPanel.add("Tablet 1", tab1);
		JPanel tab2 = new JPanel();
		tab2.setPreferredSize(new Dimension(800, 500));
		tabPanel.add("Tablet 2", tab2);
		
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.add(new JLabel("Tablet1   "), BorderLayout.CENTER);
		JLabel close = new JLabel("x");
		close.setHorizontalAlignment(SwingConstants.RIGHT);
		close.setHorizontalTextPosition(SwingConstants.RIGHT);
		close.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				close.setForeground(Color.BLUE);
			}
			public void mouseExited(MouseEvent e) {
				close.setForeground(null);
			}
		});
		header.add(close, BorderLayout.EAST);
		header.setOpaque(false);
		tabPanel.setTabComponentAt(0, header);
		frame.add(tabPanel);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
