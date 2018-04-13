package com.opengroup.longmao.gwcommon.tools.sdk.duiba;

/**
 * 开发者向兑吧发起请求，确认兑换成功失败的参数
 * 
 * 目前仅限于虚拟商品，需要使用
 * 
 * @author xuhengfei
 *
 */
public class CreditVirtualParams {

	private Short success;
	private String errorMessage = "签名验证失败";
	private String bizId = "";
	private Long credits = -1L;// 用户积分余额

	public CreditVirtualParams(Short success) {
		this.success = success;
	}

	public String toString() {
		if (0 == success) {
			return "{'status':'success','errorMessage':'成功','supplierBizId':'" + bizId + "','credits':'"
					+ credits + "'}";
		} else if (1 == success) {
			return "{'status':'process','errorMessage':'" + errorMessage + "','supplierBizId':'" + bizId
					+ "','credits':'" + credits + "'}";
		} else {
			return "{'status':'fail','errorMessage':'" + errorMessage + "','supplierBizId':'" + bizId + "','credits':'"
					+ credits + "'}";
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Long getCredits() {
		return credits;
	}

	public void setCredits(Long credits) {
		this.credits = credits;
	}

}
