package com.myplanet.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplanet.comm.CommonService;

@Service("searchSvc")
@Transactional
public class SearchService extends CommonService {

	/**
	 * @Desc	: 검색데이터 조회
	 * @Author	: 김성준
	 * @Create	: 2015년 10월 31일
	 * @stereotype ServiceMethod
	 */
	public Map findSearchData(Map paramMap) {
		String sUrl = ObjectUtils.toString(paramMap.get("url"));
		String sQry = ObjectUtils.toString(paramMap.get("qry"));
		String sSelector = ObjectUtils.toString(paramMap.get("selector"));
		String sEngine = ObjectUtils.toString(paramMap.get("engine"));
		String sSearchUrl = sUrl + sQry;

		String sHtmlData = null;

		try {
			Document doc = null;

			if ( "daum".equals(sEngine) ) {
				doc = Jsoup.connect(sSearchUrl)
						.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36")
						.get();
			} else if( "naver".equals(sEngine) )  {
				doc = Jsoup.connect(sSearchUrl).get();
			}

			// 검색구분자 CSS Selector
			Elements rcw = doc.select(sSelector);
			sHtmlData = rcw.toString();
//			System.out.println(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(sHtmlData);

		Map mHtmlData = new HashMap<String, String>();
		mHtmlData.put("htmlData", sHtmlData);

		return mHtmlData;
	}
}
