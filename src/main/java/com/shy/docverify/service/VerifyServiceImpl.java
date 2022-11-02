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
	public List<TableDTO> getDBTable(TableDTO tableDto, UserDTO user) {
		return dbInfoSql.selectTableInfo(tableDto, user);
	}
	
	@Override
	public List<TableNameDTO> getTableName(TreeSet<String> asisTableName, UserDTO user) {
		return dbInfoSql.selectTableNameList(asisTableName, user);
	}
	
	@Override
	public Map<String, Object> verifyTables(ParameterDTO firstParam, ParameterDTO secondParam) {

//		String[] tableKeys = {"tableName", "physicalName", "entityName", "logicalName", "dataType", "length",
//				"precision", "scale", "notNull", "pk"};
		String[] tableKeys = {"tableName", "physicalName", "entityName", "logicalName", "dataType", "length",
				"precision", "scale"};

		List<TableDTO> firstTableList = firstParam.getTableDTO();
		List<TableDTO> secondTableList = secondParam.getTableDTO();
		
		Map<String, Object> result = new HashMap();

		for (TableDTO firstTable : firstTableList) {

			List<String> firstRow = setRowList(firstTable);
			
			for (TableDTO secondTable : secondTableList) {

				List<String> secondRow = setRowList(secondTable);
				
				if (StringUtils.equals(firstRow.get(10), secondRow.get(10))
						&& StringUtils.equals(firstRow.get(1), secondRow.get(1))) {
					
					firstTable.setCheck(true);
					secondTable.setCheck(true);
					secondTable.setTableName(firstTable.getTableName());
					
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
		
		result.put("doc", firstTableList);
		result.put("db", secondTableList);
		result.put("excelName", firstParam.getName());

		return result;
	}
	
//	@Override
//	public Map<String, Object> verifySelectTable (ParameterDTO firstParam, ParameterDTO secondParam) {
//		
////		String[] tableKeys = {"tableName", "physicalName", "entityName", "logicalName", "dataType", "length",
////				"precision", "scale", "notNull", "pk"};
//
//				String[] tableKeys = {"tableName", "physicalName", "entityName", "logicalName", "dataType", "length",
//				"precision", "scale"};
//		
//		Map<String, Object> result = new HashMap();
//		
//		List<TableDTO> firstTableList = firstParam.getTableDTO();
//		List<TableDTO> secondTableList = secondParam.getTableDTO();
//		
//		for (TableDTO firstTable : firstTableList) {
//
//			List<String> firstRow = setRowList(firstTable);
//
//			for(TableDTO secondTable : secondTableList) {
//				
//				List<String> secondRow = setRowList(secondTable);
//				
//				if(StringUtils.equals(firstRow.get(1), secondRow.get(1))) {
//
//					firstTable.setCheck(true);
//					secondTable.setCheck(true);
//				
//					boolean match = true;
//					
//					for (int col = 2; col < tableKeys.length; col++) {
//						
//						if (!StringUtils.equals(firstRow.get(col), secondRow.get(col))) {
//							
//							List<String> wrongColumns = firstTable.getWrongColumns();
//							match = false;
//
//							if(wrongColumns == null) 
//								wrongColumns = new ArrayList<String>();
//							
//							wrongColumns.add(tableKeys[col]);
//							
//							firstTable.setWrongColumns(wrongColumns);
//							secondTable.setWrongColumns(wrongColumns);
//						}
//						
//					}
//					
//					firstTable.setMatch(match);
//					secondTable.setMatch(match);
//					
//					break;
//				
//				}
//
//			}
//			
//		}
//		
//		result.put(firstParam.getName(), firstTableList);
//		result.put(secondParam.getName(), secondTableList);
//		
//		return result;
//	}
	
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
	public List<Map<String, Object>> excelVerify(List<ParameterDTO> parameterDtoList, UserDTO user) {

		List<Map<String, Object>> result = new ArrayList<>();
		
		
		for(ParameterDTO parameterDto : parameterDtoList) {
			
			Map<String,String> tableNameMap = new HashMap<>();
			List<TableDTO> tableDtoList = parameterDto.getTableDTO();
			
			for(TableDTO tableDto : tableDtoList) {
				if(!tableNameMap.containsKey(tableDto.getTableName())) {
					tableNameMap.put(tableDto.getDbTableName() + ":" + tableDto.getPhysicalName(), tableDto.getSchema());
				}
			}
			
			List<TableDTO> dbTableDtoList = new ArrayList<>();
			
			for(String tableName : tableNameMap.keySet()) {
				
				TableDTO dto = new TableDTO.TableBuilder()
						.schema(tableNameMap.get(tableName))
						.dbTableName(tableName.split(":")[0])
						.physicalName(tableName.split(":")[1])
						.build();
				
				dbTableDtoList.addAll(getDBTable(dto, user));
			}
			
			ParameterDTO dbParameterDto = new ParameterDTO();
			dbParameterDto.setName("db");
			dbParameterDto.setTableDTO(dbTableDtoList);
			
			result.add(verifyTables(parameterDto, dbParameterDto));
			
		}
		
		return result;
		
	}

}
