package com.lq.wechatserver.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lq.wechatserver.entity.PayInfoEntity;

public interface PayInfoRepository extends CrudRepository<PayInfoEntity, Integer> {
	
	
	List<PayInfoEntity> getPayInfoEntityByOpenId(String openId);

}
