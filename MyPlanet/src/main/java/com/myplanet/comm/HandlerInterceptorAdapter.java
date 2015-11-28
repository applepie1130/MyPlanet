package com.myplanet.comm;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptorAdapter extends SuperDelegationAdapter implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);
	
	/**
	 * @Desc	: 전처리기
	 * @Author	: 김성준
	 * @Create	: 2015년 07월 04일 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Referer
		String sReferer = request.getHeader("REFERER");
		
		// SNS 로그인 확인 
		Map mCookieInfo = new HashMap<String, Object>();
		Map mRtnCookieInfo = new HashMap<String, Object>();
		
		mCookieInfo.put("cookieName", "SNS_SESSION");
		mRtnCookieInfo = RequestUtil.getCookie(mCookieInfo, request, response);
		
		if ( !StringUtil.isEmpty(ObjectUtils.toString(mRtnCookieInfo.get("SNS_SESSION"))) ) {
			GLIO.setSnsLoginStatus(true);
		} else {
			GLIO.setSnsLoginStatus(false);
		}
		
		// 모바일 기기 확인
		SitePreference currentSitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
		if(currentSitePreference.isMobile()){
			GLIO.setUserAgentMobileYn(true);
		} else {
			GLIO.setUserAgentMobileYn(false);
		}
		
		// 사용자 IP 확인
		GLIO.setUserIp(request.getHeader("X-FORWARDED-FOR"));
		if (GLIO.getUserIp() == null) {
			GLIO.setUserIp(request.getRemoteAddr());
		}
		
		// 즐겨찾기 쿠키 확인 
		mCookieInfo.put("cookieName", "MY_FAVORITE");
		mRtnCookieInfo = RequestUtil.getCookie(mCookieInfo, request, response);
		GLIO.setFavoriteCookieInfo(ObjectUtils.toString(mRtnCookieInfo.get("MY_FAVORITE")));
		
		// 현재시간구하기
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		StringBuffer sb = new StringBuffer();
		sb.append("현재 시간은 ").append(formattedDate);
		
		logger.info("==============PRE HANDLE==================");
		logger.info("Referer\t\t: {}", sReferer);
		logger.info("LoginSession\t: {}", mRtnCookieInfo.get("SNS_SESSION"));
		logger.info("Login ?\t\t: {}", GLIO.getSnsLoginStatus());
		logger.info("isMobile ?\t: {}", GLIO.getUserAgentMobileYn());
		logger.info("Client IP ?\t: {}", GLIO.getUserIp());
		logger.info("Favorite ?\t: {}", GLIO.getFavoriteCookieInfo());
		logger.info("Instance Info \t:{}", GLIO);
		logger.info("Current Time \t:{}", sb);
		logger.info("==============PRE HANDLE==================");
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	};
}