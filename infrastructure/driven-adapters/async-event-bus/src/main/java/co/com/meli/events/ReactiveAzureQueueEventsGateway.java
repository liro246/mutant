package co.com.meli.events;

import co.com.meli.model.events.gateways.Event;
import co.com.meli.model.events.gateways.EventsGateway;
import com.google.gson.Gson;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log
@Component
public class ReactiveAzureQueueEventsGateway implements EventsGateway {

    private final CloudQueueClient cloudQueueClient;

    @Autowired
    public ReactiveAzureQueueEventsGateway(CloudQueueClient cloudQueueClient) {
        this.cloudQueueClient = cloudQueueClient;
    }

    @Override
    public Mono<Void> emit(Event event) {
        try {
            String data = new Gson().toJson(event.data());
            CloudQueue queue = cloudQueueClient.getQueueReference(event.name());
            queue.createIfNotExists();
            CloudQueueMessage message = new CloudQueueMessage(data);
            queue.addMessage(message);
        } catch (Exception e) {
            log.severe(String.format("Error emitiendo evento azure - Mensaje: %s , StackTrace: %s", e.getMessage(), e.getStackTrace()));
        }
        return Mono.empty();
    }
}
