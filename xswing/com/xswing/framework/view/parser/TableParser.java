package com.xswing.framework.view.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import com.framework.common.BaseUtils;
import com.framework.proxy.DynamicObject;
import com.framework.proxy.DynamicObjectFactory2;
import com.xswing.framework.view.Context;

import net.sf.cglib.proxy.MethodInterceptor;

@XElement(names = { Const.TABLE })
public class TableParser extends ComponentParser<JTable> {

	@Override
	public JTable parseElement(Context context, Element source) {
		JTable table = super.parseElement(context, source);
		Vector<String> columnNames = new Vector<String>();
		List<Class<?>> columnClasses = new ArrayList<Class<?>>();
		List<Element> children = source.getChildren(Const.COLUMN);
		if (children != null && children.size() > 0) {
			for (Element child : children) {
				columnNames.add(getString(child, Const.NAME));
				String classText = getString(child, Const.CLASS);
				if (StringUtils.isNotEmpty(classText)) {
					Class<?> cls = BaseUtils.getClass(classText);
					if (cls != null) {
						columnClasses.add(cls);
					} else {
						columnClasses.add(Object.class);
					}
				} else {
					columnClasses.add(Object.class);
				}
			}
			table.setModel(createModel(context, source, columnNames, columnClasses));
			TableColumnModel columnModel = table.getColumnModel();
			for (int i = 0; i < children.size(); i++) {
				Element child = children.get(i);
				TableColumn column = columnModel.getColumn(i);
				parseColumn(column, context, child);
			}
		}
		return table;
	}

	private TableColumn parseColumn(TableColumn column, Context context, Element source) {
		Element e = source.getChild(Const.HEADER);
		if (e != null) {
			column.setHeaderRenderer((TableCellRenderer) ParserEngine.parse(context, e));
		}
		e = source.getChild(Const.EDITOR);
		if (e != null) {
			column.setCellEditor((TableCellEditor) ParserEngine.parse(context, e));
		}
		e = source.getChild(Const.RENDERER);
		if (e != null) {
			column.setCellRenderer((TableCellRenderer) ParserEngine.parse(context, e));
		}
		String widthText = getString(source, Const.WIDTH);
		if (StringUtils.isNotEmpty(widthText)) {
			int width = Integer.valueOf(widthText);
			column.setPreferredWidth(width);
		}
		return column;
	}

	private TableModel createModel(Context context, Element source, Vector<String> columnNames, List<Class<?>> columnClasses) {
		String modelText = getString(source, Const.MODEL);
		TableModel model = null;
		if (StringUtils.isNotEmpty(modelText)) {
			Class<?> modelCls = BaseUtils.getClass(modelText);
			if (modelCls != null) {
				model = (TableModel) BaseUtils.newInstance(modelCls);
			} else {
				model = (TableModel) context.getBean(modelText);
			}
		} else {
			Element modelElement = source.getChild(Const.MODEL);
			if (modelElement != null) {
				model = (TableModel) ParserEngine.parse(context, modelElement);
			} else {
				model = new DefaultTableModel(columnNames, 0);
			}
		}
		final TableModel tempModel = model;
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
		return DynamicObjectFactory2.createDynamicObject(model, interceptor, (Class<? extends DynamicObject>[]) null, (DynamicObject[]) null);
	}

}
