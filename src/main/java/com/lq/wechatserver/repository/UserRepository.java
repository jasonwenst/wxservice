package com.lq.wechatserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.lq.wechatserver.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	
	UserEntity findByOpenId(String openId);

}
