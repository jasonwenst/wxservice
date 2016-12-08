package test.repository;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lq.wechatserver.entity.PayInfoErrEntity;
import com.lq.wechatserver.entity.SysConfigEntity;
import com.lq.wechatserver.repository.PayInfoErrRepository;
import com.lq.wechatserver.repository.PayInfoRepository;
import com.lq.wechatserver.repository.SysConfigRepository;

import test.configuration.BaseJUnit4Test;

public class PayInfoRepositoryTest  extends BaseJUnit4Test {
	
	@Autowired
	private PayInfoRepository repository;
	@Autowired
	private PayInfoErrRepository errRepository;
	@Autowired
	private SysConfigRepository sysConfigRepository;
	
	
	@Test
	public void testSave() {
		
		PayInfoErrEntity entity = new PayInfoErrEntity();
		
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setOpenId("121423");
		entity.setOutTradeNo("outtradeNo");
		entity.setTotalFee(1000);
		entity.setErrMsg("a fdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafadfdeafafad");
		
		errRepository.save(entity);
	}
	
	
	
	@Test
	public void testSysConfig() {
		
		SysConfigEntity entity = new SysConfigEntity();
		
		entity.setCode("wx.partenerId");
		entity.setValue("avdfhghjmgjgh");
		entity.setDesc("微信支付商户号");
		
		sysConfigRepository.save(entity);
	}
	
	@Test
	public void qrySysCOnfig() {
		List<SysConfigEntity> list = (List<SysConfigEntity>) sysConfigRepository.findAll();
		
		for(SysConfigEntity entity : list) {
			System.out.println(entity.getCode());
		}
	}

}
