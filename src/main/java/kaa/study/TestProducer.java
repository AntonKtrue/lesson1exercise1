package kaa.study;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * Created by user on 22.05.17.
 */
public class TestProducer implements Runnable{

    private int sessionMode;
    private String queueName;
    private int deliveryMode;
    private int messagesCount;

    public TestProducer(int sessionMode, int deliveryMode, String queueName, int messagesCount) {
        this.sessionMode = sessionMode;
        this.queueName = queueName;
        this.deliveryMode = deliveryMode;
        this.messagesCount = messagesCount;
    }

    public void run() {
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = null;
        Session session = null;

        long startTime = System.currentTimeMillis();
        try {
            connection = cf.createConnection();
            connection.start();
            session = connection.createSession(sessionMode == Session.SESSION_TRANSACTED,
                    sessionMode);
            Destination destination = new ActiveMQQueue(queueName);
            MessageProducer producer= session.createProducer(destination);
            //if(sessionMode == Session.DUPS_OK_ACKNOWLEDGE) {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//            }
            for(int i = 0; i < messagesCount; i++) {
                Message message = session.createTextMessage("message " + i);
                producer.send(message);
                if(sessionMode == Session.SESSION_TRANSACTED &&
                        (i % 100 == 0 || i == messagesCount-1 )) {
                    session.commit();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("Time for producer: " + queueName + ": " + (System.currentTimeMillis() - startTime));
    }
}
