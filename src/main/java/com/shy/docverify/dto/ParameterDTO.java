package com.shy.docverify.dto;

import java.util.List;

public class ParameterDTO {

	private String name;
	private List<TableDTO> tableDTO;
	
	public String getName() {
		return name;
	}
	public List<TableDTO> getTableDTO() {
		return tableDTO;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTableDTO(List<TableDTO> tableDTO) {
		this.tableDTO = tableDTO;
	}
	
}
