package com.lq.wechatserver.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TB_PAY_INFO database table.
 * 
 */
@Entity
@Table(name="TB_PAY_INFO_ERR")
//@NamedQuery(name="TbPayInfo.findAll", query="SELECT t FROM TbPayInfo t")
public class PayInfoErrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAY_INFO_ID")
	private int payInfoId;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="OPEN_ID")
	private String openId;

	@Column(name="OUT_TRADE_NO")
	private String outTradeNo;

	@Column(name="TOTAL_FEE")
	private int totalFee;
	
	@Column(name = "ERR_MSG")
	private String errMsg;
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Transient
	private long createTimestamp;

	public long getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public PayInfoErrEntity() {
	}

	public int getPayInfoId() {
		return this.payInfoId;
	}

	public void setPayInfoId(int payInfoId) {
		this.payInfoId = payInfoId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public int getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

}