package net.wecash.server.amqp;

import net.wecash.server.analyzer.UserMatchCalculator;
import net.wecash.server.analyzer.bean.ScoreTriggerBean;

import org.hibernate.mapping.Map;
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
public class PortraitMessageHandler implements MessageListener, ChannelAwareMessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(PortraitMessageHandler.class);

    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private UserMatchCalculator userMatchCalculator;
    
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		routeAnalyzer(message.getBody(), message.getMessageProperties().getReceivedRoutingKey());
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
	}
	
	private void routeAnalyzer(byte[] body, String routingKey){
		try{
			mapper.readValue(body, Map.class);
			ScoreTriggerBean atb = mapper.readValue(body, ScoreTriggerBean.class); 
			logger.info("get analyzer trigger bean:" + atb);
//			userMatchCalculator.analyzer(atb);
		}catch(Exception e){
			logger.error("accept error analyzer message:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
