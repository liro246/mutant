package co.com.meli.api.web;

import co.com.meli.model.mutant.StatisticsMutant;
import co.com.meli.usecase.mutant.QueryStatisticsMutantUseCase;
import lombok.Data;
import lombok.NoArgsConstructor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(QueryStatisticsMutantService.class)
public class QueryStatisticsMutantServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private QueryStatisticsMutantUseCase queryStatisticsMutantUseCase;

    private final StatisticsMutant statisticsMutant = StatisticsMutant
            .builder()
            .count_mutant_dna(40)
            .count_human_dna(100)
            .ratio(0.4)
            .build();

    @BeforeEach
    public void init() throws ParseException {
        final Mono<StatisticsMutant> statisticsMutantMono = Mono.just(
                statisticsMutant
        );
        when(queryStatisticsMutantUseCase.queryStatisticsMutant()).thenReturn(statisticsMutantMono);
    }

    @Test
    public void queryStatisticsMutantTest() {

        webTestClient.post()
                .uri("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatisticsMutantDTO.class)
                .consumeWith(mutantEntityExchangeResult -> {
                    final StatisticsMutantDTO body = mutantEntityExchangeResult.getResponseBody();
                    assertThat(body).isNotNull();
                    assertThat(body).extracting(StatisticsMutantDTO::getRatio).toString().equals("0.4");
                });
    }

    @Test
    public void StatusStatisticsMutantTest() {

        final WebTestClient.ResponseSpec spec = webTestClient.post().uri("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        spec.expectBody(StatisticsMutantDTO.class).consumeWith(res -> {
            final HttpStatus status = res.getStatus();
            assertThat(status.is2xxSuccessful()).isTrue();
        });
        verify(queryStatisticsMutantUseCase).queryStatisticsMutant();

    }

    @Data
    @NoArgsConstructor
    private static class StatisticsMutantDTO {
        private long count_mutant_dna;
        private long count_human_dna;
        private double ratio;

    }

}
