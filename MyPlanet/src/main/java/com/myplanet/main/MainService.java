package com.myplanet.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.myplanet.comm.CommonService;
import com.myplanet.comm.FileService;
import com.myplanet.test.TestService;

@Service("mainSvc")
@Transactional
public class MainService extends CommonService {
	
	@Autowired
	TestService testSvc;
	
	@Autowired
	FileService fileSvc;
	
	/**
	 * @Desc	: 실시간 급상승 데이터 조회 (네이버)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 19일 
	 * @stereotype ServiceMethod
	 */
	public List findNaverRealRankList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 실시간 급상승 데이터 조회 (다음)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 19일 
	 * @stereotype ServiceMethod
	 */
	public List findDaumRealRankList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 갓피플 오늘의 말씀
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 20일 
	 * @stereotype ServiceMethod
	 */
	public Map findDailyQTData(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONMap(sFilePath);
	}
	
	/**
	 * @Desc	: 네이트 랭킹 뉴스
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 20일 
	 * @stereotype ServiceMethod
	 */
	public List findNateRealRankList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 네이버 금융(증권정보)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 22일 
	 * @stereotype ServiceMethod
	 */
	public List findNaverFinanceList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 네이버 트랜드 랭킹 조회 (RESTful : JSON)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 26일 
	 * @stereotype ServiceMethod
	 */
	public List findNaverTrendRankJSONList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 네이버 금융(증권정보 : 상한가 종목)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 27일 
	 * @stereotype ServiceMethod
	 */
	public List findNaverFinanceUpperList(Map paramMap) {
		String sFilePath = (String) paramMap.get("filePath");
		
		return fileSvc.readFileFromJSONList(sFilePath);
	}
	
	/**
	 * @Desc	: 조회 테스트
	 * @Author	: 김성준
	 * @Create	: 2015년 11월 28일 
	 * @stereotype ServiceMethod
	 */
	public List findTestList(Map paramMap) {
		List resultData = queryForListData("mainQry.selectTestList", paramMap);
		return resultData;
	}	
}
