package test.repository;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lq.wechatserver.entity.PayInfoEntity;
import com.lq.wechatserver.repository.PayInfoRepository;

import test.configuration.BaseJUnit4Test;

public class PayInfoRepositoryTest  extends BaseJUnit4Test {
	
	@Autowired
	private PayInfoRepository repository;
	
	
	@Test
	public void testSave() {
		
		PayInfoEntity entity = new PayInfoEntity();
		
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setOpenId("121423");
		entity.setOutTradeNo("outtradeNo");
		entity.setTotalFee(1000);
		
		repository.save(entity);
	}

}
