package com.shy.docverify.dto;

import java.util.List;

public class TableDTO {

	private String schema;
	private String tableName;
	private String entityName;
	private String physicalName;
	private String logicalName;
	private String dataType;
	private String length;
	private String precision;
	private String scale;
	private String notNull;
	private String pk;
	private String type;
	private List<String> wrongColumns;
	private boolean match = false;
	private boolean check = false;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	public String getLogicalName() {
		return logicalName;
	}
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getWrongColumn() {
		return wrongColumns;
	}
	public void setWrongColumn(List<String> wrongColumns) {
		this.wrongColumns = wrongColumns;
	}
	public boolean getMatch() {
		return match;
	}
	public void setMatch(boolean match) {
		this.match = match;
	}
	public boolean getCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	
}
