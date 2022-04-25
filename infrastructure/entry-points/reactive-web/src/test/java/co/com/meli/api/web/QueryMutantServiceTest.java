package co.com.meli.api.web;

import co.com.meli.model.mutant.Mutant;
import co.com.meli.usecase.mutant.MutantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(QueryMutantService.class)
public class QueryMutantServiceTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MutantUseCase mutantUseCase;

    final String[] dnaMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    final String[] dnaHuman = {"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};

    private final Mutant mutant = Mutant
            .builder()
            .dna(Arrays.toString(dnaMutant))
            .isMutant(true)
            .build();

    private final Mutant human = Mutant
            .builder()
            .dna(Arrays.toString(dnaHuman))
            .isMutant(false)
            .build();


    @BeforeEach
    public void init() throws ParseException {

        final String[] dnaMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        final String[] dnaHuman = {"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};

        final Mono<Mutant> mutantMono = Mono.just(mutant);
        final Mono<Mutant> humanMono = Mono.just(human);

        when(mutantUseCase.isMutant(dnaMutant)).thenReturn(mutantMono);
        when(mutantUseCase.isMutant(dnaHuman)).thenReturn(humanMono);
    }

    @Test
    public void queryMutantTest() {

        final StringBuilder requestBody = new StringBuilder()
                .append("{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}");

        webTestClient.post()
                .uri("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(mutantEntityExchangeResult -> {
                    final String body = mutantEntityExchangeResult.getResponseBody();
                    assertThat(body).isNotNull();
                });
    }

    @Test
    public void StatusIsMutantTest() {

        final StringBuilder requestBody = new StringBuilder()
                .append("{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}");

        final WebTestClient.ResponseSpec spec = webTestClient.post().uri("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody.toString())
                .exchange();

        spec.expectBody(String.class).consumeWith(res -> {
            final HttpStatus status = res.getStatus();
            assertThat(status.is2xxSuccessful()).isTrue();
        });

    }

    @Test
    public void StatusNotIsMutantTest() {

        final StringBuilder requestBody = new StringBuilder()
                .append("{\"dna\": [\"GTGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}");

        final WebTestClient.ResponseSpec spec = webTestClient.post().uri("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody.toString())
                .exchange();

        spec.expectBody(String.class).consumeWith(res -> {
            final HttpStatus status = res.getStatus();
            assertThat(status.is4xxClientError()).isTrue();
        });

    }

}
