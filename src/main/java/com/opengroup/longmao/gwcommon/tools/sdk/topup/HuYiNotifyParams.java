/**
 * @Title: HuYiNotifyParams.java 
 * @Package com.opengroup.longmao.gwcommon.tools.sdk.topup 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.tools.sdk.topup;

/**
 * @ClassName: HuYiNotifyParams
 * @Description: 互亿无线推送结果
 * @author Mr.Zhu
 */
public class HuYiNotifyParams {
	private String taskId; // 充值任务ID(签名)
	private String orderID; // 订单ID
	private String mobile; // 手机号码(签名)
	private String state; // 状态(签名)1:成功 2:失败
	private String message; // 消息(签名)

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
