/**
 * 
 */
package net.wecash.server.badupush;

import net.wecash.server.controller.TagController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

/**
 * @author franklin.li
 * 
 */

@Service
public class IosPushNotificationSample {

	@Value("${baidu.push.apiKey}")
	private String apiKey;
	@Value("${baidu.push.secretKey}")
	private String secretKey;
	private static Logger logger = LoggerFactory.getLogger(TagController.class);

	public void push(String channelId, String userId, Integer type, String content) {
		/*
		 * @brief 推送单播通知(IOS APNS) message_type = 1 (默认为0)
		 */
		// 1. 设置developer平台的ApiKey/SecretKey
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

		// 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);

		// 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		try {
			// 4. 创建请求类对象
			// 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(type); // device_type => 1: web 2: pc
											// 3:android
											// 4:ios 5:wp
			request.setDeployStatus(1); // DeployStatus => 1: Developer 2:
			// Production
			request.setChannelId(Long.parseLong(channelId));
			request.setUserId(userId);

			request.setMessageType(1);
			request.setMessage(content);

			// 5. 调用pushMessage接口
			PushUnicastMessageResponse response = channelClient
					.pushUnicastMessage(request);

			// 6. 认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount());
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			logger.error(String.format(
					"request_id: %d, error_code: %d, error_message: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
	}
}
