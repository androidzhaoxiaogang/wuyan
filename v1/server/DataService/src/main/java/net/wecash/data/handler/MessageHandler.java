package net.wecash.data.handler;

import java.io.IOException;

import net.wecash.data.analyzer.ThirdSnsAnalyzer;
import net.wecash.data.analyzer.bean.SnsAuthBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
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
	ThirdSnsAnalyzer thirdSnsAnalyzer;
	
	public void onMessage(Message message, Channel channel) throws Exception {
		byte[] body = message.getBody();
		collectData(body, message.getMessageProperties().getReceivedRoutingKey());
	}
	
	public void onMessage(Message message) {
	}
	
    private void collectData(byte[] body, String routingKey) {
        try {
        	SnsAuthBean sab =  mapper.readValue(body, SnsAuthBean.class);
            if (body != null) {
            	//maybe limit the interval between two same request
            	logger.info("get data:" + mapper.writeValueAsString(sab));
            	if(sab.getUserId() != null){
            		thirdSnsAnalyzer.analyzer(sab);
            	}else{
            		logger.error("Error data, unknown userId:" + mapper.writeValueAsString(sab));
            	}
            	
            } else {
                logger.error("Error data, unknown data:" + mapper.writeValueAsString(sab));
            }
        } catch (IOException e) {
            logger.error("Error message {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
