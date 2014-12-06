package net.wecash.data.sns.service;

import java.util.List;

import net.wecash.common.mongo.model.SnsDataBean;


/**
 * @author franklin.li
 * 
 */
public interface SnsDataDAO {

	/**
	 * @param sdb
	 */
	public void addSnsData(SnsDataBean sdb);

	/**
	 * @param userId
	 * @return
	 */
	public SnsDataBean get(Long userId);

	/**
	 * @param userIds
	 * @return
	 */
	public List<SnsDataBean> gets(List<Long> userIds);

}
