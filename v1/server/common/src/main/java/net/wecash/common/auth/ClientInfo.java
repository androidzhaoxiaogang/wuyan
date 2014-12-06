package net.wecash.common.auth;

public class ClientInfo {
	public static final String IOS_CLIENT_ID = "0264a94959afb8d8";
	public static final String IOS_CLIENT_SECRET = "95fc6be00264a94959afb8d8ec6704fc";
	public static final String ANDROID_CLIENT_ID = "720a34d31d141444";
	public static final String ANDROID_CLIENT_SECRET = "7ce60b13720a34d31d141444b1680393";
	public static final String WEB_CLIENT_ID = "852e46d49ca29e2e";
	public static final String WEB_CLIENT_SECRET = "572ce914852e46d49ca29e2e04c3f355";
	
	public static final String BACKEND_CLIENT_ID = "80c1b0e51e5ed547";
	public static final String BACKEND_CLIENT_SECRET = "2b37661f80c1b0e51e5ed54781a7ff52";
	
	public static boolean validateIOSClient(String clientId, String clientSecret){
		return clientId.equals(IOS_CLIENT_ID) & clientSecret.equals(IOS_CLIENT_SECRET);
	}
	public static boolean validateAndroidClient(String clientId, String clientSecret){
		return clientId.equals(ANDROID_CLIENT_ID) & clientSecret.equals(ANDROID_CLIENT_SECRET);
	}
	public static boolean validateWebClient(String clientId, String clientSecret){
		return clientId.equals(WEB_CLIENT_ID) & clientSecret.equals(WEB_CLIENT_SECRET);
	}
	public static boolean validateBackendClient(String clientId, String clientSecret){
		return clientId.equals(BACKEND_CLIENT_ID) & clientSecret.equals(BACKEND_CLIENT_SECRET);
	}
}
