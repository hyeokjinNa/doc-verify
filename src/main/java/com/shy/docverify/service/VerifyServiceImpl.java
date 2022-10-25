package com.shy.docverify.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.dao.DBInfoSql;
import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableNameDTO;
import com.shy.docverify.dto.UserDTO;

@Service
public class VerifyServiceImpl implements VerifyService {

	@Autowired
	private DBInfoSql dbInfoSql;
	
	
	@Override
	public List<TableDTO> getDBTable(String schema, String table, String column, UserDTO user) {
		return dbInfoSql.selectTableInfo(schema, table, column, user);
	}
	
	@Override
	public List<TableNameDTO> getTableNmae(TreeSet<String> asisTableName, UserDTO user) {
		return dbInfoSql.selectTableNameList(asisTableName, user);
	}
	
	@Override
	public Map<String, Object> verifyTables(ParameterDTO firstParam, ParameterDTO secondParam) {

		String[] tableKeys = {"TableName", "PhysicalName", "EntityName", "LogicalName", "DataType", "Length",
				"Precision", "Scale", "NotNull", "Pk"};

		List<TableDTO> firstTableList = firstParam.getTableDTO();
		List<TableDTO> secondTableList = secondParam.getTableDTO();
		
		Map<String, Object> result = new HashMap();

		for (TableDTO firstTable : firstTableList) {

			List<String> firstRow = setRowList(firstTable);
			
			if(StringUtils.equals(firstRow.get(11), "GMDMI")) 
				firstRow.set(0, firstRow.get(10));
			
			for (TableDTO secondTable : secondTableList) {

				List<String> secondRow = setRowList(secondTable);
				
				if (StringUtils.equals(firstRow.get(0), secondRow.get(0))
						&& StringUtils.equals(firstRow.get(1), secondRow.get(1))) {
					
					firstTable.setCheck(true);
					secondTable.setCheck(true);

					boolean match = true;

					for (int col = 2; col < tableKeys.length; col++) {
						
						if (!StringUtils.equals(firstRow.get(col), secondRow.get(col))) {
							
							List<String> wrongColumns = firstTable.getWrongColumns();
							match = false;

							if(wrongColumns == null) 
								wrongColumns = new ArrayList<String>();
							
							wrongColumns.add(tableKeys[col]);
							
							firstTable.setWrongColumns(wrongColumns);
							secondTable.setWrongColumns(wrongColumns);
						}
						
					}
					
					firstTable.setMatch(match);
					secondTable.setMatch(match);
					
					break;
				}

			}
			
		}
		
		result.put(firstParam.getName(), firstTableList);
		result.put(secondParam.getName(), secondTableList);

		return result;
	}
	
	@Override
	public Map<String, Object> verifySelectTable (ParameterDTO firstParam, ParameterDTO secondParam) {
		
		String[] tableKeys = {"TableName", "PhysicalName", "EntityName", "LogicalName", "DataType", "Length",
				"Precision", "Scale", "NotNull", "Pk"};
		Map<String, Object> result = new HashMap();
		
		List<TableDTO> firstTableList = firstParam.getTableDTO();
		List<TableDTO> secondTableList = secondParam.getTableDTO();
		
		for (TableDTO firstTable : firstTableList) {

			List<String> firstRow = setRowList(firstTable);

			if(StringUtils.equals(firstRow.get(11), "GMDMI")) 
				firstRow.set(0, firstRow.get(10));
			
			for(TableDTO secondTable : secondTableList) {
				
				List<String> secondRow = setRowList(secondTable);
				
				if(StringUtils.equals(firstRow.get(1), secondRow.get(1))) {

					firstTable.setCheck(true);
					secondTable.setCheck(true);
				
					boolean match = true;
					
					for (int col = 2; col < tableKeys.length; col++) {
						
						if (!StringUtils.equals(firstRow.get(col), secondRow.get(col))) {
							
							List<String> wrongColumns = firstTable.getWrongColumns();
							match = false;

							if(wrongColumns == null) 
								wrongColumns = new ArrayList<String>();
							
							wrongColumns.add(tableKeys[col]);
							
							firstTable.setWrongColumns(wrongColumns);
							secondTable.setWrongColumns(wrongColumns);
						}
						
					}
					
					firstTable.setMatch(match);
					secondTable.setMatch(match);
					
					break;
				
				}

			}
			
		}
		
		result.put(firstParam.getName(), firstTableList);
		result.put(secondParam.getName(), secondTableList);
		
		return result;
	}
	
	public List<String> setRowList(TableDTO dto) {

		List<String> list = new ArrayList();

		list.add(dto.getTableName());
		list.add(dto.getPhysicalName());
		list.add(dto.getEntityName());
		list.add(dto.getLogicalName());
		list.add(dto.getDataType());
		list.add(dto.getLength());
		list.add(dto.getPrecision());
		list.add(dto.getScale());
		list.add(dto.getNotNull());
		list.add(dto.getPk());
		list.add(dto.getDbTableName());
		list.add(dto.getSchema());
		
		return list;
	}
	
	@Override
	public List<Map<String, Object>> excelVerify(List<ParameterDTO> data, UserDTO user) {

		List<Map<String, Object>> result = new ArrayList<>();
		
		
		for(ParameterDTO parameterDto : data) {
			
			Map<String,String> tableNameMap = new HashMap<>();
			List<TableDTO> tableDtoList = parameterDto.getTableDTO();
			
			for(TableDTO tableDto : tableDtoList) {
				if(!tableNameMap.containsKey(tableDto.getTableName())) {
					if(StringUtils.equals(tableDto.getSchema(),"GMDMI")) {
						tableNameMap.put(tableDto.getDbTableName() + ":" + tableDto.getPhysicalName(), tableDto.getSchema());
					} else {
						tableNameMap.put(tableDto.getTableName() + ":" + tableDto.getPhysicalName(), tableDto.getSchema());
					}
				}
			}
			
			List<TableDTO> dbTableDtoList = new ArrayList<>();
			
			for(String tableName : tableNameMap.keySet()) {
				dbTableDtoList.addAll(getDBTable(tableNameMap.get(tableName), tableName.split(":")[0], tableName.split(":")[1], user));
			}
			
			ParameterDTO dbParameterDto = new ParameterDTO();
			dbParameterDto.setName("DB");
			dbParameterDto.setTableDTO(dbTableDtoList);
			
			result.add(verifyTables(parameterDto, dbParameterDto));
			
		}
		
		return result;
		
	}

}
