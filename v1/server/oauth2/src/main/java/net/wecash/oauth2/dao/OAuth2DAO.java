package net.wecash.oauth2.dao;

import net.wecash.common.model.Token;
import net.wecash.common.model.User;

public interface OAuth2DAO {
	User getUserByUsername(String username);
	public void addToken(Token token);
	public void removeToken(String token);
	public void removeExpiredToken(long now);
	public void updateUserLoginInfo(String username);
}
