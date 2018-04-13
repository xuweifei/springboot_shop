package com.opengroup.longmao.gwcommon.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

	@Value("${auth.weibo.appId}")
	private String weiboAppId;

	@Value("${auth.weibo.appKey}")
	private String weiboAppKey;

	@Value("${auth.weibo.appSecret}")
	private String weiboAppSecret;

	@Value("${auth.weibo.ticketUrl}")
	private String weiboTicketUrl;

	@Value("${auth.weibo.tokenUrl}")
	private String weiboTokenUrl;

	@Value("${auth.weibo.showUrl}")
	private String weiboShowUrl;
	
	@Value("${auth.wechat.appId}")
	private String wechatAppId;

	@Value("${auth.wechat.appSecret}")
	private String wechatAppSecret;
	
	@Value("${auth.wechat.web.appId}")
	private String wechatWebAppId;

	@Value("${auth.wechat.web.appSecret}")
	private String wechatWebAppSecret;

	@Value("${auth.wechat.tokenUrl}")
	private String wechatTokenUrl;
	
	@Value("${auth.wechat.userUrl}")
	private String wechatUserUrl;
	
	@Value("${auth.qq.appId}")
	private String qqAppId;

	@Value("${auth.qq.appKey}")
	private String qqAppKey;
	
	@Value("${auth.qq.tokenUrl}")
	private String qqTokenUrl;
	
	@Value("${auth.qq.openidUrl}")
	private String qqOpenIdUrl;
	
	@Value("${auth.qq.userUrl}")
	private String qqUserUrl;

	public String getWeiboAppId() {
		return weiboAppId;
	}

	public void setWeiboAppId(String weiboAppId) {
		this.weiboAppId = weiboAppId;
	}

	public String getWeiboAppKey() {
		return weiboAppKey;
	}

	public void setWeiboAppKey(String weiboAppKey) {
		this.weiboAppKey = weiboAppKey;
	}

	public String getWeiboAppSecret() {
		return weiboAppSecret;
	}

	public void setWeiboAppSecret(String weiboAppSecret) {
		this.weiboAppSecret = weiboAppSecret;
	}

	public String getWeiboTicketUrl() {
		return weiboTicketUrl;
	}

	public void setWeiboTicketUrl(String weiboTicketUrl) {
		this.weiboTicketUrl = weiboTicketUrl;
	}

	public String getWeiboTokenUrl() {
		return weiboTokenUrl;
	}

	public void setWeiboTokenUrl(String weiboTokenUrl) {
		this.weiboTokenUrl = weiboTokenUrl;
	}

	public String getWeiboShowUrl() {
		return weiboShowUrl;
	}

	public void setWeiboShowUrl(String weiboShowUrl) {
		this.weiboShowUrl = weiboShowUrl;
	}

	public String getWechatAppId() {
		return wechatAppId;
	}

	public void setWechatAppId(String wechatAppId) {
		this.wechatAppId = wechatAppId;
	}

	public String getWechatAppSecret() {
		return wechatAppSecret;
	}

	public void setWechatAppSecret(String wechatAppSecret) {
		this.wechatAppSecret = wechatAppSecret;
	}

	public String getWechatWebAppId() {
		return wechatWebAppId;
	}

	public void setWechatWebAppId(String wechatWebAppId) {
		this.wechatWebAppId = wechatWebAppId;
	}

	public String getWechatWebAppSecret() {
		return wechatWebAppSecret;
	}

	public void setWechatWebAppSecret(String wechatWebAppSecret) {
		this.wechatWebAppSecret = wechatWebAppSecret;
	}

	public String getWechatTokenUrl() {
		return wechatTokenUrl;
	}

	public void setWechatTokenUrl(String wechatTokenUrl) {
		this.wechatTokenUrl = wechatTokenUrl;
	}

	public String getWechatUserUrl() {
		return wechatUserUrl;
	}

	public void setWechatUserUrl(String wechatUserUrl) {
		this.wechatUserUrl = wechatUserUrl;
	}

	public String getQqAppId() {
		return qqAppId;
	}

	public void setQqAppId(String qqAppId) {
		this.qqAppId = qqAppId;
	}

	public String getQqAppKey() {
		return qqAppKey;
	}

	public void setQqAppKey(String qqAppKey) {
		this.qqAppKey = qqAppKey;
	}

	public String getQqTokenUrl() {
		return qqTokenUrl;
	}

	public void setQqTokenUrl(String qqTokenUrl) {
		this.qqTokenUrl = qqTokenUrl;
	}

	public String getQqOpenIdUrl() {
		return qqOpenIdUrl;
	}

	public void setQqOpenIdUrl(String qqOpenIdUrl) {
		this.qqOpenIdUrl = qqOpenIdUrl;
	}

	public String getQqUserUrl() {
		return qqUserUrl;
	}

	public void setQqUserUrl(String qqUserUrl) {
		this.qqUserUrl = qqUserUrl;
	}
	
}
