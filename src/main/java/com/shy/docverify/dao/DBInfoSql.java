package com.shy.docverify.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableNameDTO;
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.util.ConvertUtil;

@Service
public class DBInfoSql {
	
	@Autowired
	private ConvertUtil convert;
	
	
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
	
	public List<TableDTO> selectTableInfo(TableDTO tableDto, UserDTO user) {
		
		String sql = convert.convertSqlToString("sql/selectTableInfo.sql");
		
		if(tableDto.getPhysicalName() != null) {
			sql = sql + "AND COL.COLUMN_NAME = ?";
		}
		
		List<TableDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		
		try {
			
			Class.forName(user.getDriver());
			
			conn = DriverManager.getConnection(user.getUrl(), user.getUserName(), user.getPassword());
			pre = conn.prepareStatement(sql);
			pre.setString(1, tableDto.getSchema());
			pre.setString(2, tableDto.getDbTableName());
			
			if(tableDto.getPhysicalName() != null) {
				pre.setString(3, tableDto.getPhysicalName());
			}
			result = pre.executeQuery();
			
			while(result.next()) {
				TableDTO dto = new TableDTO.TableBuilder()
					.schema(result.getString(1))
					.dbTableName(result.getString(2))
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
	
	public List<TableDTO> selectTableWithSchema(String schema, UserDTO user) {
		
		String sql = convert.convertSqlToString("sql/selectTableWithSchema.sql");
		List<TableDTO> list = new ArrayList();
		
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
				TableDTO dto = new TableDTO.TableBuilder()
						.dbTableName(result.getString(1))
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
	
	public List<TableNameDTO> selectTableNameList(TreeSet<String> asisTableName, UserDTO user){
		
		user = new UserDTO.UserBuilder()
				.url("jdbc:tibero:thin:@10.47.39.125:8629:DB_D_GMD")
				.driver("com.tmax.tibero.jdbc.TbDriver")
				.userName("GMDMF")
				.password("gmdmf")
				.build();
		
		String sql = convert.convertSqlToString("sql/selectTableNames.sql");
		List<String> setToList = new ArrayList<String>();
		setToList.addAll(asisTableName);
		
			
		List<TableNameDTO> tableNamelist = new ArrayList<TableNameDTO>();
		
		
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet result = null;
			
		try {
			
			Class.forName(user.getDriver());
			
			conn = DriverManager.getConnection(user.getUrl(), user.getUserName(), user.getPassword());
			
			for(int i=0; i<setToList.size(); i++) {
				TableNameDTO tableName = new TableNameDTO();
				pre = conn.prepareStatement(sql);
				pre.setString(1, setToList.get(i));
				pre.setString(2, "%"+setToList.get(i));
				result = pre.executeQuery();
				
				
				while(result.next()) {
					tableName.setDocTableName(result.getString(1));
					tableName.setDbTableName(result.getString(2));
				}//while end
				
				tableNamelist.add(tableName);
				
			}//for end
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection Error");
		} finally {
			try {
				if(result != null) result.close();
				if(pre != null) pre.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				System.out.println("close Error");
			}//try~catch end
			
		}//try~catch~finally end
		
		
		return tableNamelist;
	}
	
	
}
