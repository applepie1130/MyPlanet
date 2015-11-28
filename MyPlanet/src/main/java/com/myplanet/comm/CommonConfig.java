package com.myplanet.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Configuration
//@PropertySource("/WEB-INF/spring/common.properties")
@Component
public class CommonConfig {
	@Value("#{commonConf['service.shutDownStartDt']}")
	private String serviceShutDownStartDt;
	@Value("#{commonConf['service.shutDownEndDt']}")
	private String serviceShutDownEndDt;
	@Value("#{commonConf['server.ip']}")
	private String serverIp;
	@Value("#{commonConf['server.port']}")
	private String serverPort;
	@Value("#{commonConf['server.domain']}")
	private String serverDomain;
	
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}
	
	/**
	 * @return the serviceShutDownStartDt
	 */
	public String getServiceShutDownStartDt() {
		return serviceShutDownStartDt;
	}
	public String getServiceShutDownEndDt() {
		return serviceShutDownEndDt;
	}
	public String getServerIp() {
		return serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public String getServerDomain() {
		return serverDomain;
	}
}
