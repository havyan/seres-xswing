/**
 * 
 */
package com.xswing.framework.test;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.framework.exception.ExceptionUtils;
import com.xswing.framework.view.XPanelBuilder;
import com.xswing.framework.view.parser.BeanParser;
import com.xswing.framework.view.parser.XElement;

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
		XElement xmlElement = BeanParser.class.getAnnotation(XElement.class);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(XPanelBuilder.build("./gantt.xml"));
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
