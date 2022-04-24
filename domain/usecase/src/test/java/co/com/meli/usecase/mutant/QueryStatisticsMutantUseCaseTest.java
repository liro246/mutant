package co.com.meli.usecase.mutant;

import co.com.meli.model.mutant.Mutant;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class QueryStatisticsMutantUseCaseTest {

    @InjectMocks
    private QueryStatisticsMutantUseCase queryStatisticsMutantUseCase;

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

}
