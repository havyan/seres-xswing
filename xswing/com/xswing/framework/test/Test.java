/**
 * 
 */
package com.xswing.framework.test;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.framework.exception.ExceptionUtils;
import com.xswing.framework.view.XPanelBuilder;

/**
 * @author HWYan
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		try {
			// com.pagosoft.plaf.PgsLookAndFeel
			// com.jtattoo.plaf.acryl.AcrylLookAndFeel
			// com.jtattoo.plaf.bernstein.BernsteinLookAndFeel
			// com.jtattoo.plaf.smart.SmartLookAndFeel
			// net.sourceforge.napkinlaf.NapkinLookAndFeel
			// net.sf.tinylaf.TinyLookAndFeel
			// com.digitprop.tonic.TonicLookAndFeel
			// com.jtattoo.plaf.texture.TextureLookAndFeel
			// com.jtattoo.plaf.luna.LunaLookAndFeel
			// com.jtattoo.plaf.graphite.GraphiteLookAndFeel
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
		} catch (Exception e) {
			ExceptionUtils.logAndShowException(e);
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(XPanelBuilder.build("./gantt.xml", new Person("Haowei", 32)));
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

	public static class Person {
		private String name;

		private int age;
		
		public Person(){
			
		}

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

}
