package net.wecash.server.amqp;

import net.wecash.common.amqp.Exchanges;
import net.wecash.common.amqp.RoutingKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 
 * @author xkk
 *
 */
@Service
public class AmqpMessageSender {

    @Autowired
    AmqpTemplate template;

	private static Logger logger = LoggerFactory.getLogger(AmqpMessageSender.class);

    public void publishTriggerSnsDataCollectorMessage(Object o){
    	Assert.notNull(o);
    	logger.trace("publish trigger sns data collector:" + o.toString());
    	template.convertAndSend(Exchanges.SNS_COLLECTOR, RoutingKeys.SNS_COLLECTOR + ".data", o);
    }

    public void publishTriggerAnalyzerMessage(Object o){
    	Assert.notNull(o);
    	logger.trace("publish analyzer:" + o.toString());
    	template.convertAndSend(Exchanges.USER_ANALYZER, RoutingKeys.USER_ANALYZER, o);
    }
}
