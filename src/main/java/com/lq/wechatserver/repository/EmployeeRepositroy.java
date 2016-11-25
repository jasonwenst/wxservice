package com.lq.wechatserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.lq.wechatserver.entity.EmployeeEntity;

public interface EmployeeRepositroy extends CrudRepository<EmployeeEntity, Integer> {

}
