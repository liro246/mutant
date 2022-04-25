package co.com.meli.model.mutant.gateways;

import co.com.meli.model.mutant.Mutant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MutantRepository {
    Mono<Mutant> findByDna(String dna);
    Mono<Mutant> save(Mutant mutant);
    Flux<Mutant> findAll();
}
