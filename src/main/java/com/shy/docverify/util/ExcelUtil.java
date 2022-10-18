package com.shy.docverify.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
		ParameterDTO parameterDto = new ParameterDTO();

		for (File file : files) {
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

			XSSFSheet sheet = workbook.getSheetAt(3);
			XSSFCell cell = null;
			int valueNum = 0;
			String toBeSchema = "";
			String[] delimeter = null;

			int rows = sheet.getPhysicalNumberOfRows();
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();

			for (int r = 2; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);

				if (row != null) {
					TableDTO toBeTable = new TableDTO();
					TableDTO asIsTable = new TableDTO();
					for (int c = 1; c < cells; c++) {
						cell = row.getCell(c);

						if (cell != null) {
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

							if (c == 9 || c > 16) {
								continue;
							}
							if (c > 9) {
								valueNum = c - 2;
							} else {
								valueNum = c - 1;
							}
							// asis 확인부터
							switch (ExtractType.values()[valueNum]) {
							case TOBE_TABLENAME:
								toBeTable.setTableName("C_" + value + "_VA");
								toBeSchema = "TOBE" + value.substring(0, 4);
								toBeTable.setSchema(toBeSchema);
								break;
							case TOBE_ENTITYNAME:
								toBeTable.setEntityName(value);
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
									toBeTable.setPrecision(delimeter[0]);
									toBeTable.setScale(delimeter[1]);
									toBeTable.setLength("22");
									delimeter = null;
								} else {

									toBeTable.setLength(value);
								}
								break;
							case TOBE_NOTNULL:
								if (!value.equals("")) {
									toBeTable.setNotNull("Y");
								} else {
									toBeTable.setNotNull("N");
								}
								break;
							case TOBE_PK:
								if (!value.equals("")) {
									toBeTable.setPk(value);
								} else {
									toBeTable.setPk("N");
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
									asIsTable.setPrecision(delimeter[0]);
									asIsTable.setScale(delimeter[1]);
									asIsTable.setLength("22");
									delimeter = null;
								} else {
									asIsTable.setLength(value);
								}
								break;
							case ASIS_NOTNULL:
								if (!value.equals("")) {
									asIsTable.setNotNull("Y");
								} else {
									asIsTable.setNotNull("N");
								}
								break;
							case ASIS_PK:
								if (!value.equals("")) {
									asIsTable.setPk(value);
								} else {
									asIsTable.setPk("N");
								}
								break;

							}// switch end

						} // if cell end

					} // for cells end
					tableList.add(toBeTable);
					asisTableList.add(asIsTable);

				} // if row null end

			} // for rows end

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

		List<TableNameDTO> tableNameList = service.getTableNmae(asisTableName, user);

		for (int i = 0; i < asisTableList.size(); i++) {
			for (int j = 0; j < tableNameList.size(); j++) {
				if (asisTableList.get(i).getTableName().equals(tableNameList.get(j).getDocTableName())) {
					asisTableList.get(i).setTableName(tableNameList.get(j).getDbTableName());
					break;
				} // if end
			} // for tableNameList end
		} // for asisTableList end

		return asisTableList;

	}// setAsisTableName

}
