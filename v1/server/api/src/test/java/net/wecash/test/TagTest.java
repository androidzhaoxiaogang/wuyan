package net.wecash.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.wecash.server.tag.service.TagService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TagTest {
	@Autowired
	TagService ts;
	@Autowired
	RestTemplate template;
	@Test
	public void addTags()throws Exception{
		int type = 11;
		for(int i=0;i<=11;i++){
			File f = new File("C:\\Users\\franklin.li\\Desktop\\0729\\"+i+".txt");
			String url = "http://www.tongjuba.com/v1/tag?access_token=";
			System.out.println();
			net.wecash.server.mysql.model.Tag t = new net.wecash.server.mysql.model.Tag();
			t.setType(i);
			t.setWeight(1);
			String line = null;
//			t.setName("宠物");
//			ts.add(t);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			while ((line = reader.readLine()) != null) {
				t.setName(line.trim());
				if(!ts.isTagNameExists(t.getName())){
					ts.add(t);
				}else{
					System.out.println(t.getName() + " exists");
				}
			}
			reader.close();
		}

		
		/*for(File ff : fs){
			String[] i = ff.getName().split("\\.");
			int type = Integer.valueOf(i[0]);
			BufferedReader reader = new BufferedReader(new FileReader(ff));
			String line = null;
			net.wecash.server.mysql.model.Tag t = new net.wecash.server.mysql.model.Tag();
			while ((line = reader.readLine()) != null) {
				t.setName(line.trim());
				t.setType(type);
				t.setWeight(1);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(t);
				System.out.println(json);
				if(!ts.isTagNameExists(t.getName())){
					ts.add(t);
					feedback+=json+"\\r\\n";
//					System.out.println(IOUtils.stream2String(NetUtils.postMethod(url, null, json)));
				}else{
					System.out.println(t.getName() + " exists");
				}
			}
			reader.close();
		}
		os.write(feedback.getBytes());
		os.flush();
		os.close();*/
	}
}
