package co.com.meli.jpa;

import co.com.meli.jpa.helper.AdapterOperations;
import co.com.meli.jpa.mutant.MutantData;
import co.com.meli.model.mutant.Mutant;
import co.com.meli.model.mutant.gateways.MutantRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public class JPARepositoryAdapter extends AdapterOperations<Mutant, MutantData, String, JPARepository> implements MutantRepository {

    public JPARepositoryAdapter(JPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Mutant.MutantBuilder.class).build());
    }

    @Override
    public Mono<Mutant> findByDna(String dna) {
        Optional<MutantData> mutantDataOptional = repository.findByDna(dna);
        return mutantDataOptional.map(mutantData -> Mono.justOrEmpty(toEntity(mutantData))).orElseGet(Mono::empty);
    }

}
