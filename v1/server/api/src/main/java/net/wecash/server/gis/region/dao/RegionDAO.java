package net.wecash.server.gis.region.dao;

import java.util.List;

import net.wecash.server.bean.AreaBean;
import net.wecash.server.bean.CityBean;
import net.wecash.server.bean.LandmarkAllBean;
import net.wecash.server.bean.LandmarkBean;
import net.wecash.server.bean.provinceCodeBean;
import net.wecash.server.bean.region.zh.bean.RegionBean;

public interface RegionDAO {

	List<provinceCodeBean> getProvince();

	List<CityBean> getCity(String provinceCode);

	List<AreaBean> getArea(String citycode);

	List<LandmarkBean> getLandmark(String areacode);

	List<LandmarkAllBean> getAll(String code);
	
	public RegionBean getName(String province,String city,String area,String landmark);
}
