package com.shy.docverify.service;

import java.util.List;

import com.shy.docverify.dto.TableDTO;

public interface VerifyService {

	public List<TableDTO> getDBTable(String schema, String table); 
	
}
