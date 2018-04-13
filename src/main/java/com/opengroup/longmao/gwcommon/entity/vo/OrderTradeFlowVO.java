/**
 * @Title: OrderTradeFlowVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: OrderTradeFlowVO
 * @Description: TODO
 * @author Mr.Zhu
 */
public class OrderTradeFlowVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -7023242343640676954L;
	
	private Long flowId;
	private Long orderId;
	private Short payStatus;
	private BigDecimal payPrice;
	private String payFlowNo;
	private Date time;
	private Short isDelete;

	public OrderTradeFlowVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public OrderTradeFlowVO(Long flowId, String payFlowNo, Date time) {
		super();
		this.flowId = flowId;
		this.payFlowNo = payFlowNo;
		this.time = time;
	}


	/**
	 * @Description: TODO 
	 * @param orderId
	 * @param payStatus
	 * @param payPrice
	 * @param payFlowNo
	 * @param time
	 */
	public OrderTradeFlowVO(Long orderId, Short payStatus, BigDecimal payPrice, String payFlowNo, Date time, Short isDelete) {
		super();
		this.orderId = orderId;
		this.payStatus = payStatus;
		this.payPrice = payPrice;
		this.payFlowNo = payFlowNo;
		this.time = time;
		this.isDelete = isDelete;
	}
	
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Short getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Short payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public String getPayFlowNo() {
		return payFlowNo;
	}

	public void setPayFlowNo(String payFlowNo) {
		this.payFlowNo = payFlowNo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	
}
