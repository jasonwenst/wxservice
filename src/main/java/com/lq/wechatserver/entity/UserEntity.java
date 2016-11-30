package com.lq.wechatserver.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the TB_USER database table.
 * 
 */
@Entity
@Table(name="TB_USER")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="OPEN_ID")
	private String openId;

	@Column(name="IS_PAIED")
	private String isPaied;

	@Column(name="IS_SUBSCRIBED")
	private String isSubscribed;

	@Column(name="JOINED_DATE")
	private Timestamp joinedDate;

	@Column(name="NICK_NAME")
	private String nickName;

	//bi-directional many-to-one association to TbUser
	@ManyToOne
	@JoinColumn(name="JOINED_BY")
	private UserEntity user;

	//bi-directional many-to-one association to TbUser
//	@OneToMany(mappedBy="tbUser")
//	private List<UserEntity> tbUsers;

	public UserEntity() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIsPaied() {
		return this.isPaied;
	}

	public void setIsPaied(String isPaied) {
		this.isPaied = isPaied;
	}

	public String getIsSubscribed() {
		return this.isSubscribed;
	}

	public void setIsSubscribed(String isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	public Timestamp getJoinedDate() {
		return this.joinedDate;
	}

	public void setJoinedDate(Timestamp joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public UserEntity getUser() {
		return this.user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	

//	public List<UserEntity> getTbUsers() {
//		return this.tbUsers;
//	}
//
//	public void setTbUsers(List<UserEntity> tbUsers) {
//		this.tbUsers = tbUsers;
//	}

//	public UserEntity addTbUser(UserEntity tbUser) {
//		getTbUsers().add(tbUser);
//		tbUser.setTbUser(this);
//
//		return tbUser;
//	}

//	public UserEntity removeTbUser(UserEntity tbUser) {
//		getTbUsers().remove(tbUser);
//		tbUser.setTbUser(null);
//
//		return tbUser;
//	}

}