package com.lq.wechatserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TB_SYS_CONFIG database table.
 * 
 */
@Entity
@Table(name="TB_SYS_CONFIG")
//@NamedQuery(name="TbSysConfig.findAll", query="SELECT t FROM TbSysConfig t")
public class SysConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;

	@Column(name="CODE")
	private String code;

	@Column(name="`DESC`")
	private String desc;

	@Column(name="VALUE")
	private String value;

	public SysConfigEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
