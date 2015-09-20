package com.myplanet.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		// 네이버
		paramMap.put("url", "http://www.naver.com/");
		List rNaverRankList = mainSvc.findNaverRealRankList(paramMap);
		
		// 다음
		paramMap.put("url", "http://www.daum.net/");
		List rDaumRankList = mainSvc.findDaumRealRankList(paramMap);
		
		System.out.println(rDaumRankList);
		
		return "/main/index";
	}
	
	/**
	 * @Desc	: 실시간 급상승 데이터 조회 (ajax)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 19일 
	 * @stereotype Action
	 */
	@ResponseBody
	@RequestMapping(value = "/findRealRankList", method = RequestMethod.POST)
	public Map findNaverRealRankList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);
		
		Map mRtnData = new HashMap<String, List>();
		
		// 네이버
		paramMap.put("url", "http://www.naver.com/");
		mRtnData.put("naver", mainSvc.findNaverRealRankList(paramMap));
		
		// 다음카카오
		paramMap.put("url", "http://www.daum.net/");
		mRtnData.put("daum", mainSvc.findDaumRealRankList(paramMap));
		
		return mRtnData;
	}
}