package com.likg.util;

public class TableItem {

	//表名称	字段	是否主键	是否外键/表	是否索引	类型/长度	备注
	private String field; //字段
	
	private String type; //字段类型
	
	private String key; //
	
	private String comment; //字段注释
	
	/**
	 * 判断是否为主键
	 */
	public boolean isPK() {
		return "PRI".equals(this.key);
	}
	
	/**
	 * 判断是否为索引
	 */
	public boolean isIndex() {
		return "MUL".equals(this.key);
	}
	
	@Override
	public String toString() {
		return "[field=" + field + ", type=" + type + ", key=" + key + ", comment=" + comment + "]\n";
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
