package com.shy.docverify.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dao.DBInfoSql;

@Service
public class VerifyServiceImpl implements VerifyService {

	@Autowired
	private DBInfoSql dbInfoSql;
	
	
	@Override
	public List<TableDTO> getDBTable(String schema, String table) {
		return dbInfoSql.selectTableInfo(schema, table);
	}

}
