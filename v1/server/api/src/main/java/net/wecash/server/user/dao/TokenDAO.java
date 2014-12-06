package net.wecash.server.user.dao;

import net.wecash.server.mysql.model.Token;


public interface TokenDAO {
	public void addToken(Token token);
	public Token getToken(String token);
	public Token getToken(Long userId, Integer type);
	public void deleteToken(String token);
	public void deleteToken(Long id);
}
