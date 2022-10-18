package com.shy.docverify.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.shy.docverify.dao.DBInfoSql;
import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableDTO.TableBuilder;
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.service.VerifyServiceImpl;
import com.shy.docverify.util.ConvertUtil;
import com.shy.docverify.util.ExcelUtil;

@Controller
public class MainController {
	
	@Autowired
	private ConvertUtil converFile;
	
	@Autowired
	private ExcelUtil excelUtil;
	
	@Autowired
	private DBInfoSql dbInfo;
	
	@Autowired
	private VerifyServiceImpl verifyService;

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@PostMapping("/excelRegister")
	public String uploadExcel(List<MultipartFile> mfiles, UserDTO user) {
		List<File> files = new ArrayList<File>();
		
		for(MultipartFile mfile:mfiles) {
			File file = converFile.multipartToFile(mfile);
			files.add(file);
		}
		List<ParameterDTO> parameterDtoList = excelUtil.excelFileRead(files, user);
		
		List<Map<String, Object>> data = verifyService.excelVerify(parameterDtoList, user);
		
		return null;
	}
	
	@PostMapping("/dbLogin")
	public String dbLogin(UserDTO user, HttpSession session, RedirectAttributes ra) {
		Boolean check = dbInfo.connectionTest(user);
		if(check == true) {
			session.setAttribute("loginMember", user);
		}else {
			ra.addFlashAttribute("msg","DB 세팅 정보가 맞지 않습니다");
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping("/result")
	public String result(Model model) {
		List<Map<String, Object>> data = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<>();
		
		TableDTO table = new TableBuilder()
				.schema("GMDMI")
				.tableName("TB_BL01I_001_MAST")
				.entityName("건축물대장")
				.physicalName("IEM_CODE")
				.logicalName("코드명")
				.dataType("VARCHAR")
				.length("20")
				.pk("N")
				.notNull("N")
				.match(true)
				.check(true)
				.build();
		
		TableDTO table2 = new TableBuilder()
				.schema("GMDMI")
				.tableName("TB_BL01I_002")
				.entityName("폐쇄말소대장")
				.physicalName("IEM_CODE")
				.logicalName("코드명")
				.dataType("NUMBER")
				.precision("20")
				.scale("1")
				.pk("Y")
				.notNull("Y")
				.match(false)
				.check(true)
				.wrongColumns(Arrays.asList("logicalName"))
				.build();
		
		
		TableDTO docTable = new TableBuilder()
				.schema("GMDMI")
				.tableName("TB_BL01I_001_MAST")
				.entityName("건축물대장")
				.physicalName("IEM_CODE")
				.logicalName("코드명")
				.dataType("VARCHAR")
				.length("20")
				.pk("N")
				.notNull("N")
				.match(true)
				.check(true)
				.build();
		
		TableDTO docTable2 = new TableBuilder()
				.schema("GMDMI")
				.tableName("TB_BL01I_002")
				.entityName("폐쇄말소대장")
				.physicalName("EEEE")
				.logicalName("이름")
				.dataType("VARCHAR")
				.length("100")
				.pk("Y")
				.notNull("Y")
				.match(false)
				.check(false)
				.build();
		
		TableDTO docTable3 = new TableBuilder()
				.schema("GMDMI")
				.tableName("TB_BL01I_002")
				.entityName("폐쇄말소대장")
				.physicalName("IEM_CODE")
				.logicalName("코드이름")
				.dataType("NUMBER")
				.precision("20")
				.scale("1")
				.pk("N")
				.notNull("Y")
				.match(false)
				.check(true)
				.wrongColumns(Arrays.asList("logicalName", "pk"))
				.build();
		
		
		map.put("excelName", "테이블 정의서");
		map.put("db", Arrays.asList(table, table2));
		map.put("doc", Arrays.asList(docTable, docTable2));
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("excelName", "컬럼 정의서");
		map2.put("db", Arrays.asList(table, table2));
		map2.put("doc", Arrays.asList(docTable, docTable2, docTable3));
		
		data.add(map);
		data.add(map2);
		
		model.addAttribute("dataJson", new Gson().toJson(data));
		model.addAttribute("data", data);
		return "resultPage";
	}
}
