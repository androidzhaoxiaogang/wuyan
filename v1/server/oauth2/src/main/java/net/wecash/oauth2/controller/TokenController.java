package net.wecash.oauth2.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.common.util.HttpRequestUtils;
import net.wecash.oauth2.amqp.AmqpMessageSender;
import net.wecash.oauth2.bean.AuthorizationBean;
import net.wecash.oauth2.bean.TokenBean;
import net.wecash.oauth2.factory.OAuth2Factory;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author xkk
 *
 */
@Controller
public class TokenController extends HandleExceptionController{
	@Autowired
	private OAuth2Factory oauth2Factory;
	@Autowired
	private AmqpMessageSender amqpMessageSender;
    @Autowired
    private ObjectMapper mapper;
	
	@RequestMapping(value = "/access_token", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getToken(HttpServletRequest request, 
    		HttpServletResponse response,
    		@RequestParam(value = "grant_type", required = true) String grantType,
    		@RequestParam(value = "username", required = false) String username,
    		@RequestParam(value = "password", required = false) String password
    		)
            throws OAuthSystemException, OAuthProblemException, Exception{
//    	OAuthTokenRequest oAuth2Request = new OAuthTokenRequest(request);
        AuthorizationBean authBean = new AuthorizationBean();
        authBean.setGrantType(grantType);
        authBean.setUsername(username);
        authBean.setPassword(password);
        authBean.setIp(HttpRequestUtils.getIpAddr(request));
        TokenBean token = oauth2Factory.generateToken(authBean);
        if(token != null){
        	amqpMessageSender.publishTokenMessage(token);
            OAuthResponse r = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(token.getToken())
                    .setExpiresIn(String.valueOf(token.getExpiresIn())).buildJSONMessage();
            response.setStatus(r.getResponseStatus());
            return mapper.readValue(r.getBody(), Map.class);
        }else{
        	return new Error("oauth2/access_token", ErrorCode.ACCESS_DENIED);
        }
    }
}
