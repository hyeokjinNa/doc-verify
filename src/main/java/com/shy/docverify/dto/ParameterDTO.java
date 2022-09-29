package com.shy.docverify.dto;

public class ParameterDTO {

	private String name;
	private TableDTO tableDTO;
	
	public String getName() {
		return name;
	}
	public TableDTO getTableDTO() {
		return tableDTO;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTableDTO(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
	}
	
}
