package com.xswing.framework.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 */
public class JCloseableTabbedPane extends JTabbedPane implements Serializable {

	private static final long serialVersionUID = 1L;
	private JPopupMenu popup = null;

	static {
		Insets tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
		if (tabInsets != null) {
			tabInsets.right = 5;
		}
	}

	public void setPopup(JPopupMenu popup) {
		this.popup = popup;
	}

	public void addTab(String title, Icon icon, Component component, String tip, boolean closable) {
		setTabClosable(component, closable);
		super.addTab(title, icon, component, tip);
	}

	public void addTab(String title, Icon icon, Component component, boolean closable) {
		setTabClosable(component, closable);
		super.addTab(title, icon, component);
	}

	public void addTab(String title, Component component, boolean closable) {
		setTabClosable(component, closable);
		super.addTab(title, component);
	}

	private void setTabClosable(Component component, boolean closable) {
		if (component instanceof JComponent) {
			((JComponent) component).putClientProperty("tabClosable", closable);
		}
	}

	public void insertTab(String title, Icon icon, Component component, String tip, int index, boolean closable) {
		setTabClosable(component, closable);
		this.insertTab(title, icon, component, tip, index);
	}

	public void insertTab(String title, Icon icon, Component component, String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		boolean closable = true;
		if (component instanceof JComponent) {
			Object tabClosable = ((JComponent) component).getClientProperty("tabClosable");
			if (tabClosable != null) {
				closable = (Boolean) tabClosable;
			}
		}
		if (closable) {
			this.setTabComponentAt(index, new CloseTab(title, component));
		}
	}

	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		Component tab = this.getTabComponentAt(index);
		if (tab instanceof CloseTab) {
			((CloseTab) tab).component = component;
		}
	}

	class CloseTab extends JPanel {

		String title;

		Component component;

		JLabel closeLabel;

		JLabel titleLabel;

		public CloseTab(String title, Component component) {
			this.title = title;
			this.component = component;
			initUI();
			initEvents();
		}

		private void initUI() {
			this.setLayout(new BorderLayout());
			titleLabel = new JLabel(title);
			this.add(titleLabel, BorderLayout.CENTER);
			closeLabel = new JLabel(new CloseIcon());
			this.add(closeLabel, BorderLayout.EAST);
			this.setOpaque(false);
		}

		private void initEvents() {
			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e) && popup != null) {
						popup.show(e.getComponent(), e.getX(), e.getY());
					} else {
						JCloseableTabbedPane.this.setSelectedComponent(component);
					}
				}
			});
			closeLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					JCloseableTabbedPane.this.remove(component);
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

	class CloseIcon implements Icon {

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

	/**
	 * 测试
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
		} catch (Exception e) {
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JCloseableTabbedPane Demo");
		frame.getContentPane().setLayout(new BorderLayout());
		final JCloseableTabbedPane tab = new JCloseableTabbedPane();
		tab.addTab("TabbedPane", new JPanel(), false);
		tab.addTab("Has", new JPanel());
		tab.addTab("Popup", new JPanel());
		tab.addTab("PopupMenu", new JPanel());

		// tab.setIconDrawCenter(1, false);

		// 添加关闭按钮事件

		// 设置弹出菜单
		JPopupMenu menu = new JPopupMenu();
		for (int i = 0; i < 10; i++) {
			menu.add(new JMenuItem("item " + i));
		}
		tab.setPopup(menu);

		frame.getContentPane().add(tab, BorderLayout.CENTER);
		frame.setSize(400, 320);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
	}
}