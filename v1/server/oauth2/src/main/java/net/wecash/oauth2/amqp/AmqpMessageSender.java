package net.wecash.oauth2.amqp;

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

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AmqpMessageSender.class);

    public void publishTokenMessage(Object o) {
        Assert.notNull(o);
        template.convertAndSend(Exchanges.TOKEN, RoutingKeys.TOKEN+".add", o);
    }
}
