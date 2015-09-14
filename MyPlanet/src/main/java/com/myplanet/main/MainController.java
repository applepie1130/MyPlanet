package com.myplanet.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myplanet.comm.CommonController;
import com.myplanet.comm.FileService;
import com.myplanet.comm.RequestUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController extends CommonController {
	
	/*
	 * Define Service Variables
	 */
	@Autowired (required=false)
	private MainService mainSvc;
	
	@Autowired (required=false)
	private FileService fileSvc;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * @Desc	: 메인페이지
	 * @Author	: 김성준
	 * @Create	: 2015년 08월 02일 
	 * @stereotype Action
	 */
	@RequestMapping("/")
	public String findMainPage(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);
		
		Map mRtnData	= new HashMap<String, Object>();
		
		return "/main/index";
	}
}