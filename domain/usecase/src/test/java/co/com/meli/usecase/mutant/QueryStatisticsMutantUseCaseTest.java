package co.com.meli.usecase.mutant;

import co.com.meli.model.mutant.Mutant;
import co.com.meli.model.mutant.StatisticsMutant;
import co.com.meli.model.mutant.gateways.MutantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class QueryStatisticsMutantUseCaseTest {

    @InjectMocks
    private QueryStatisticsMutantUseCase queryStatisticsMutantUseCase;

    @InjectMocks
    private QueryStatisticsMutantUseCase queryStatisticsMutantUseCaseTwo;

    @Mock
    private MutantRepository mutantRepository;

    final String dnaMutant = "[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]";
    final String dnaHuman = "[GTGCGA, CAGTGC, TTATGT, AGAAGG, CACCTA, TCACTG]";

    private final Mutant mutant = Mutant
            .builder()
            .id(1L)
            .dna(dnaMutant)
            .isMutant(true)
            .build();

    private final Mutant human = Mutant
            .builder()
            .id(2L)
            .dna(dnaHuman)
            .isMutant(false)
            .build();

    @Test
    public void findAllTest() {

        final Flux<Mutant> mutantFlux = Flux.just(
                mutant,
                human
        );

        when(mutantRepository.findAll()).thenReturn(mutantFlux);

        final Mono<StatisticsMutant> statisticsMutantMono = queryStatisticsMutantUseCase.queryStatisticsMutant();

        StepVerifier.create(statisticsMutantMono)
                .assertNext(statisticsMutant -> {
                    assertThat(statisticsMutant).isNotNull();
                    assertThat(statisticsMutant.getCount_mutant_dna()).isEqualTo(1);
                    assertThat(statisticsMutant.getCount_human_dna()).isEqualTo(2);
                    assertThat(statisticsMutant.getRatio()).isEqualTo(0.5);
                }).verifyComplete();
    }
    @Test
    public void errorFindAllTest() {

        when(mutantRepository.findAll()).thenReturn(Flux.error(new Throwable("error al consultar")));

        final Mono<StatisticsMutant> statisticsMutantMono = queryStatisticsMutantUseCaseTwo.queryStatisticsMutant();

        StepVerifier.create(statisticsMutantMono)
                .expectError();
    }

}
