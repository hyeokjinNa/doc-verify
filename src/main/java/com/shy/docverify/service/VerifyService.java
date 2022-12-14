package com.shy.docverify.service;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableNameDTO;
import com.shy.docverify.dto.UserDTO;

public interface VerifyService {

	public List<TableDTO> getDBTable(TableDTO tableDto, UserDTO user); 
	
	public Map<String, Object> verifyTables(ParameterDTO firstTable, ParameterDTO secondTable);
	
//	public Map<String, Object> verifySelectTable (ParameterDTO firstParam, ParameterDTO secondParam);
	
	public List<TableNameDTO> getTableName(TreeSet<String> asisTableName, UserDTO user);
	
	public List<Map<String, Object>> excelVerify(List<ParameterDTO> data, UserDTO user);
	
	public List<TableDTO> checkTableList(String schema, UserDTO user);
}
