/**
 * 
 */
package net.wecash.test;

import java.util.HashMap;
import java.util.Map;

import net.wecash.common.jackson.ObjectMapperFactory;
import net.wecash.common.util.IOUtils;
import net.wecash.common.util.NetUtils;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 */
public class UserTest {
	@Test
	public void test()throws Exception{
		ObjectMapper mapper = new ObjectMapperFactory().getMapper();
		String url = "http://www.tongjuba.net/v1/user/1/update?access_token=15534000471c045399ea44dbaba7c71a";
		Map map = new HashMap<>();
		map.put("name", "难啃的骨头");
		map.put("personality", 0.5f);
		String json = mapper.writeValueAsString(map);
		System.out.println(IOUtils.stream2String(NetUtils.postMethod(url, null, json)));
	}
}
