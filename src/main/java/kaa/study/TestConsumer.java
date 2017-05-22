package kaa.study;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * Created by user on 22.05.17.
 */
public class TestConsumer implements Runnable {

    private int sessionMode;
    private String queueName;
    private int messagesCount;

    public TestConsumer(int sessionMode, String queueName, int messagesCount) {
        this.sessionMode = sessionMode;
        this.queueName = queueName;
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
            MessageConsumer consumer = session.createConsumer(destination);
            int count = 0;
            while (true) {
                Message message = consumer.receive();
                if(message instanceof TextMessage) {
                    count++;
                    if(sessionMode == Session.CLIENT_ACKNOWLEDGE &&
                            (count == messagesCount)) {
                        message.acknowledge();
                    }
                    if(sessionMode == Session.SESSION_TRANSACTED &&
                            (count % 100 == 0 || count == messagesCount-1)) {
                        session.commit();
                    }
                    if (count == messagesCount) {
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("Time for consumer: " + queueName + ": " + (System.currentTimeMillis() - startTime));
    }
}
