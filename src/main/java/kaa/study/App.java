package kaa.study;

import javax.jms.DeliveryMode;
import javax.jms.Session;

/*
Применение персистентности - замедляет процесс но сохраняет данные после перезапуска сервера
эффект от dups наблюдается только без персистентости
 */

public class App
{
    public static void main( String[] args )
    {
        int messageCount = 50000;
        int method = Session.SESSION_TRANSACTED;
        switch (method) {
            case Session.SESSION_TRANSACTED:
                /*
                 * Session.SESSION_TRANSACTED - we need commit parts of messages when we send & receive it
                 * It may increase speed
                 */
                thread(new TestConsumer(Session.SESSION_TRANSACTED,
                                "Transacted",
                                messageCount),
                        false);
                thread(new TestProducer(Session.SESSION_TRANSACTED,
                                DeliveryMode.NON_PERSISTENT,
                                "Transacted",
                                messageCount),
                        false);
                break;
            case Session.AUTO_ACKNOWLEDGE:
                /*
                 * Session.AUTO_ACKNOWLEDGE - every sent message acknowledgment personality - it is to slow
                 */
                thread(new TestConsumer(Session.AUTO_ACKNOWLEDGE,
                                "AutoAcknowledge",
                                messageCount),
                        false);
                thread(new TestProducer(Session.AUTO_ACKNOWLEDGE,
                                DeliveryMode.NON_PERSISTENT,
                                "AutoAcknowledge",
                                messageCount),
                        false);
                break;
            case Session.CLIENT_ACKNOWLEDGE:
                /*
                 * Session.CLIENT_ACKNOWLEDGE -  consumes a lot of messages before calling acknowledge
                 * very slow (?)
                 */
                thread(new TestConsumer(Session.CLIENT_ACKNOWLEDGE,
                                "ClientAcknowledge",
                                messageCount),
                        false);
                thread(new TestProducer(Session.CLIENT_ACKNOWLEDGE,
                                DeliveryMode.NON_PERSISTENT,
                                "ClientAcknowledge",
                                messageCount),
                        false);
                break;
            case Session.DUPS_OK_ACKNOWLEDGE:
                /*
                 * Session.CLIENT_ACKNOWLEDGE -  consumes a lot of messages before calling acknowledge
                 * very slow (?)
                 */
                thread(new TestConsumer(Session.DUPS_OK_ACKNOWLEDGE,
                                "DupsOkAcknowledge",
                                messageCount),
                        false);
                thread(new TestProducer(Session.DUPS_OK_ACKNOWLEDGE,
                                DeliveryMode.NON_PERSISTENT,
                                "DupsOkAcknowledge",
                                messageCount),
                        false);
        }
    }

    private static void thread(Runnable runnable, boolean daemon) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(daemon);
        thread.start();
    }
}
