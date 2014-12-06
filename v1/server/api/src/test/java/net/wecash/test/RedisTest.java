package net.wecash.test;

import net.wecash.server.badupush.service.PushService;
import net.wecash.server.mysql.model.Push;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	PushService pushService;
	@SuppressWarnings("unchecked")
	@Test
	public void Test()throws Exception{
		System.out.println("-----------------------------------------------------------");
//		String testKey = "test";
//		redisTemplate.opsForHash().put(HashCacheNames.PHONE_CAPTCHA, testKey, "xkkkkk");
//		Object o = redisTemplate.opsForHash().get(HashCacheNames.PHONE_CAPTCHA, testKey);
//		redisTemplate.expire(HashCacheNames.PHONE_CAPTCHA, 1, TimeUnit.MINUTES);
//		System.out.println("--- before 60s --------------------------"+o.toString());
//		Thread.sleep(1000 * 61);
//		System.out.println("--after 60s --------------------------"+o.toString());
//		System.out.println("-----------------------------------------------------------");
//		
		Push p = pushService.getPush(10561L);
		System.out.println(p.toString());
	}
}
