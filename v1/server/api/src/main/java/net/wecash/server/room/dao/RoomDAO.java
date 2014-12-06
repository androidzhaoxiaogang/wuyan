package net.wecash.server.room.dao;

import java.util.List;

import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.mysql.model.RoomInfo;

public interface RoomDAO {

	void saveRoom(RoomInfo roomInfo);

	void updateRoom(RoomInfo roomInfo);

	RoomQueryBean getRoomByUserId(Long id);

	RoomQueryBean getRoomCodeById(Long id);

	public void deleteRoom(Long userId);

	public boolean isRoomExists(Long userId);

	public RoomInfo getSrcRoomInfoByUserId(Long userId);

	List<Long> getUserId(String province, String city, String area);
}
