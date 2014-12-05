package verticles;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.core.logging.impl.LoggerFactory;
import org.vertx.java.platform.Verticle;

/**
 * Created by sandra.kriemann on 28.11.2014.
 */
public class ConnectWorkerVerticle extends Verticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectWorkerVerticle.class);

    EventBus eventBus;

    public void start() {
        eventBus = vertx.eventBus();
        eventBus.registerHandler(getCurrentVerticleAddress(), new MessageHandler());
    }

    private String getCurrentVerticleAddress() {
        return this.getClass().getSimpleName();
    }

    public void stop() {
        System.out.println("Stopping KafkaWorkerVerticle");
    }


    private class MessageHandler implements Handler<Message<JsonObject>> {
        @Override
        public void handle(Message<JsonObject> jsonObjectMessage) {
            JsonObject object = jsonObjectMessage.body();
            String message = object.getString("Data");
            LOGGER.info(message);
        }
    }
}