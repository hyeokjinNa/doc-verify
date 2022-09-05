package com.shy.docverify.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@PostMapping("/ajax/uploadExcel")
	public String uploadExcel(List<MultipartFile> files) {
		
		for(MultipartFile file:files) {
			//메서드 추가(MultipartFile -> file -> inputStream)
		}
		
		return null;
	}
	
}
