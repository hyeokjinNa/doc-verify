package com.shy.docverify.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.config.ExtractType;
import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableNameDTO;
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.service.VerifyService;

@Service
public class ExcelUtil {

	@Autowired
	private VerifyService service;

	public List<ParameterDTO> excelFileRead(List<File> files, UserDTO user) {
		List<ParameterDTO> parameterDtoList = new ArrayList<ParameterDTO>();

		for (File file : files) {
			ParameterDTO parameterDto = new ParameterDTO();
			parameterDto.setName(file.getName());
			parameterDto.setTableDTO(extractXlsxFile(file, user));
			parameterDtoList.add(parameterDto);
		}
		return parameterDtoList;
	}// excelFileRead end

	public List<TableDTO> extractXlsxFile(File file, UserDTO user) {
		List<TableDTO> tableList = new ArrayList<TableDTO>();
		List<TableDTO> asisTableList = new ArrayList<TableDTO>();
		TreeSet<String> asisTableName = new TreeSet<String>();

		try {

			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFCell cell = null;
			XSSFRow row = null;

			List<Integer> sheetNumbers = getSheetNumbers(workbook);
			
			for(int sheetNumber : sheetNumbers) {
				
				XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
				int valueNum = 0;
				String toBeSchema = "";
				String[] delimeter = null;
	
				int rows = sheet.getPhysicalNumberOfRows();
				int cells = sheet.getRow(sheetNumber).getPhysicalNumberOfCells();
				
				boolean flag = true;
				for (int r = 2; r < rows; r++) {
					
					row = sheet.getRow(r);
	
					if (row != null) {
						TableDTO toBeTable = new TableDTO();
						TableDTO asIsTable = new TableDTO();
						for (int c = 1; c < cells; c++) {
							cell = row.getCell(c);
	
							if (cell != null) {
								
								if (c == 9 || c > 17) {
									continue;
								}
								
								String value = "";
								
								value = getCellValue(cell);
								
								if (c > 9) {
									valueNum = c - 2;
								} else {
									valueNum = c - 1;
								}
								
								switch (ExtractType.values()[valueNum]) {
								case TOBE_TABLENAME:
									toBeTable.setTableName(value);
									toBeTable.setDbTableName("C_" + value + "_VA");
									toBeSchema = "TOBE" + value.substring(0, 4);
									toBeTable.setSchema(toBeSchema);
									break;
								case TOBE_ENTITYNAME:
									toBeTable.setEntityName(value);
									break;
								case TOBE_LOGICALNAME:
									toBeTable.setLogicalName(value);
									break;
								case TOBE_PHYSICALNAME:
									toBeTable.setPhysicalName("PV_" + value);
									break;
								case TOBE_DATATYPE:
									toBeTable.setDataType(value);
									break;
								case TOBE_LENGTH:
									if (value.contains(",")) {
										delimeter = value.split(",");
										toBeTable.setPrecision(delimeter[0].trim());
										toBeTable.setScale(delimeter[1].trim());
										toBeTable.setLength("22");
										delimeter = null;
									} else {
										toBeTable.setLength(value);
										toBeTable.setPrecision("0");
										toBeTable.setScale("0");
									}
									break;
								case TOBE_NOTNULL:
									if (!value.equals("")) {
										toBeTable.setNotNull("Y");
									} else {
										toBeTable.setNotNull(null);
									}
									break;
								case TOBE_PK:
									if (!value.equals("")) {
										toBeTable.setPk(value);
									} else {
										toBeTable.setPk(null);
									}
									break;
								case ASIS_TABLENAME:
									asIsTable.setSchema("GMDMI");
									asisTableName.add(value);
									asIsTable.setTableName(value);
									break;
								case ASIS_ENTITYNAME:
									asIsTable.setEntityName(value);
									break;
								case ASIS_LOGICALNAME:
									asIsTable.setLogicalName(value);
									break;
								case ASIS_PHYSICALNAME:
									asIsTable.setPhysicalName(value);
									break;
								case ASIS_DATATYPE:
									asIsTable.setDataType(value);
									break;
								case ASIS_LENGTH:
									if (value.contains(",")) {
										delimeter = value.split(",");
										asIsTable.setPrecision(delimeter[0].trim());
										asIsTable.setScale(delimeter[1].trim());
										asIsTable.setLength("22");
										delimeter = null;
									} else {
										asIsTable.setLength(value);
										asIsTable.setPrecision("0");
										asIsTable.setScale("0");
									}
									break;
								case ASIS_NOTNULL:
									if (!value.equals("")) {
										asIsTable.setNotNull("Y");
									} else {
										asIsTable.setNotNull(null);
									}
									break;
								case ASIS_PK:
									if (!value.equals("")) {
										asIsTable.setPk(value);
									} else {
										asIsTable.setPk(null);
									}
									break;
	
								}// switch end
	
							}else {
								flag = false;
								break;
							}// if cell end
							
						} // for cells end
						if(!flag) {
							break;
						}
						tableList.add(toBeTable);
						asisTableList.add(asIsTable);
	
					} // if row null end
				} // for rows end
			}//for sheetNumber end
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		asisTableList = setAsisTableName(asisTableList, asisTableName, user);

		for (TableDTO dto : asisTableList) {
			tableList.add(dto);
		}

		return tableList;

	}// extractXlsxFile end

	public List<TableDTO> setAsisTableName(List<TableDTO> asisTableList, TreeSet<String> asisTableName, UserDTO user) {

		List<TableNameDTO> tableNameList = service.getTableName(asisTableName, user);

		for (int i = 0; i < asisTableList.size(); i++) {
			for (int j = 0; j < tableNameList.size(); j++) {
				if (asisTableList.get(i).getTableName().equals(tableNameList.get(j).getDocTableName())) {
					asisTableList.get(i).setDbTableName(tableNameList.get(j).getDbTableName());
					break;
				} // if end
			} // for tableNameList end
		} // for asisTableList end

		return asisTableList;

	}// setAsisTableName
	
	public List<Integer> getSheetNumbers(XSSFWorkbook workbook) {
		
		List<Integer> sheetNumbers = new ArrayList<Integer>();
		int sheetCn = workbook.getNumberOfSheets();
		XSSFRow row = null;
		XSSFCell cell = null;
		
		for(int i=0; i<sheetCn; i++) {
			
			XSSFSheet sheet = workbook.getSheetAt(i);
			
			row = sheet.getRow(0);
			cell = row.getCell(0);
			String value = "";
			if(cell != null) {
				value = getCellValue(cell);
				
				if(value.equals("Target")) {
					 sheetNumbers.add(i);
				 }
			}
			 
			 
		}
		return sheetNumbers;
	}
	
	public String getCellValue(XSSFCell cell) {
		String value = "";
		
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			value = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
			break;
		case STRING:
			value = cell.getStringCellValue();
			break;
		case FORMULA:
			value = cell.getStringCellValue();
			break;
		case BLANK:
			break;
		case BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		}
		
		return value;
	}

}
