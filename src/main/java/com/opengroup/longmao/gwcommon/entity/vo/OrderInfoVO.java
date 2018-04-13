/**
 * @Title: OrderInfoVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: OrderInfoVO
 * @Description: TODO
 * @author Mr.Zhu
 */
public class OrderInfoVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4544850066591402612L;
	private Long orderId;
	private Short orderStatus;
	private Short deliveryStatus;
	private Date payTime;
	private String payFlowNo;
	private String remark;
	private Short isDelete;

	public OrderInfoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderInfoVO(Long orderId, Short orderStatus, Short deliveryStatus, String remark) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.deliveryStatus = deliveryStatus;
		this.remark = remark;
	}

	public OrderInfoVO(Long orderId, Short orderStatus, Short deliveryStatus, Date payTime, String payFlowNo,
			String remark) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.deliveryStatus = deliveryStatus;
		this.payTime = payTime;
		this.payFlowNo = payFlowNo;
		this.remark = remark;
	}
	
	public OrderInfoVO(Long orderId, Short orderStatus, Short deliveryStatus, Date payTime, String payFlowNo,
			String remark, Short isDelete) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.deliveryStatus = deliveryStatus;
		this.payTime = payTime;
		this.payFlowNo = payFlowNo;
		this.remark = remark;
		this.isDelete = isDelete;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Short deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayFlowNo() {
		return payFlowNo;
	}

	public void setPayFlowNo(String payFlowNo) {
		this.payFlowNo = payFlowNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	
}
