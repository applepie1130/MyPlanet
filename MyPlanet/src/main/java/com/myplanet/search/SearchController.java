package com.myplanet.search;

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
import com.myplanet.comm.RequestUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SearchController extends CommonController {

	/*
	 * Define Service Variables
	 */
	@Autowired (required=false)
	private SearchService searchSvc;

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	/**
	 * @Desc	: 검색페이지
	 * @Author	: 김성준
	 * @Create	: 2015년 10월 31일
	 * @stereotype Action
	 */
	@RequestMapping("/search")
	public String findMainPage(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);
		
		// 선택 SNB 메뉴 셋팅
		Map<String, String> mSnbMenuInfo = new HashMap<String, String>();
		mSnbMenuInfo.put("menu2", "selected");
		
		model.addAttribute("mSnbMenuInfo", mSnbMenuInfo);

		return "/search/search";
	}

	/**
	 * @Desc	: 검색 데이터 조회 (ajax)
	 * @Author	: 김성준
	 * @Create	: 2015년 10월 31일
	 * @stereotype Action
	 */
	@ResponseBody
	@RequestMapping(value = "/search/findSearchData", method = RequestMethod.POST)
	public Map findSearchData(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);

		Map mRtnData = new HashMap<String, List>();

		mRtnData = searchSvc.findSearchData(paramMap);

		return mRtnData;
	}
}