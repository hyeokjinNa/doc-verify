package com.shy.docverify.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.shy.docverify.config.ExtractType;
import com.shy.docverify.dto.TableDTO;

@Service
public class ExcelUtil {

	public String excelFileRead(List<File> files) {
		
		for(File file:files) {
			if(file.getAbsolutePath().endsWith("xlsx")) {
				extractXlsxFile(file);
			}else if(file.getAbsolutePath().endsWith("xls")) {
				extractXlsFile(file);
			}
			
			
			
		}
		return null;
	}//excelFileRead end
	
	public void extractXlsxFile(File file) {
		try {
			
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			List<TableDTO> tableList = new ArrayList<TableDTO>();
			
			XSSFSheet sheet = workbook.getSheetAt(3);
			XSSFCell cell = null;
			int valueNum = 0;
			String toBeSchema="";
			String[] delimeter= null;
			
			int rows = sheet.getPhysicalNumberOfRows();				
			int cells = sheet.getRow(0).getPhysicalNumberOfCells(); 
			
			for(int r = 2; r<rows; r++) {
				XSSFRow row = sheet.getRow(r);
				
				if(row != null) {
					TableDTO toBeTable = new TableDTO();
					TableDTO asIsTable = new TableDTO();
					for(int c = 1; c<cells; c++) {
						cell = row.getCell(c);
						
						if(cell != null) {
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
							
							if(c==9 || c>16) {
								continue;
							}
							if(c>9) {
								valueNum = c-2;
							}else {
								valueNum = c-1;
							}
							//asis 확인부터
							switch(ExtractType.values()[valueNum]) {
							case TOBE_TABLENAME:
								toBeTable.setTableName("C_"+value+"_VA");
								toBeSchema="TOBE"+value.substring(0,4);
								toBeTable.setSchema(toBeSchema);
								break;
							case TOBE_ENTITYNAME:
								toBeTable.setEntityName(value);
							case TOBE_LOGICALNAME:
								toBeTable.setLogicalName(value);
								break;
							case TOBE_PHYSICALNAME:
								toBeTable.setPhysicalName("PV_"+value);
								break;
							case TOBE_DATATYPE:
								toBeTable.setDataType(value);
								break;
							case TOBE_LENGTH:
								if(value.contains(",")) {
									delimeter=value.split(",");
									toBeTable.setPrecision(delimeter[0]);
									toBeTable.setScale(delimeter[1]);
									toBeTable.setLength("22");
									delimeter=null;
								}else {
									
									toBeTable.setLength(value);
								}
								break;
							case TOBE_NOTNULL:
								if(!value.equals("")) {
									toBeTable.setNotNull("Y");
								}else {
									toBeTable.setNotNull("N");
								}
								break;
							case TOBE_PK:
								if(!value.equals("")) {
									toBeTable.setPk(value);
								}else {
									toBeTable.setPk("N");
								}
								break;
							case ASIS_TABLENAME:
								asIsTable.setSchema("GMDMI");
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
								if(value.contains(",")) {
									delimeter=value.split(",");
									asIsTable.setPrecision(delimeter[0]);
									asIsTable.setScale(delimeter[1]);
									asIsTable.setLength("22");
									delimeter=null;
								}else {
									asIsTable.setLength(value);
								}
								break;
							case ASIS_NOTNULL:
								if(!value.equals("")) {
									asIsTable.setNotNull("Y");
								}else {
									asIsTable.setNotNull("N");
								}
								break;
							case ASIS_PK:
								if(!value.equals("")) {
									asIsTable.setPk(value);
								}else {
									asIsTable.setPk("N");
								}
								break;
							
							}//switch end
							
						}//if cell end
						
					}//for cells end
					tableList.add(toBeTable);
					tableList.add(asIsTable);
					
				}//if row null end
				
			}//for rows end
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}//extractXlsxFile end
	
	
	public void extractXlsFile(File file) {
		HSSFWorkbook hWorkbook = null;
		try {
			
			FileInputStream fis = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			
			int rowindex = 0;
			int columnindex = 0;

			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}//extractXlsFile end
	
}
