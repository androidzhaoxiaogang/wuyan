/**
 * 
 */
package net.wecash.server.backend;

import net.wecash.server.mysql.model.AdminInfo;

/**
 * @author franklin.li
 * 
 */
public interface BackendDAO {

	public boolean validateAdmin(String username, String password);

	public AdminInfo getAdminInfo(String username);

}
