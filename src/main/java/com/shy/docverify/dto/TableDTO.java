package com.shy.docverify.dto;

public class TableDTO {

	private String schema;
	private String table;
	private String tableComment;
	private String column;
	private String columnComment;
	private String type;
	private String notNull;
	private String pk;
	private String verify;
	private String msg;
	private int length;
	private int precision;
	private int scale;
	
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
	public String getNotNull() {
		return notNull;
	}
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	
	@Override
	public String toString() {
		return "TableDTO [schema=" + schema + ", table=" + table + ", tableComment=" + tableComment + ", column="
				+ column + ", columnComment=" + columnComment + ", type=" + type + ", notNull=" + notNull + ", pk=" + pk
				+ ", verify=" + verify + ", msg=" + msg + ", length=" + length + ", precision=" + precision + ", scale="
				+ scale + "]";
	}
}
