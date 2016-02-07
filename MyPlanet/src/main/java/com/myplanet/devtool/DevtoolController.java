package com.myplanet.devtool;

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
public class DevtoolController extends CommonController {

	/*
	 * Define Service Variables
	 */
	@Autowired (required=false)
	private DevtoolService devtoolSvc;

	private static final Logger logger = LoggerFactory.getLogger(DevtoolController.class);

	/**
	 * @Desc	: 코드생성기 페이지
	 * @Author	: 김성준
	 * @Create	: 2016년 02월 07일
	 * @stereotype Action
	 */
	@RequestMapping("/codegen")
	public String findMainPage(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);

		// 선택 SNB 메뉴 셋팅
		Map<String, String> mSnbMenuInfo = new HashMap<String, String>();
		mSnbMenuInfo.put("menu3", "selected");

		model.addAttribute("mSnbMenuInfo", mSnbMenuInfo);

		return "/devtool/codegen";
	}
}