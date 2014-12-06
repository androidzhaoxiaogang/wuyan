package net.wecash.server.facade;

import java.util.List;

import net.wecash.server.gis.subway.dao.SubwayDAO;
import net.wecash.server.mysql.model.Subway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubwayFacade {

	@Autowired
	SubwayDAO subwayDAO;

	public List<Subway> getAll(String citycode, String lineStation) {
		return subwayDAO.getAll(citycode,lineStation);
	}
	
}
