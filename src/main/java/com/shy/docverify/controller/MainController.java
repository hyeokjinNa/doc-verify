package com.shy.docverify.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.shy.docverify.util.ConvertFile;
import com.shy.docverify.util.ExcelUtil;

@Controller
public class MainController {
	
	@Autowired
	private ConvertFile converFile;
	
	@Autowired
	private ExcelUtil excelUtil;

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@PostMapping("/ajax/uploadExcel")
	public String uploadExcel(List<MultipartFile> mfiles) {
		List<File> files = new ArrayList<File>();
		
		for(MultipartFile mfile:mfiles) {
			//multipartToFile -> 메서드 에러부터
			File file = converFile.multipartToFile(mfile);
			files.add(file);
		}
		excelUtil.excelFileRead(files);
		
		return null;
	}
	
}
