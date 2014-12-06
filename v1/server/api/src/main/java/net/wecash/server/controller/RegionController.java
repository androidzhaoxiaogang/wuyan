package net.wecash.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.AreaBean;
import net.wecash.server.bean.CityBean;
import net.wecash.server.bean.LandmarkBean;
import net.wecash.server.bean.provinceCodeBean;
import net.wecash.server.facade.RegionFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegionController extends HandleExceptionController {

	@Autowired
	RegionFacade regionFacade;
	@Autowired
	AuthValidater authValidater;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/geographicInfo", method = RequestMethod.GET)
	public @ResponseBody
	Object getAll() {
		List<Map> geAll = new ArrayList<Map>();
		List<provinceCodeBean> provinces = regionFacade.getProvince();
		for (provinceCodeBean bean : provinces) {
			Map<String, Object> provincemap = new HashMap<String, Object>();
			provincemap.put("name", bean.getName());
			provincemap.put("code", bean.getCode());

			List<Map> cityList=new ArrayList<Map>();
			List<CityBean> citys = regionFacade.getCity(bean.getCode());
			for (CityBean city : citys) {
				Map<String, Object> citymap = new HashMap<String, Object>();
				citymap.put("name", city.getName());
				citymap.put("code", city.getCode());
				
				List<Map> areaList=new ArrayList<Map>();
				List<AreaBean> areas = regionFacade.getArea(city.getCode());
				for (AreaBean area : areas) {
					Map<String, Object> areamap = new HashMap<String, Object>();
					areamap.put("name", area.getName());
					areamap.put("code", area.getCode());

					List<LandmarkBean> landmarks = regionFacade
							.getLandmark(area.getCode());
					areamap.put("landmarks", landmarks);
					areaList.add(areamap);
				}
				citymap.put("areas", areaList);
				cityList.add(citymap);
			}
			provincemap.put("citys", cityList);
			geAll.add(provincemap);
		}
		return new OnlyResultBean(geAll);
	}
	@RequestMapping(value = "/getProvince", method = RequestMethod.GET)
	public @ResponseBody
	Object getProvince() {
		List<provinceCodeBean> provinces = regionFacade.getProvince();
		return new OnlyResultBean(provinces);
	}
	@RequestMapping(value = "/getCity", method = RequestMethod.GET)
	public @ResponseBody
	Object getCity(@RequestParam String provincecode) {
		List<CityBean> city = regionFacade.getCity(provincecode);
		return new OnlyResultBean(city);
	}
	@RequestMapping(value = "/getArea", method = RequestMethod.GET)
	public @ResponseBody
	Object getArea(@RequestParam String citycode) {
		List<AreaBean> area = regionFacade.getArea(citycode);
		return new OnlyResultBean(area);
	}
}
