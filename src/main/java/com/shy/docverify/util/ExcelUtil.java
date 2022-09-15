package com.shy.docverify.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelUtil {

	public String excelFileRead(List<File> files) {
		
		for(File file:files) {
			extractExcelFile(file);
			
			
		}
		return null;
	}
	
	public void extractExcelFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			System.out.println();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
