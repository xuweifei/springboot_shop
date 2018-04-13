package com.opengroup.longmao.gwcommon.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

	@Value("${inc.totoro.appkey}")
	private String incAppkey;

	@Value("${duiba.profiles.appKey}")
	private String appKey;

	@Value("${duiba.profiles.appSecret}")
	private String appSecret;

	@Value("${duiba.profiles.duibaHTTPS}")
	private String duiBaHttps;

	@Value("${duiba.profiles.duibaInformHTTP}")
	private String duiBaInformHttp;

	@Value("${sms.app.key}")
	private String sms_app_key;

	@Value("${sms.app.secret}")
	private String sms_app_secret;

	@Value("${sms.nonce.str}")
	private String sms_nonce_str;

	@Value("${sms.send.url}")
	private String sms_send_url;

	@Value("${duiba.profiles.isTest}")
	private String isTest;

	@Value("${smsc.yunpian_appkey}")
	private String smscAppkey;

	@Value("${smsc.yunpian_url}")
	private String sendUrl;

	public String getIncAppkey() {
		return incAppkey;
	}

	public void setIncAppkey(String incAppkey) {
		this.incAppkey = incAppkey;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getDuiBaHttps() {
		return duiBaHttps;
	}

	public void setDuiBaHttps(String duiBaHttps) {
		this.duiBaHttps = duiBaHttps;
	}

	public String getDuiBaInformHttp() {
		return duiBaInformHttp;
	}

	public void setDuiBaInformHttp(String duiBaInformHttp) {
		this.duiBaInformHttp = duiBaInformHttp;
	}

	public String getSms_app_key() {
		return sms_app_key;
	}

	public void setSms_app_key(String sms_app_key) {
		this.sms_app_key = sms_app_key;
	}

	public String getSms_app_secret() {
		return sms_app_secret;
	}

	public void setSms_app_secret(String sms_app_secret) {
		this.sms_app_secret = sms_app_secret;
	}

	public String getSms_nonce_str() {
		return sms_nonce_str;
	}

	public void setSms_nonce_str(String sms_nonce_str) {
		this.sms_nonce_str = sms_nonce_str;
	}

	public String getSms_send_url() {
		return sms_send_url;
	}

	public void setSms_send_url(String sms_send_url) {
		this.sms_send_url = sms_send_url;
	}

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}

	public String getSmscAppkey() {
		return smscAppkey;
	}

	public void setSmscAppkey(String smscAppkey) {
		this.smscAppkey = smscAppkey;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

}
