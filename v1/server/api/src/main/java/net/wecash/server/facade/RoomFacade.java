package net.wecash.server.facade;

import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.bean.UserAllBean;
import net.wecash.server.bean.region.zh.bean.RegionBean;
import net.wecash.server.gis.region.dao.RegionDAO;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.room.dao.RoomDAO;
import net.wecash.server.util.CRUDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RoomFacade {

	@Autowired
	RoomDAO roomDAO;
	@Autowired
	RegionDAO regionDAO;
	
	public void saveRoom(RoomInfo roomInfo) {
		Assert.notNull(roomInfo);
		roomDAO.saveRoom(roomInfo);
	}

	public void updateRoom(RoomInfo roomInfo) {
		Assert.notNull(roomInfo);
		roomDAO.updateRoom(roomInfo);
	}

	public RoomQueryBean getRoom(Long userId) {
		Assert.notNull(userId);
		return roomDAO.getRoomByUserId(userId);
	}
	
	public RoomInfo getSrcRoomInfo(Long userId){
		return roomDAO.getSrcRoomInfoByUserId(userId);
	}
	
	
	public boolean isRoomExists(Long userId){
		return roomDAO.isRoomExists(userId);
	}
	
	public void convertRegionInfo(RoomInfo roomInfo){
		if(roomInfo.getProvince() != null && roomInfo.getArea() != null && roomInfo.getCity() != null){
			RegionBean regionBean = regionDAO.getName(
					roomInfo.getProvince(), roomInfo.getCity(),
					roomInfo.getArea(), roomInfo.getLandmark());
			if (regionBean == null) {
				throw new ErrorCodeException(
						ErrorCode.PARAMETER_VALUE_INVALID,
						"province|city|area");
			}
			CRUDUtil.convertRoomInfo(regionBean, roomInfo);
		}
	}
	
	public boolean updateOrsaveRoomByUserAllBean(Long userId, UserAllBean uab){
		boolean saveOrupdate = false;
		RoomInfo oldRoomInfo = roomDAO.getSrcRoomInfoByUserId(userId);
		if (oldRoomInfo != null) {
			RoomInfo roomInfo = CRUDUtil.checkUpdateValues(uab,
					oldRoomInfo, RoomInfo.class);
			if (CRUDUtil.canUpdate(roomInfo)) {
				convertRegionInfo(roomInfo);
				roomInfo.setUserId(userId);
				roomDAO.updateRoom(CRUDUtil.intersectionUpdateValues(
						roomInfo, oldRoomInfo, RoomInfo.class));
				saveOrupdate = true;
			}
		} else {
			RoomInfo roomInfo = new RoomInfo(uab.getMinPrice(),
					uab.getMaxPrice(), userId, uab.getProvince(),
					uab.getCity(), uab.getArea(),
					uab.getLandmark());
			convertRegionInfo(roomInfo);
			roomDAO.saveRoom(roomInfo);
			saveOrupdate = true;
		}
		return saveOrupdate;
	}
}
