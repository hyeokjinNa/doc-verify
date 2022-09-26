package com.shy.docverify.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.util.ConvertSqlToString;

@Service
public class DBInfoSql {
	
	@Autowired
	private ConvertSqlToString convert;
	
	public List<TableDTO> selectTableInfo(String owner, String table) {
		
		String url = "jdbc:tibero:thin:@10.47.39.125:8629:DB_D_GMD";
		String driver = "com.tmax.tibero.jdbc.TbDriver";
		String username = "GMDMF";
		String password = "gmdmf";
		
		String sql = convert.Convert("sql/selectTableInfo.sql");
		
		List<TableDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		
		try {
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, username, password);
			pre = conn.prepareStatement(sql);
			pre.setString(1, owner);
			pre.setString(2, table);
			result = pre.executeQuery();
			
			while(result.next()) {
				TableDTO dto = new TableDTO.TableBuilder()
					.schema(result.getString(1))
					.tableName(result.getString(2))
					.entityName(result.getString(3))
					.physicalName(result.getString(4))
					.logicalName(result.getString(5))
					.dataType(result.getString(6))
					.length(result.getString(7))
					.precision(result.getString(8))
					.scale(result.getString(9))
					.notNull(result.getString(10))
					.pk(result.getString(11))
					.build();
				
				list.add(dto);
			}
			
		} catch(Exception e) {
			System.out.println("Connection Error");
			
		} finally {
			try {
				if(result != null) result.close();
				if(pre != null) pre.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				System.out.println("close Error");
			}
		}
		
		
		return list;
	}
	
	
}
