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

import org.jdom.Element;

import com.framework.exception.ExceptionUtils;
import com.xswing.framework.view.Context;

/**
 * @author HWYan
 * 
 */
@XElement(names = { Const.GRIDBAGPANEL })
public class GridBagPanelParser extends ElementParser<JPanel> {

	@SuppressWarnings("unchecked")
	@Override
	public JPanel parseElement(Context context, Element source) {
		JPanel panel = createPanel();
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
		String value = e.getAttributeValue("gridx");
		if (value == null || value.length() == 0) {
			if (c == 0)
				constraints.gridx = 0;
			else
				constraints.gridx += constraints.gridwidth;
		} else
			constraints.gridx = Integer.parseInt(value);

		value = e.getAttributeValue("gridy");
		if (value == null || value.length() == 0)
			constraints.gridy = r;
		else
			constraints.gridy = Integer.parseInt(value);

		value = e.getAttributeValue("gridwidth");
		if (value == null || value.length() == 0)
			constraints.gridwidth = 1;
		else
			constraints.gridwidth = Integer.parseInt(value);

		value = e.getAttributeValue("gridheight");
		if (value == null || value.length() == 0)
			constraints.gridheight = 1;
		else
			constraints.gridheight = Integer.parseInt(value);

		value = e.getAttributeValue("weightx");
		if (value == null || value.length() == 0)
			constraints.weightx = 100;
		else
			constraints.weightx = Integer.parseInt(value);

		value = e.getAttributeValue("weighty");
		if (value == null || value.length() == 0)
			constraints.weighty = 100;
		else
			constraints.weighty = Integer.parseInt(value);

		value = e.getAttributeValue("ipadx");
		if (value == null || value.length() == 0)
			constraints.ipadx = 0;
		else
			constraints.ipadx = Integer.parseInt(value);

		value = e.getAttributeValue("ipady");
		if (value == null || value.length() == 0)
			constraints.ipady = 0;
		else
			constraints.ipady = Integer.parseInt(value);

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

	protected JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		return panel;
	}

}
