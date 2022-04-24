package co.com.meli.usecase.mutant;

import co.com.meli.model.mutant.Mutant;
import co.com.meli.model.mutant.gateways.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MutantUseCaseTest {

    @InjectMocks
    private MutantUseCase mutantUseCase;

    @Mock
    private MutantRepository mutantRepository;

    final String[] dnaMutantArray = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    final String[] dnaHumanArray = {"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};
    final String[] dnaErrorArray = {"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "TCACTG"};

    final String dnaMutant = "[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]";
    final String dnaHuman = "[GTGCGA, CAGTGC, TTATGT, AGAAGG, CACCTA, TCACTG]";

    private final Mutant mutant = Mutant
            .builder()
            .dna(dnaMutant)
            .isMutant(true)
            .build();

    private final Mutant human = Mutant
            .builder()
            .dna(dnaHuman)
            .isMutant(false)
            .build();

    @BeforeEach
    public void init() {

        final Mono<Mutant> mutantMono = Mono.just(mutant);
        final Mono<Mutant> humanMono = Mono.just(human);

        when(mutantRepository.findByDna(dnaMutant)).thenReturn(mutantMono);
        when(mutantRepository.findByDna(dnaHuman)).thenReturn(humanMono);
        when(mutantRepository.save(mutant)).thenReturn(mutantMono);
        when(mutantRepository.save(human)).thenReturn(humanMono);

    }

    @Test
    public void findByDnaIsMutantTest() {
        final Mono<Mutant> mutantMono = mutantUseCase.isMutant(dnaMutantArray);

        StepVerifier.create(mutantMono)
                .assertNext(mutant1 -> {
                    assertThat(mutant1).isNotNull();
                    assertThat(mutant1.getIsMutant()).isTrue();
                }).verifyComplete();
    }

    @Test
    public void findByDnaIsNotMutantTest() {

        final Mono<Mutant> mutantMono = mutantUseCase.isMutant(dnaHumanArray);

        StepVerifier.create(mutantMono)
                .assertNext(mutant1 -> {
                    assertThat(mutant1).isNotNull();
                    assertThat(mutant1.getIsMutant()).isFalse();
                }).verifyComplete();
    }

    @Test
    public void errorInDnaTest() {

        final Mono<Mutant> mutantMono = mutantUseCase.isMutant(dnaErrorArray);

        StepVerifier.create(mutantMono)
                .expectNextCount(0)
                .verifyComplete();
    }

}
