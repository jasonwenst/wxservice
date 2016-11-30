package test.repository;

import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lq.wechatserver.entity.UserEntity;
import com.lq.wechatserver.repository.UserRepository;

import test.configuration.BaseJUnit4Test;

public class UserRepositoryTest extends BaseJUnit4Test {
	
	@Autowired
	private UserRepository repository;
	
	
	@Test
	public void testAdd() {
		
		UserEntity entity = new UserEntity();
		entity.setIsPaied("Y");
		entity.setIsSubscribed("Y");
		entity.setJoinedDate(new Timestamp(System.currentTimeMillis()));
		entity.setNickName("新农哥1");
		entity.setOpenId("adgbfsthmyjmtyrte");
		
		repository.save(entity);
		
	}
	
	
	
	@Test
	public void testQuery() {
		UserEntity entity1 = repository.findOne(3);
		
		System.out.println(entity1.getUser().getNickName());
		
	}

}
