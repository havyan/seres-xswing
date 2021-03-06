package com.xswing.framework.view.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
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
import com.xswing.framework.view.components.ListTableModel;

import net.sf.cglib.proxy.MethodInterceptor;

@XElement(names = { Const.TABLE })
public class TableParser extends ComponentParser<JTable> {

	@Override
	public JTable parseElement(Context context, Element source) {
		JTable table = super.parseElement(context, source);
		Vector<String> columnNames = new Vector<String>();
		List<String> columnAttrs = new ArrayList<String>();
		List<Class<?>> columnClasses = new ArrayList<Class<?>>();
		List<Element> children = source.getChildren(Const.COLUMN);
		if (children != null && children.size() > 0) {
			for (Element child : children) {
				columnNames.add(getString(child, Const.NAME));
				columnAttrs.add(getString(child, Const.ATTR));
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
			setModel(table, context, source, columnNames, columnAttrs, columnClasses);
			TableColumnModel columnModel = table.getColumnModel();
			for (int i = 0; i < children.size(); i++) {
				Element child = children.get(i);
				TableColumn column = columnModel.getColumn(i);
				parseColumn(column, context, child);
			}
		}
		boolean horizontalLines = getBoolean(source, Const.HORIZONTALLINES, true);
		table.setShowHorizontalLines(horizontalLines);
		boolean verticalLines = getBoolean(source, Const.VERTICALLINES, true);
		table.setShowVerticalLines(verticalLines);
		boolean sortable = getBoolean(source, Const.SORTABLE, true);
		table.setAutoCreateRowSorter(sortable);
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
			column.setWidth(width);
		}
		widthText = getString(source, Const.MAXWIDTH);
		if (StringUtils.isNotEmpty(widthText)) {
			int width = Integer.valueOf(widthText);
			column.setMaxWidth(width);
		}
		widthText = getString(source, Const.MINWIDTH);
		if (StringUtils.isNotEmpty(widthText)) {
			int width = Integer.valueOf(widthText);
			column.setMinWidth(width);
		}
		return column;
	}

	protected void setModel(JTable table, Context context, Element source, Vector<String> columnNames, List<String> columnAttrs, List<Class<?>> columnClasses) {
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
				model = new ListTableModel(columnNames, columnAttrs, columnClasses);
			}
		}
		if (model instanceof ListTableModel) {
			ListTableModel listTableModel = (ListTableModel)model;
			String listText = getString(source, Const.LIST);
			if (StringUtils.isNotBlank(listText)) {
				listText = listText.trim();
				if (!hasProperty(listText)) {
					listText = "{{" + listText + "}}";
					bindSet(context, table, listText, value -> {
						listTableModel.setList((List<?>) value);
					});
				}
			}
		} else {
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
			model = DynamicObjectFactory2.createDynamicObject(model, interceptor, (Class<? extends DynamicObject>[]) null, (DynamicObject[]) null);
		}
		table.setModel(model);
	}

	@Override
	protected void bind(Context context, String id, JTable bean, Element source) {
		super.bind(context, id, bean, source);
		String text = getString(source, Const.ROWHEIGHT);
		if (StringUtils.isNotEmpty(text)) {
			bindSet(context, bean, text, value -> {
				if (value != null) {
					if (value instanceof Integer) {
						bean.setRowHeight((int) value);
					} else {
						bean.setRowHeight(Integer.valueOf(value.toString()));
					}
				}
			});
		}
	}

}
