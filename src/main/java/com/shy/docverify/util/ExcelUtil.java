package com.shy.docverify.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

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
			
			int rowindex = 0;
			int columnindex = 0;
			
			int sheetCount = workbook.getNumberOfSheets();
			
			for(int i = 0; i<sheetCount; i++) {
					
				XSSFSheet sheet = workbook.getSheetAt(i);
				
				int rows = sheet.getPhysicalNumberOfRows();				
				int cells = sheet.getRow(i).getPhysicalNumberOfCells(); 
				
				for(int r = 0; r<rows; r++) {
					
				}
				
			}
			
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
