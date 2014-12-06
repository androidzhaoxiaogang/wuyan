package net.wecash.test;

import net.wecash.server.analyzer.UserMatchCalculator;
import net.wecash.server.analyzer.bean.ScoreTriggerBean;
import net.wecash.server.tag.dao.TagDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MatchTest {
	@Autowired
	UserMatchCalculator userMatchCalculator;
	@Autowired
	TagDAO tagDAO;
	@Test
	public void test(){
		System.out.println("-------------------------------------");
				/*	atb.setToken("2.00PZaVCCa5DuUB39ae54e92786s3bD");
		atb.setType(1);
		atb.setUid("1869347073");
		System.out.println(userMatchCalculator.analyzerInterest(atb));;
		System.out.println("-------------------------------------");
		atb.setToken("2.00JHAtwBz1GiMD929fee02b0qUq3GE");
		atb.setType(1);
		atb.setUid("1786268803");
		System.out.println(userMatchCalculator.analyzerInterest(atb));;
		System.out.println("-------------------------------------");*/
//		atb.setToken("2.002mRYFC6PTOxC784486f16e0LlS8o");
//		atb.setType(1);
//		atb.setUid("1914318841");
//		System.out.println(userMatchCalculator.analyzerInterest(atb));;
//		System.out.println("-------------------------------------");
		
//		atb.setToken("2.00HExSEC4bPmSC0819e718acJKQS1B");
//		atb.setType(1);
//		atb.setUid("1895887963");
//		System.out.println(userMatchCalculator.analyzerInterest(atb));;
//		System.out.println("-------------------------------------");
		
//		atb.setToken("2.00GrXwdBUXfhmD06003fd6400RgPdM");
//		atb.setType(1);
//		atb.setUid("1506363104");
//		System.out.println(userMatchCalculator.analyzerInterest(atb));;
//		System.out.println("-------------------------------------");
		
//		atb.setToken("2.0089OBACFIGzBD1f8c7e112eSfmqOB");
//		atb.setType(1);
//		atb.setUid("1832559571");
//		System.out.println(userMatchCalculator.analyzerInterest(atb));;
		int type = 1;
		ScoreTriggerBean atb = new ScoreTriggerBean(1L, null);
		atb.setUserId(3L);
		userMatchCalculator.analyzer(atb);
		System.out.println("-------------------------------------");
	}
}
