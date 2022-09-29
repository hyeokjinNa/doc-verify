package com.shy.docverify.service;

import java.util.List;
import java.util.Map;

import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;

public interface VerifyService {

	public List<TableDTO> getDBTable(String schema, String table); 
	
	public Map<String, Object> verifyTables(ParameterDTO firstTable, ParameterDTO secondTable);
	
}
