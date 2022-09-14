package com.shy.docverify.dto;

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
	private String match;
	private String msg;
	private String type;
	
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
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "TableDTO [schema=" + schema + ", tableName=" + tableName + ", entityName=" + entityName
				+ ", physicalName=" + physicalName + ", logicalName=" + logicalName + ", dataType=" + dataType
				+ ", length=" + length + ", precision=" + precision + ", scale=" + scale + ", notNull=" + notNull
				+ ", pk=" + pk + ", match=" + match + ", msg=" + msg + ", type=" + type + "]";
	}
	
}
