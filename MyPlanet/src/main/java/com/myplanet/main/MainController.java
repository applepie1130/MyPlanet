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

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Desc	: 메인페이지
	 * @Author	: 김성준
	 * @Create	: 2015년 08월 02일
	 * @stereotype Action
	 */
	@RequestMapping("/")
	public String findMainPage(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		paramMap = RequestUtil.getParameter(paramMap, request, response);
		
		// 랭킹순위제한
		paramMap.put("ranknum", 10);

		Map mRtnData	= new HashMap<String, Object>();

		// 네이버
		paramMap.put("filePath", "/tank0/batch/NaverRealRankList.json");
		List lsNaverRankList = mainSvc.findNaverRealRankList(paramMap);

		// 다음
		paramMap.put("filePath", "/tank0/batch/DaumRealRankList.json");
		List lsDaumRankList = mainSvc.findDaumRealRankList(paramMap);

		// 갓피플 오늘의 말씀
		paramMap.put("filePath", "/tank0/batch/DailyQTMap.json");
		Map mRtnDailyQTData = mainSvc.findDailyQTData(paramMap);

		// 네이트 랭킹뉴스(시사)
		paramMap.put("filePath", "/tank0/batch/NateRealRankNewsList_1.json");
		List lsSisaRankList = mainSvc.findNateRealRankList(paramMap);

		// 네이트 랭킹뉴스(연예)
		paramMap.put("filePath", "/tank0/batch/NateRealRankNewsList_2.json");
		List lsEntRankList = mainSvc.findNateRealRankList(paramMap);

		// 네이트 랭킹뉴스(스포츠)
		paramMap.put("filePath", "/tank0/batch/NateRealRankNewsList_3.json");
		List lsSpoRankList = mainSvc.findNateRealRankList(paramMap);

		// 네이버 증권정보
		paramMap.put("filePath", "/tank0/batch/NaverFinanceList.json");
		List lsFinanceList = mainSvc.findNaverFinanceList(paramMap);
		
		// 네이버 트렌드 랭킹
		paramMap.put("filePath", "/tank0/batch/NaverTrendRankList.json");
		List lsTrendRankList = mainSvc.findNaverTrendRankJSONList(paramMap);

		// 네이버 증권정보(상한가 종목)
		paramMap.put("filePath", "/tank0/batch/NaverFinanceUpperList_KOSPI.json");
		List lsKospiUpperList = mainSvc.findNaverFinanceUpperList(paramMap);

		paramMap.put("filePath", "/tank0/batch/NaverFinanceUpperList_KOSDAQ.json");
		List lsKosdaqUpperList = mainSvc.findNaverFinanceUpperList(paramMap);
		
		// 선택 SNB 메뉴 셋팅
		Map<String, String> mSnbMenuInfo = new HashMap<String, String>();
		mSnbMenuInfo.put("menu1", "selected");
		
		// set response data
		model.addAttribute("lsNaverRankList", lsNaverRankList);
		model.addAttribute("lsDaumRankList", lsDaumRankList);
		model.addAttribute("mRtnDailyQTData", mRtnDailyQTData);
		model.addAttribute("lsSisaRankList", lsSisaRankList);
		model.addAttribute("lsEntRankList", lsEntRankList);
		model.addAttribute("lsSpoRankList", lsSpoRankList);
		model.addAttribute("lsFinanceList", lsFinanceList);
		model.addAttribute("lsTrendRankList", lsTrendRankList);
		model.addAttribute("lsKospiUpperList", lsKospiUpperList);
		model.addAttribute("lsKosdaqUpperList", lsKosdaqUpperList);
		model.addAttribute("mSnbMenuInfo", mSnbMenuInfo);
		
		// [S] 쿼리 조회 테스트
//		List findTestList = mainSvc.findTestList(paramMap);
//		User user = (User) findTestList.get(0);
//		logger.info("쿼리정보 : {}", findTestList);
		// [E] 쿼리 조회 테스트

		return "/main/main";
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
		paramMap.put("filePath", "/tank0/batch/NaverRealRankList.json");
		mRtnData.put("naver", mainSvc.findNaverRealRankList(paramMap));

		// 다음카카오
		paramMap.put("filePath", "/tank0/batch/DaumRealRankList.json");
		mRtnData.put("daum", mainSvc.findDaumRealRankList(paramMap));

		return mRtnData;
	}
}