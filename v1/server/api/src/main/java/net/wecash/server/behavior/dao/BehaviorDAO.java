/**
 * 
 */
package net.wecash.server.behavior.dao;

import java.util.Map;

/**
 * @author franklin.li
 * 
 */
public interface BehaviorDAO {

	public Map getLastUpdatePortraitRecord(Long userId);

	public void addUpdatePortraitRecord(Long userId, Map map, boolean trigger);

}
