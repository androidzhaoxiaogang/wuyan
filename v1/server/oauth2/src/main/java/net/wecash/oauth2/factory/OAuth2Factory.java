package net.wecash.oauth2.factory;

import java.util.Map;

import net.wecash.common.auth.oauth2.TokenInfo;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.oauth2.bean.AuthorizationBean;
import net.wecash.oauth2.bean.TokenBean;
import net.wecash.oauth2.dao.AuthenticateDAO;
import net.wecash.oauth2.util.AccessTokenGenerator;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author xkk
 */
@Component
public class OAuth2Factory {
	
	@Autowired
	AuthenticateDAO authenticateDAO;
    @Autowired
    private ObjectMapper mapper;

    public TokenBean generateToken(AuthorizationBean authBean)throws Exception{
    	TokenBean token = null;
    	switch (GrantType.valueOf(authBean.getGrantType().toUpperCase())) {
    	case AUTHORIZATION_CODE:
			break;
		case PASSWORD:
			token = authorizationPwd( 
					authBean.getUsername(), 
					authBean.getPassword(), 
					authBean.getIp());
			break;
		case REFRESH_TOKEN:
			break;
		case CLIENT_CREDENTIALS:
			break;
		default:
			Error error = new Error("oauth2/access_token", ErrorCode.UNSUPPORTED_GRANT_TYPE);
			throw new ErrorCodeException(error);
		}
    	return token;
    }
    private TokenBean authorizationPwd(String username, String password, String ip) throws Exception{
    	TokenBean token = null; 
		Map<String, Object> validateResult = authenticateDAO.validateCreditials(username, password, ip);
		token = getPwdToken(username, validateResult);
    	return token;
	}
    
    private TokenBean getPwdToken(String username, Map<String, Object> validateResult) throws OAuthSystemException{
    	TokenBean token = null;
    	if(username != null){
    		if(validateResult == null || validateResult.containsKey("result")){
            	token = new TokenBean();
                token.setToken(AccessTokenGenerator.getInstance().getAccessToken());
    			//token.setType(MapUtils.getIntValue(validateResult, "type", 1));
    			token.setUsername(username);
    			token.setExpiresIn(TokenInfo.TokenExpiresIn);
            }else{
            	throw new ErrorCodeException(mapper.convertValue(validateResult, Error.class));
            }
    	}else{
    		throw new ErrorCodeException(ErrorCode.ACCESS_DENIED);
    	}
    	return token;
    }
}
