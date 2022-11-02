package com.shy.docverify.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shy.docverify.dto.TableDTO;
import com.shy.docverify.dto.UserDTO;
import com.shy.docverify.service.VerifyService;

@RestController
public class ResultRestController {
	
	@Autowired
	private VerifyService verifyService;

	@PostMapping(value = "/checkTableList", produces = "application/json;charset=utf-8")
	public List checkTableList(String schema, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("loginMember");
		List<TableDTO> list = verifyService.checkTableList(schema, user);
		
		return list;
	}
}
