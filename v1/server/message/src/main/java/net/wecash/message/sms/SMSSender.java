package net.wecash.message.sms;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * 
 * @author xkk
 *
 */
@Component
public class SMSSender {
	
	@Value("${sms.host}")
	private String host;
			
	@Value("${sms.port}")
	private String port;
	
	@Value("${sms.main.account}")
	private String account;
	
	@Value("${sms.main.token}")
	private String smsToken;
	
	@Value("${sms.appid}")
	private String appId;
	
	public static final Logger logger = LoggerFactory.getLogger(SMSSender.class);
	
	@SuppressWarnings("unchecked")
	public void sendMessage(String toPhoneList, String templateId, String[] param, String content){
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(host, port);
		restAPI.setAccount(account, smsToken);
		restAPI.setAppId(appId);
		result = restAPI.sendTemplateSMS(toPhoneList, templateId, param);

		logger.info("SDKGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				logger.info(key +" = "+object);
			}
		}else{
			logger.error("error code(" + result.get("statusCode") +") error info("+result.get("statusMsg")+")");
		}
	}
}
