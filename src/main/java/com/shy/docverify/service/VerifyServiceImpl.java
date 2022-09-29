package com.shy.docverify.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.dto.ParameterDTO;
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
	
	@Override
	public Map<String, Object> verifyTables(ParameterDTO firstParam, ParameterDTO secondParam) {

		String[] tableKeys = {"TableName", "PhysicalName", "EntityName", "LogicalName", "DataType", "Length",
				"Precision", "Scale", "NotNull", "Pk" };

		List<TableDTO> firstTable = firstParam.getTableDTO();
		List<TableDTO> secondTable = secondParam.getTableDTO();
		
		Map<String, Object> result = new HashMap();

		for (int i = 0; i < firstTable.size(); i++) {

			List<String> firstRow = setRowList(firstTable.get(i));

			boolean check = false;
			
			for (int j = 0; j < secondTable.size(); j++ ) {

				List<String> secondRow = setRowList(secondTable.get(j));

				if (StringUtils.equals(firstRow.get(0), secondRow.get(0))
						&& StringUtils.equals(firstRow.get(1), secondRow.get(1))) {
					
					check = true;
					boolean match = true;
					
					firstTable.get(i).setCheck(check);
					secondTable.get(j).setCheck(check);

					for (int col = 2; col < firstRow.size(); col++) {
						
						if (!StringUtils.equals(firstRow.get(col), secondRow.get(col))) {
							
							List<String> wrongColumns = firstTable.get(i).getWrongColumns();
							match = false;

							wrongColumns.add(tableKeys[i]);
							
							firstTable.get(i).setWrongColumns(wrongColumns);
							secondTable.get(j).setWrongColumns(wrongColumns);
						}
						
					}
					
					firstTable.get(i).setMatch(match);
					secondTable.get(j).setMatch(match);
					
					break;
				}

			}
			
		}
		
		result.put(firstParam.getName(), firstTable);
		result.put(secondParam.getName(), secondTable);

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

		return list;
	}

}
