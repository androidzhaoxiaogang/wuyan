package net.wecash.oauth2.service;

import java.util.HashMap;
import java.util.Map;

import net.wecash.oauth2.dao.AuthenticateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticateService implements AuthenticateDAO{
	@Autowired
    private RestTemplate template;
    @Autowired
    private ObjectMapper mapper;
    @Value("${api.authenticateUrl}")
    private String authenticateUrl;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, Object> validateCreditials(
			String username,
    		String password, 
    		String ip) {
    	Map<String, Object> body = new HashMap<String, Object>();
    	body.put("username", username);
    	body.put("password", password);
    	body.put("ip", ip);
    	Map validateResult = template.postForObject(authenticateUrl, body, Map.class);
    	return new HashMap<String, Object>(validateResult);
    }
}
