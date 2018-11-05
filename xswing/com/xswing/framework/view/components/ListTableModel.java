package com.xswing.framework.view.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import com.framework.common.BaseUtils;

public class ListTableModel extends AbstractTableModel {
	
	private List<?> list = new ArrayList<Object>();
	
	private List<String> columnNames = new ArrayList<String>();
	private List<String> columnAttrs = new ArrayList<String>();
	private List<Class<?>> columnClasses = new ArrayList<Class<?>>();

	public ListTableModel(List<String> columnNames, List<String> columnAttrs, List<Class<?>> columnClasses) {
		super();
		this.columnNames = columnNames;
		this.columnAttrs = columnAttrs;
		this.columnClasses = columnClasses;
	}

	@Override
	public int getRowCount() {
		return this.list.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses.get(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object item = list.get(rowIndex);
		if (item != null) {
			String attr = columnAttrs.get(columnIndex);
			if (StringUtils.isNotEmpty(attr)) {
				return BaseUtils.getProperty(item, attr);
			}
		}
		return null;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		if (list == null) {
			list = new ArrayList<Object>();
		}
		this.list = list;
		this.fireTableDataChanged();
	}

}
