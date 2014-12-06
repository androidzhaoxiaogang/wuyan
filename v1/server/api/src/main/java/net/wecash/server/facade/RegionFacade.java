package net.wecash.server.facade;

import java.util.List;

import net.wecash.server.bean.AreaBean;
import net.wecash.server.bean.CityBean;
import net.wecash.server.bean.LandmarkBean;
import net.wecash.server.bean.provinceCodeBean;
import net.wecash.server.gis.region.dao.RegionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegionFacade {

	@Autowired
	RegionDAO regionDAO;
	
	public List<provinceCodeBean> getProvince(){
		return  regionDAO.getProvince();
	}
	
	public List<CityBean> getCity(String provincecode){
		return  regionDAO.getCity(provincecode);
	}
	
	public List<AreaBean> getArea(String citycode){
		return  regionDAO.getArea(citycode);
	}
	public List<LandmarkBean> getLandmark(String areacode){
		return  regionDAO.getLandmark(areacode);
	}
}
