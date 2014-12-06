/**
 * 
 */
package net.wecash.data.analyzer.interest.service;

import java.util.List;

/**
 * @author franklin.li
 * 
 */
public interface InterestTagDAO {

	/**
	 * @param names
	 * @return
	 */
	public Object groupTypesByNames(List<String> names);

	/**
	 * @param userId
	 * @return
	 */
	public Object groupTypesByUserId(Long userId);

}
