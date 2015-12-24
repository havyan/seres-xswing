/**
 * 
 */
package com.xswing.framework.view.parser;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JPanel;

import org.jdom2.Element;

import com.framework.exception.ExceptionUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.GRIDBAGPANEL })
public class GridBagPanelParser extends ComponentParser<JPanel> {

	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = super.parseElement(context, source);
		panel.setLayout(new GridBagLayout());
		List<Element> rows = source.getChildren();
		for (int i = 0; i < rows.size(); i++) {
			Element row = (Element) rows.get(i);
			List<Element> cells = row.getChildren();
			for (int j = 0; j < cells.size(); j++) {
				Element cell = (Element) cells.get(j);
				parseCell(context, panel, cell, i, j);
			}

		}
		return panel;
	}

	private GridBagConstraints constraints = new GridBagConstraints();

	/**
	 * parse the properties of cell.
	 * 
	 * @param e
	 * @param r
	 * @param c
	 */
	private void parseCell(Context context, JPanel panel, Element e, int r, int c) {
		constraints.gridx = getInt(e, "gridx", c == 0 ? 0 : constraints.gridx + constraints.gridwidth);
		constraints.gridy = getInt(e, "gridy", r);
		constraints.gridwidth = getInt(e, "gridwidth", 1);
		constraints.gridheight = getInt(e, "gridheight", 1);
		constraints.weightx = getInt(e, "weightx", 100);
		constraints.weighty = getInt(e, "weighty", 100);
		constraints.ipadx = getInt(e, "ipadx", 0);
		constraints.ipady = getInt(e, "ipady", 0);

		Class<GridBagConstraints> cl = GridBagConstraints.class;

		try {
			int fill = GridBagConstraints.NONE;
			String name = e.getAttributeValue("fill");
			Field f = null;
			if (name != null) {
				f = cl.getField(name);
				fill = f.getInt(cl);
			}
			constraints.fill = fill;

			int anchor = GridBagConstraints.CENTER;
			name = e.getAttributeValue("anchor");
			if (name != null) {
				f = cl.getField(name);
				anchor = f.getInt(cl);
			}
			constraints.anchor = anchor;
		} catch (Exception e1) {
			ExceptionUtils.logAndShowException(e1);
			return;
		}

		Component comp = (Component) ParserEngine.parse(context, e);
		panel.add(comp, constraints);
	}

}
