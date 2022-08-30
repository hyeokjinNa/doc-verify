package com.shy.docverify.dto;

public class TableDTO {

	private String schema;
	private String table;
	private String tableComment;
	private String column;
	private String columnComment;
	private String type;
	private int length;
	private int precision;
	private int scale;
	private boolean nullable;
	private boolean pk;
	private boolean fk;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isFk() {
		return fk;
	}
	public void setFk(boolean fk) {
		this.fk = fk;
	}
	
	@Override
	public String toString() {
		return "TableDTO [schema=" + schema + ", table=" + table + ", tableComment=" + tableComment + ", column="
				+ column + ", columnComment=" + columnComment + ", type=" + type + ", length=" + length + ", precision="
				+ precision + ", scale=" + scale + ", nullable=" + nullable + ", pk=" + pk + ", fk=" + fk + "]";
	}
	
	
}
