package com.shy.docverify.dto;

public class TableNameDTO {

	private String docTableName;
	private String dbTableName;
	
	public TableNameDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getDocTableName() {
		return docTableName;
	}

	public void setDocTableName(String docTableName) {
		this.docTableName = docTableName;
	}

	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}

	@Override
	public String toString() {
		return "TableNameDTO [docTableName=" + docTableName + ", dbTableName=" + dbTableName + "]";
	}
	
	
}
