package com.xswing.framework.view.parser;

import java.util.List;
import java.util.Vector;

import javax.swing.JTable;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.framework.proxy.DynamicObject;
import com.framework.proxy.DynamicObjectFactory2;
import com.xswing.framework.view.Context;

import net.sf.cglib.proxy.MethodInterceptor;

@XElement(names = { Const.TREETABLE })
public class TreeTableParser extends TableParser {

	protected Class<?> getBaseClass(Element source) {
		return JXTreeTable.class;
	}

	@Override
	protected void setModel(JTable table, Context context, Element source, Vector<String> columnNames, List<String> columnAttrs, List<Class<?>> columnClasses) {
		String modelText = getString(source, Const.MODEL);
		TreeTableModel model = null;
		if (StringUtils.isNotEmpty(modelText)) {
			Class<?> modelCls = BaseUtils.getClass(modelText);
			if (modelCls != null) {
				model = (TreeTableModel) BaseUtils.newInstance(modelCls);
			} else {
				model = (TreeTableModel) context.getBean(modelText);
			}
		} else {
			Element modelElement = source.getChild(Const.MODEL);
			if (modelElement != null) {
				model = (TreeTableModel) ParserEngine.parse(context, modelElement);
			} else {
				model = new DefaultTreeTableModel(new DefaultMutableTreeTableNode(), columnNames);
			}
		}
		final TreeTableModel tempModel = model;
		MethodInterceptor interceptor = (obj, method, args, proxy) -> {
			if (method.getName().equals("getColumnName") && args.length == 1 && args[0] instanceof Integer) {
				return columnNames.get((int) args[0]);
			} else if (method.getName().equals("getColumnCount") && args.length == 0) {
				return columnNames.size();
			} else if (method.getName().equals("getColumnClass") && args[0] instanceof Integer) {
				return columnClasses.get((int) args[0]);
			} else {
				return proxy.invoke(tempModel, args);
			}
		};
		model = DynamicObjectFactory2.createDynamicObject(model, interceptor, (Class<? extends DynamicObject>[]) null, (DynamicObject[]) null);
		((JXTreeTable) table).setTreeTableModel(model);
	}

	@Override
	public JTable parseElement(Context context, Element source) {
		JTable table = super.parseElement(context, source);
		boolean horizontalLines = getBoolean(source, Const.HORIZONTALLINES, false);
		table.setShowHorizontalLines(horizontalLines);
		boolean verticalLines = getBoolean(source, Const.VERTICALLINES, false);
		table.setShowVerticalLines(verticalLines);
		return table;
	}

}