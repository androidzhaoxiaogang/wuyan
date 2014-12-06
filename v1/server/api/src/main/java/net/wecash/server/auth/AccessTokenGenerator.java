package net.wecash.server.auth;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 
 * @author xkk
 *
 */
public class AccessTokenGenerator {
	private static AccessTokenGenerator uniqueInstance = null;
	private OAuthIssuer oauthIssuerImpl = null;
	public AccessTokenGenerator() {
		oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
	}

	public static AccessTokenGenerator getInstance() {  
		if (uniqueInstance == null) {  
			uniqueInstance = new AccessTokenGenerator();  
		}  
		return uniqueInstance;  
	}

	public String getAccessToken() throws OAuthSystemException{
		return oauthIssuerImpl.accessToken();
	}
	
	public String getRefreshToken() throws OAuthSystemException{
		return oauthIssuerImpl.refreshToken();
	}
}
