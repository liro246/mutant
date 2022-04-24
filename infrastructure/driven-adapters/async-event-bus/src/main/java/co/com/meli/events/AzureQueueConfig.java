package co.com.meli.events;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureQueueConfig {

    @Value("${azurequeue-storageConnectionString}")
    private String storageConnectionString;

    @Bean
    public CloudQueueClient azureTemplate() {
        CloudQueueClient queueClient = null;
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            queueClient = storageAccount.createCloudQueueClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queueClient;
    }

}