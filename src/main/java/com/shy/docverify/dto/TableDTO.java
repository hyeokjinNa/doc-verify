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
	private String dbTableName;
	private List<String> wrongColumns;
	private boolean match;
	private boolean check;

	public TableDTO() {
		
	}
	
	private TableDTO(TableBuilder builder) {
		this.schema = builder.schema;
		this.tableName = builder.tableName;
		this.entityName = builder.entityName;
		this.physicalName = builder.physicalName;
		this.logicalName = builder.logicalName;
		this.dataType = builder.dataType;
		this.length = builder.length;
		this.precision = builder.precision;
		this.scale = builder.scale;
		this.notNull = builder.notNull;
		this.pk = builder.pk;
		this.wrongColumns = builder.wrongColumns;
		this.match = builder.match;
		this.check = builder.check;
	}

	public String getSchema() {
		return schema;
	}

	public String getTableName() {
		return tableName;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getPhysicalName() {
		return physicalName;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public String getDataType() {
		return dataType;
	}

	public String getLength() {
		return length;
	}

	public String getPrecision() {
		return precision;
	}

	public String getScale() {
		return scale;
	}

	public String getNotNull() {
		return notNull;
	}

	public String getPk() {
		return pk;
	}

	public List<String> getWrongColumns() {
		return wrongColumns;
	}

	public boolean isMatch() {
		return match;
	}

	public boolean isCheck() {
		return check;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public void setWrongColumns(List<String> wrongColumns) {
		this.wrongColumns = wrongColumns;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbtableName) {
		this.dbTableName = dbtableName;
	}

	

	@Override
	public String toString() {
		return "TableDTO [schema=" + schema + ", tableName=" + tableName + ", entityName=" + entityName
				+ ", physicalName=" + physicalName + ", logicalName=" + logicalName + ", dataType=" + dataType
				+ ", length=" + length + ", precision=" + precision + ", scale=" + scale + ", notNull=" + notNull
				+ ", pk=" + pk + ", dbtableName=" + dbTableName + ", wrongColumns=" + wrongColumns + ", match=" + match
				+ ", check=" + check + "]";
	}



	public static class TableBuilder {

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
		private List<String> wrongColumns;
		private boolean match = false;
		private boolean check = false;

		public TableBuilder schema(String schema) {
			this.schema = schema;
			return this;
		}

		public TableBuilder tableName(String tableName) {
			this.tableName = tableName;
			return this;
		}

		public TableBuilder entityName(String entityName) {
			this.entityName = entityName;
			return this;
		}

		public TableBuilder physicalName(String physicalName) {
			this.physicalName = physicalName;
			return this;
		}

		public TableBuilder logicalName(String logicalName) {
			this.logicalName = logicalName;
			return this;
		}

		public TableBuilder dataType(String dataType) {
			this.dataType = dataType;
			return this;
		}

		public TableBuilder length(String length) {
			this.length = length;
			return this;
		}

		public TableBuilder precision(String precision) {
			this.precision = precision;
			return this;
		}

		public TableBuilder scale(String scale) {
			this.scale = scale;
			return this;
		}

		public TableBuilder notNull(String notNull) {
			this.notNull = notNull;
			return this;
		}

		public TableBuilder pk(String pk) {
			this.pk = pk;
			return this;
		}

		public TableBuilder wrongColumns(List<String> wrongColumns) {
			this.wrongColumns = wrongColumns;
			return this;
		}

		public TableBuilder match(boolean match) {
			this.match = match;
			return this;
		}

		public TableBuilder check(boolean check) {
			this.check = check;
			return this;
		}

		public TableDTO build() {
			TableDTO dto = new TableDTO(this);
			return dto;
		}

	}

}
