/**
 * 
 */
package net.wecash.data.analyzer;

import net.wecash.data.analyzer.bean.SnsAuthBean;
import net.wecash.data.analyzer.interest.service.InterestCalculator;
import net.wecash.data.sns.service.SnsDataDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author franklin.li
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CollectTest {
	@Autowired
	ThirdSnsAnalyzer thirdSnsAnalyzer;
	@Autowired
	SnsDataDAO snsDataDAO;
	@Autowired
	InterestCalculator interestCalculator;
	@Test
	public void test()throws Exception{
//			interestCalculator.analyzerSelfInterest((long)10209);
//			Thread.sleep(500);
//		interestCalculator.analyzerSelfInterest((long)3);
//		Thread.sleep(500);
		SnsAuthBean sab = new SnsAuthBean();
		sab.setUid("wuyan53cfdeed76cd119c73b32253");
		sab.setToken("wuyan53cfdeed76cd119c73b32253");
		sab.setUserId(10120l);
		thirdSnsAnalyzer.analyzer(sab);
	}
	
//	@Test
//	public void testAddData(){
//		SnsDataBean sdb = new SnsDataBean(null, 2L, null, 1, null, null, null, null, null);
//		snsDataDAO.addSnsData(sdb);
//	}
}
