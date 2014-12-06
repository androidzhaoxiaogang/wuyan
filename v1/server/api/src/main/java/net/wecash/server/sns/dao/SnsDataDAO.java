package net.wecash.server.sns.dao;

import java.util.List;

import net.wecash.common.mongo.model.SnsDataBean;

/**
 * @author franklin.li
 * 
 */
public interface SnsDataDAO {

	public SnsDataBean get(Long userId);

	public List<SnsDataBean> gets(List<Long> userIds);

	public void add(SnsDataBean s);

	public void delete(Long userId);

}
