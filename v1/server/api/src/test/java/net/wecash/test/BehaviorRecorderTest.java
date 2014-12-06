/**
 * 
 */
package net.wecash.test;

import java.util.Map;

import net.wecash.server.bean.UserAllBean;
import net.wecash.server.behavior.service.BehaviorRecorder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BehaviorRecorderTest {
	@Autowired
	BehaviorRecorder behaviorRecorder;
	@Autowired
	ObjectMapper mapper;
	@Test
	public void test()throws Exception{
		UserAllBean uab = new UserAllBean();
		uab.setArea("");
		Map map = mapper.convertValue(uab, Map.class);
		behaviorRecorder.triggerAnalyzerAll(10206L, map);
	}
}
