package com.shy.docverify.service;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableNameDTO;
import com.shy.docverify.dto.UserDTO;

public interface VerifyService {

	public List<TableDTO> getDBTable(String schema, String table); 
	
	public Map<String, Object> verifyTables(ParameterDTO firstTable, ParameterDTO secondTable);
	
	public Map<String, Object> verifySelectTable (ParameterDTO firstParam, ParameterDTO secondParam);
	
	public List<TableNameDTO> getTableNmae(TreeSet<String> asisTableName, UserDTO user);
	
}
