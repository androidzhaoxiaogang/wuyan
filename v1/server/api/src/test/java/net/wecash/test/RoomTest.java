package net.wecash.test;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.room.service.RoomService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RoomTest{

	@Autowired
	RoomService roomService;

	@Test
	public void addroom() throws JsonProcessingException, IOException {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setProvince("北京");
		roomInfo.setCity("北京市");
		roomInfo.setArea("朝阳区");
		roomInfo.setMaxPrice(1500);
		roomInfo.setMinPrice(1000);
		roomInfo.setUserId(10369L);
		roomInfo.setDescription("找房子");
		roomService.saveRoom(roomInfo);
		
	}

//	@Test
//	public void getRoom() {
//		RoomQueryBean roomQueryBean = roomService.getRoomCodeById(10120L);
//		System.out.println(roomQueryBean);
//	}
//
//	@Test
//	public void updateRoom() {
//		RoomInfo roomInfo = new RoomInfo();
//		roomInfo.setProvince("110000");
//		roomInfo.setCity("110100");
//		roomInfo.setArea("110105");
//		roomInfo.setMaxPrice(1500);
//		roomInfo.setMinPrice(900);
//		roomInfo.setDescription("找房子");
//		roomInfo.setUserId(10120L);
//		roomService.updateRoom(roomInfo);
//	}
//
//	@Test
//	public void getRoomSql() {
//		String sql = "select u.id as userId,u.name as useName,u.gender,r.min_price as minPrice,r.max_price as maxPrice,r.description,a.name as area,r.line_num as lineNum,r.line_station as lineStation from t_room_info r,t_user u,t_area a where r.area=a.code and  r.user_id=u.id and r.area=110101";
//		List<RoomQueryBean> list = roomService.getRoom(sql);
//		System.out.println(list);
//	}
}
