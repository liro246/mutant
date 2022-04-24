package co.com.meli.api.healthcheck;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HealthService.class)
public class HealthServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void healthTest() {

        final WebTestClient.ResponseSpec spec = webTestClient.get().uri("/health")
                .exchange();

        spec.expectBody(String.class).consumeWith(stringEntityExchangeResult -> {
           String body= stringEntityExchangeResult.getResponseBody();
           assertThat(body).contains("Status Ok");
        });

    }
}
