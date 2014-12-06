package net.wecash.oauth2.dao;

import java.util.HashMap;

public interface AuthenticateDAO {
	public HashMap<String, Object> validateCreditials( String username, String password, String ip);
}
