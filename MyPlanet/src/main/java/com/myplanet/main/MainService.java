package com.myplanet.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		List rRtnData = new ArrayList();
		
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
	        		rRtnData.add(mData);
	        	}
	        	
	        	mData = null;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rRtnData;
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
		List rRtnData = new ArrayList();
		
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
		        	
		        	rRtnData.add(mData);
		        	
		        	mData = null;
	        	}
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rRtnData;
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
		
		List rRtnData = new ArrayList();
		
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
	        	
	        	rRtnData.add(mData);
	        	
	        	mData = null;
	        	nChk++;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rRtnData;
	}
}
