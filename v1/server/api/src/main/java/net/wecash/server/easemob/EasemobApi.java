/**
 * 
 */
package net.wecash.server.easemob;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author franklin.li
 * 
 */
@Component
public class EasemobApi {
	@Value("${easemob.app.key}")
    private String appKey;
	@Value("${easemob.default.password}")
    private String defaultPwd;
	@Value("${easemob.admin.username}")
    private String admin;
	@Value("${easemob.admin.password}")
    private String password;
	/**
	 * 创建用户
	 * 
	 * @param host
	 *            IP或者域名
	 * @param port
	 *            端口
	 * @param appKey
	 *            easemob-demo#chatdemo
	 * @param postBody
	 *            封装了用户属性的json对象
	 * @param token
	 *            admin级别token
	 * @return
	 */
	public String createNewUser(String host, String appKey, Map<String, Object> body,
			String token) {
		String orgName = appKey.substring(0, appKey.lastIndexOf("#"));
		String appName = appKey.substring(appKey.lastIndexOf("#") + 1);

		String rest = orgName + "/" + appName + "/users";

		String reqURL = "https://" + host + "/" + rest;
		String result = HttpsUtils.sendSSLRequest(reqURL, token, HttpsUtils.Map2Json(body),
				HttpsUtils.Method_POST);
		return result;
	}

	/**
	 * 删除用户
	 * 
	 * @param host
	 *            IP或者域名
	 * @param port
	 *            端口
	 * @param appKey
	 *            easemob-demo#chatdemo
	 * @param id
	 *            usename or uuid
	 * @param token
	 *            admin级别token
	 * @return
	 */
	public String deleteUser(String host, String appKey, String id, String token) {

		String orgName = appKey.substring(0, appKey.lastIndexOf("#"));
		String appName = appKey.substring(appKey.lastIndexOf("#") + 1);

		String rest = orgName + "/" + appName + "/users/" + id;

		String reqURL = "https://" + host + "/" + rest;
		String result = HttpsUtils.sendSSLRequest(reqURL, token, null, HttpsUtils.Method_DELETE);

		return result;
	}

	/**
	 * 获取token
	 * 
	 * @param host
	 *            IP或者域名
	 * @param port
	 *            端口
	 * @param appKey
	 *            easemob-demo#chatdemo
	 * @param isAdmin
	 *            org管理员token true, IM用户token false
	 * @param postBody
	 *            POST请求体
	 * @return
	 */
	public String getAccessToken(String host, String appKey, Boolean isAdmin,
			Map<String, Object> postBody) {
		String orgName = appKey.substring(0, appKey.lastIndexOf("#"));
		String appName = appKey.substring(appKey.lastIndexOf("#") + 1);
		String accessToken = "";
		String rest = "management/token";
		if (!isAdmin) {
			rest = orgName + "/" + appName + "/token";
		}

		String reqURL = "https://" + host + "/" + rest;
		String result = HttpsUtils.sendSSLRequest(reqURL, null, HttpsUtils.Map2Json(postBody),
				HttpsUtils.Method_POST);
		Map<String, String> resultMap = HttpsUtils.Json2Map(result);

		accessToken = resultMap.get("access_token");

		return accessToken;
	}
	public static void main(String []args){
		EasemobApi ea = new EasemobApi();
		System.out.println(ea.createNewUser("xkk"));;
	}
	public String createNewUser(String username){
		String host = "a1.easemob.com";

//		// 获取IM用户token
		Map<String, Object> getIMAccessTokenPostBody = new HashMap<String, Object>();
		getIMAccessTokenPostBody.put("grant_type", "password");
		getIMAccessTokenPostBody.put("username", admin);
		getIMAccessTokenPostBody.put("password", password);
		String imToken = getAccessToken(host, appKey, false,
				getIMAccessTokenPostBody);
		System.out.println(imToken);
//
		// 获取管理员token
//		Map<String, Object> getAccessTokenPostBody = new HashMap<String, Object>();
//		getAccessTokenPostBody.put("grant_type", "password");
//		getAccessTokenPostBody.put("username", "franklin");
//		getAccessTokenPostBody.put("password", "111111");
//		 String adminToken = EasemobUserAPI.getAccessToken(host, appKey, true,
//		 getAccessTokenPostBody);
		// System.out.println(adminToken);

		// 创建用户
		Map<String, Object> createNewUserPostBody = new HashMap<String, Object>();
		createNewUserPostBody.put("username", username);
		createNewUserPostBody.put("password", defaultPwd);
//		createNewUserPostBody.put("addr", "BJFS");
		String s = createNewUser(host, appKey, createNewUserPostBody, imToken);
		return s;
	}
}
