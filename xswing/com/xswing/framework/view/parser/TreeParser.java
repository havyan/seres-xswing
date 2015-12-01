package com.xswing.framework.view.parser;

import javax.swing.JTree;

import org.jdom2.Element;

import com.xswing.framework.view.Context;

@XElement(names = { Const.TREE })
public class TreeParser extends ComponentParser<JTree> {

	@Override
	public JTree parseElement(Context context, Element source) {
		JTree tree = super.parseElement(context, source);
		tree.setRootVisible(getBoolean(source, Const.ROOTVISIBLE, true));
		return tree;
	}

}