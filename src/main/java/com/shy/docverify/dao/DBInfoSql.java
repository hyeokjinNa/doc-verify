package com.shy.docverify.sql;

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
				TableDTO dto = new TableDTO();
				dto.setSchema(result.getString(1));
				dto.setTableName(result.getString(2));
				dto.setEntityName(result.getString(3));
				dto.setPhysicalName(result.getString(4));
				dto.setLogicalName(result.getString(5));
				dto.setDataType(result.getString(6));
				dto.setLength(result.getString(7));
				dto.setPrecision(result.getString(8));
				dto.setScale(result.getString(9));
				dto.setNotNull(result.getString(10));
				dto.setPk(result.getString(11));
				
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
