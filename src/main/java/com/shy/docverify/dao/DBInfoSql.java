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
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.util.ConvertSqlToString;

@Service
public class DBInfoSql {
	
	@Autowired
	private ConvertSqlToString convert;
	
	
	public boolean connectionTest(UserDTO user) {
		
		boolean check = false;
		Connection conn = null;
		
		try {
			Class.forName(user.getDriver());
			conn = DriverManager.getConnection(user.getUrl(), user.getUserName(), user.getPassword());
			check = true;
			System.out.println("User Login Success");
		} catch(Exception e) {
			check = false;
			System.out.println("User Login Failed");
		} finally {
			try {
				if(conn != null) conn.close();
			} catch(Exception e) {
				System.out.println("close Error");
			}
		}

		return check;
	}
	
	public List<TableDTO> selectTableInfo(String owner, String table) {
		
		UserDTO user = new UserDTO.UserBuilder()
				.url("jdbc:tibero:thin:@10.47.39.125:8629:DB_D_GMD")
				.driver("com.tmax.tibero.jdbc.TbDriver")
				.userName("GMDMF")
				.password("gmdmf")
				.build();
		
		String sql = convert.Convert("sql/selectTableInfo.sql");
		
		List<TableDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		
		try {
			
			Class.forName(user.getDriver());
			
			conn = DriverManager.getConnection(user.getUrl(), user.getUserName(), user.getPassword());
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
	
	public List<String> selectTableList(String schema, UserDTO user) {
		
		
		String sql = convert.Convert("sql/selectTables.sql");
		List<String> list = new ArrayList();
		
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		
		try {
			
			Class.forName(user.getDriver());
			
			conn = DriverManager.getConnection(user.getUrl(), user.getUserName(), user.getPassword());
			pre = conn.prepareStatement(sql);
			pre.setString(1, schema);
			result = pre.executeQuery();
			
			while(result.next()) {
				list.add(result.getString(1));
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
