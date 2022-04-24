package co.com.meli.usecase.mutant;

import co.com.meli.model.mutant.Mutant;
import co.com.meli.model.mutant.StatisticsMutant;
import co.com.meli.model.mutant.gateways.MutantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class QueryStatisticsMutantUseCase {

    private final MutantRepository mutantRepository;

    public Mono<StatisticsMutant> queryStatisticsMutant() {

        Flux<Mutant> mutantFlux = mutantRepository.findAll();

        return mutantFlux.count().flatMap(totalRecords ->
                mutantFlux.filter(Mutant::getIsMutant)
                        .count()
                        .map(totalMutants ->
                                StatisticsMutant.builder()
                                        .count_mutant_dna(totalMutants)
                                        .count_human_dna(totalRecords)
                                        .ratio(totalRecords > 0 ? totalMutants.doubleValue() / totalRecords.doubleValue() : 0)
                                        .build()
                        )
        );
    }
}
