package net.wecash.server.gis.subway.dao;

import java.util.List;

import net.wecash.server.mysql.model.Subway;

public interface SubwayDAO {
	List<Subway> getAll(String citycode, String lineStation);
}
