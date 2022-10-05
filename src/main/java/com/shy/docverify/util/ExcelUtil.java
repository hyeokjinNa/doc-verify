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
			
			int rowindex = 0;
			int columnindex = 0;
			
			XSSFSheet sheet = workbook.getSheetAt(3);
			XSSFCell cell = null;
			
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
							
							switch(c) {
							case 1:
								toBeTable.setTableName("C_"+value+"_VA");
								break;
							case 2:
								toBeTable.setEntityName(value);
							case 3:
								toBeTable.setLogicalName(value);
								break;
							case 4:
								toBeTable.setPhysicalName(value);
								break;
							case 5:
								toBeTable.setDataType(value);
								break;
							case 6:
								toBeTable.setLength(value);
								break;
							case 7:
								toBeTable.setNotNull(value);
								break;
							case 8:
								if(!value.equals("")) {
									toBeTable.setPk(value);
								}else {
									toBeTable.setPk("N");
								}
								break;
							case 9:
								asIsTable.setTableName(value);
								break;
							case 10:
								asIsTable.setEntityName(value);
								break;
							case 11:
								asIsTable.setLogicalName(value);
								break;
							case 12:
								asIsTable.setPhysicalName(value);
								break;
							case 13:
								asIsTable.setDataType(value);
								break;
							case 14:
								asIsTable.setLength(value);
								break;
							case 15:
								asIsTable.setLength(value);
								break;
							case 16:
								asIsTable.setNotNull(value);
								break;
							case 17:
								if(!value.equals("")) {
									asIsTable.setPk(value);
								}else {
									asIsTable.setPk("N");
								}
								break;
							case 18:
								break;
							}
							
						}
						
						
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
