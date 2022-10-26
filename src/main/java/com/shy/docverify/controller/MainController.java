package com.shy.docverify.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.Gson;
import com.shy.docverify.dao.DBInfoSql;
import com.shy.docverify.dto.ParameterDTO;
import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.TableDTO.TableBuilder;
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.service.FileStorageService;
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
	
	 @Autowired
	 FileStorageService storageService;

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@PostMapping("/excelRegister")
	public String uploadExcel(List<MultipartFile> mfiles, HttpSession session, RedirectAttributes ra) {
		List<File> files = new ArrayList<File>();
		
		for(MultipartFile mfile:mfiles) {
			File file = converFile.multipartToFile(mfile);
			files.add(file);
		}
		
		UserDTO user = (UserDTO) session.getAttribute("loginMember");
		
		List<ParameterDTO> parameterDtoList = excelUtil.excelFileRead(files, user);
		
		List<Map<String, Object>> data = verifyService.excelVerify(parameterDtoList, user);
		
		ra.addFlashAttribute("data", data);
		
		return "redirect:/result";
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
	public String result(HttpServletRequest request, Model model) {
		
		Map<String, ?> dataMap = RequestContextUtils.getInputFlashMap(request);
		
		if(dataMap != null) {
			List<Map<String, Object>> data = (List<Map<String, Object>>)dataMap.get("data");
			model.addAttribute("dataJson", new Gson().toJson(data));
			model.addAttribute("data", data);
		}
		
		return "resultPage";
	}
	
	@GetMapping("/fileDownload")
	public void docDownload(String fileName, HttpServletResponse response) {
		byte[] downloadFile = storageService.selectOneFile(fileName);
		
		try {
			
			response.setContentType("application/octet-stream");
			response.setContentLength(downloadFile.length);
			
			response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(fileName, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(downloadFile);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
