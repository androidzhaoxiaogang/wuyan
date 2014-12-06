package net.wecash.message.handler;

import java.io.IOException;

import net.wecash.common.model.SMS;
import net.wecash.message.sms.SMSSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;


/**
 * 
 * @author xkk
 *
 */
@Component
public class MessageHandler implements MessageListener, ChannelAwareMessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	@Autowired
    private ObjectMapper mapper;
	@Autowired
	private SMSSender smsSender;
	@Value("${sms.template.captcha}")
	private String templateId4Captcha;
	@Value("${sms.template.password}")
	private String templateId4Password;
	
	public void onMessage(Message message, Channel channel) throws Exception {
		byte[] body = message.getBody();
        sendSMS(body, message.getMessageProperties().getReceivedRoutingKey());
	}
	
	public void onMessage(Message message) {
	}
	
    private void sendSMS(byte[] body, String routingKey) {
        try {
            SMS sms =  mapper.readValue(body, SMS.class);
            String[] data = routingKey.split("\\.");
            if (data.length == 4) {
            	if(data[3].equals("captcha")){
            		logger.info(sms.getPhone()+":"+templateId4Captcha+":"+sms.getParam()[0]+":"+sms.getContent());
            		smsSender.sendMessage(sms.getPhone(), templateId4Captcha, sms.getParam(), sms.getContent());
            	}else if(data[3].equals("password")){
            		logger.info(sms.getPhone()+":"+templateId4Password+":"+sms.getParam()[0]+":"+sms.getContent());
            		smsSender.sendMessage(sms.getPhone(), templateId4Password, sms.getParam(), sms.getContent());
            	}
            } else {
                logger.error("Error routing key, unknown sms type");
            }
        } catch (IOException e) {
            logger.error("Error message {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
