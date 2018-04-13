/**
 * @Title: OrderBuyDetailVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: OrderBuyDetailVO
 * @Description: TODO
 * @author Mr.Zhu
 */
public class OrderBuyDetailVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 555252870187498352L;
	private Long orderId;
	private Long goodsId;
	private String goodsName;
	private BigDecimal goodsPrice;

	public OrderBuyDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @Description: TODO 
	 * @param orderId
	 * @param goodsId
	 * @param goodsName
	 * @param goodsPrice
	 */
	public OrderBuyDetailVO(Long orderId, Long goodsId, String goodsName, BigDecimal goodsPrice) {
		super();
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

}
