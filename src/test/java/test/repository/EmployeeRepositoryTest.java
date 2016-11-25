package test.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lq.wechatserver.entity.EmployeeEntity;
import com.lq.wechatserver.repository.EmployeeRepositroy;

import junit.framework.Assert;
import test.configuration.BaseJUnit4Test;

@Repository
public class EmployeeRepositoryTest extends BaseJUnit4Test {
	
	Logger logger = LoggerFactory.getLogger(EmployeeRepositoryTest.class);
	
	@Autowired
	private EmployeeRepositroy repository;
	
	
	@Test
	public void testAdd() {
		
		EmployeeEntity entity = new EmployeeEntity();
		entity.setJoiningDate(new Date());
		entity.setName("苍井空");
		entity.setSalary(new BigDecimal(20000));
		entity.setSsn('Y');
		
		EmployeeEntity returnEntity = repository.save(entity);
		
		Assert.assertTrue(returnEntity.getId() > 0);
		
	}
	
	
	@Test
	public void testQuery() {
		EmployeeEntity entity = repository.findOne(2);
		
		logger.info("entity = {}", entity.toString());
		
	}

}
