package com.likg.util;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String name; //表名称
	
	private String comment; //注释
	
	//字段明细
	private List<TableItem> itemList = new ArrayList<TableItem>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<TableItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<TableItem> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "Table " + name + " " + comment + "\n" + itemList + "\n";
	}
	
}
