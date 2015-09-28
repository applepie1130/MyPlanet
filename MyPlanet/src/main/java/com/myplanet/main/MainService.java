package com.myplanet.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.myplanet.comm.CommonService;

@Service
@Transactional
public class MainService extends CommonService {
	
	/**
	 * @Desc	: 실시간 급상승 데이터 조회 (네이버)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 19일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public List findNaverRealRankList(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		
		List lsRtnData	= new ArrayList();
		
		try {
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = "#realrank li a";
			Elements rcw = doc.select(sRealRankSelector);
			
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
	        
	        // 실시간검색어 Parsing
	        for (Element el : rcw) {
	        	Map mData = new HashMap();
	        	
	        	mData.put("rank", el.parent().attr("value"));
	        	mData.put("title", el.attr("title"));
	        	mData.put("link", el.attr("href"));
	        	mData.put("searchTime", now);
	        	
	        	String sId = el.parent().attr("id");
	        	if ( !"lastrank".equals(sId) ) {
	        		lsRtnData.add(mData);
	        	}
	        	
	        	mData = null;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsRtnData;
	}
	
	/**
	 * @Desc	: 실시간 급상승 데이터 조회 (다음)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 19일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public List findDaumRealRankList(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		List lsRtnData = new ArrayList();
		
		try {
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = "#realTimeSearchWord li div div .txt_issue a";
			Elements rcw = doc.select(sRealRankSelector);
			
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
	        
	        // 실시간검색어 Parsing
	        int nChk = 1, nRank = 1;
	        for (Element el : rcw) {
	        	nChk++;
	        	
	        	if ( nChk % 2 == 0 ) {
	        		Map mData = new HashMap();
	        		mData.put("rank", nRank++);
		        	mData.put("title", el.after("strong").text());
		        	mData.put("link", el.attr("href"));
		        	mData.put("searchTime", now);
		        	
		        	lsRtnData.add(mData);
		        	
		        	mData = null;
	        	}
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsRtnData;
	}
	
	/**
	 * @Desc	: 갓피플 오늘의 말씀
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 20일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public Map findDailyQTData(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		
		Map mRtnData = new HashMap();
		
		try {
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = "div";
			Elements rcw = doc.select(sRealRankSelector);
			
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
	        mRtnData.put("searchTime", now);
	        
	        // CSS Parsing
	        int nChk = 1;
	        for (Element el : rcw) {
        		if ( nChk == 3 ) {
        			mRtnData.put("contents", el.after("div").text());
        		}
	        	nChk++;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mRtnData;
	}
	
	/**
	 * @Desc	: 네이트 랭킹 뉴스
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 20일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public List findNateRealRankList(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		int nRankNum = (Integer) paramMap.get("ranknum");
		
		List lsRtnData = new ArrayList();
		
		try {
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = ".rk_list li";
			Elements rcw = doc.select(sRealRankSelector);
			
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
	        
	        // CSS Parsing
	        int nChk = 0, nRank = 1;
	        for (Element el : rcw) {
	        	
	        	if ( nChk >= nRankNum ) {
	        		break;
	        	}
	        	
        		Map mData = new HashMap();
        		
        		mData.put("rank", nRank++);
	        	mData.put("title", el.after("span").text());
	        	mData.put("link", el.children().attr("href").replaceAll("m.news.nate.com", "news.nate.com").replaceAll("\\?.+", ""));
	        	mData.put("searchTime", now);
	        	
	        	lsRtnData.add(mData);
	        	
	        	mData = null;
	        	nChk++;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsRtnData;
	}
	
	/**
	 * @Desc	: 네이버 금융(증권정보)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 22일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public List findNaverFinanceList(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		
		List lsRtnData = new ArrayList();
		
		try {
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
			
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = ".index_lst a";
			Elements rcw = doc.select(sRealRankSelector);
			
			int num = 0;
			
	        // CSS Parsing
	        for (Element el : rcw) {
	        	num++;
	        	
        		Map mData = new HashMap();
        		
	        	mData.put("section", el.children().select("strong").get(0).text());
	        	mData.put("link", el.attr("href"));
	        	mData.put("amount", el.children().select("span").get(0).text());
	        	mData.put("point", el.children().select(".gap_price").text().replaceAll("[가-힣]+",""));
	        	mData.put("rate", el.children().select(".gap_rate").text());
	        	
	        	if ( num < 2 ) {
	        		mData.put("gaein", el.children().select("ul li").get(0).select("span").text());
	        		mData.put("forein", el.children().select("ul li").get(0).select("span").text());
	        		mData.put("gigan", el.children().select("ul li").get(0).select("span").text());
	        	}
	        	
	        	mData.put("searchTime", now);
	        	
	        	lsRtnData.add(mData);
	        	
	        	mData = null;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsRtnData;
	}
	
	/**
	 * @Desc	: 네이버 트랜드 랭킹 조회 (RESTful : JSON)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 26일 
	 * @stereotype ServiceMethod
	 */
	public List findNaverTrendRankJSONList() {
		
		List lsRtnData = new ArrayList<String>();
		List lsTrendURL = new ArrayList<String>();
		
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=search");		// 검색
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=news");		// 뉴스
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=company");	// 증권사
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=talk");		// 토론
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=blog");		// 블로그
		lsTrendURL.add("http://m.stock.naver.com/api/json/trend/getTrendList.nhn?type=cafe");		// 카페
		
		RestTemplate restTemplate = new RestTemplate();
		Iterator itr = lsTrendURL.iterator();
		
		while ( itr.hasNext() ) {
			lsRtnData.add(restTemplate.getForObject(ObjectUtils.toString(itr.next()), String.class));
		}
		
		return lsRtnData;
	}
	
	/**
	 * @Desc	: 네이버 금융(증권정보 : 상한가 종목)
	 * @Author	: 김성준
	 * @Create	: 2015년 09월 27일 
	 * @stereotype ServiceMethod
	 */
//	@Cacheable(value="default")
	public List findNaverFinanceUpperList(Map paramMap) {
		String sPageUrl = ObjectUtils.toString(paramMap.get("url"));
		String sType = ObjectUtils.toString(paramMap.get("type"));

		List lsRtnData = new ArrayList();
		
		// KOSPI, KOSDAQ 타입설정
		Map mTypeData = new HashMap<String, Integer>();
		mTypeData.put("KOSPI", 0);
		mTypeData.put("KOSDAQ", 1);
		
		try {
			// 검색시간
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis();
	        String now = sdf.format(new Date(nowmills));
			
			Document doc = Jsoup.connect(sPageUrl).get();
			
			// 검색구분자 CSS Selector
			String sRealRankSelector = ".box_type_l tbody";
				
	        // CSS Parsing
			int num = 0;
			int nEq = (Integer) mTypeData.get(sType.toUpperCase());
			Elements rcw = doc.select(sRealRankSelector).eq(nEq).select("tr");
			
	        for (Element el : rcw) {
        		Map mData = new HashMap();
        		
        		if ( !(num == 0 || num == 1 || (num == rcw.size() - 1) || (num == rcw.size() - 2)) ) {
        			mData.put("section", el.select("td").eq(3).select("a").text());			// 종목명
        			mData.put("amount", el.select("td").eq(4).text());						// 현재가
        			mData.put("diffpercent", el.select("td").eq(5).select("span").text());	// 전일비
        			mData.put("percent", el.select("td").eq(6).select("span").text());		// 등락률
        			mData.put("volume", el.select("td").eq(7).text());						// 거래량
        			mData.put("searchTime", now);
        			
        			lsRtnData.add(mData);
        		}
        		
        		mData = null;
	        	num++;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsRtnData;
	}
}
