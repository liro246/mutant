package co.com.meli.usecase.mutant;

import co.com.meli.model.mutant.Mutant;
import co.com.meli.model.mutant.MutantOperations;
import co.com.meli.model.mutant.gateways.MutantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Log
@RequiredArgsConstructor
public class MutantUseCase {
    private final MutantRepository mutantRepository;

    public Mono<Mutant> isMutant(String[] dna) {

        Supplier<Stream<String>> streamSupplier
                = () -> Stream.of(dna);

        if (MutantOperations.isInvalidArray(streamSupplier.get(), dna.length))
            return Mono.empty();

        String dnaToString = Arrays.toString(dna);
        Mutant mutant = Mutant.builder().dna(dnaToString).isMutant(this.isMutant(streamSupplier, dna.length)).build();

        mutantRepository.findByDna(dnaToString)
                .map(mutantMap -> mutant)
                .switchIfEmpty(mutantRepository.save(mutant))
                .onErrorResume(throwable -> {
                    log.severe(throwable.getMessage());
                    return Mono.empty();
                }).subscribe();

        return Mono.just(mutant);
    }


    private boolean isMutant(Supplier<Stream<String>> streamSupplier, int lng) {
        return
                validateMutant(streamSupplier.get()) +
                        validateMutant(MutantOperations.streamVertical(streamSupplier.get(), lng)) +
                        validateMutant(MutantOperations.streamObliqueLeft(streamSupplier.get(), lng)) +
                        validateMutant(MutantOperations.streamObliqueRight(streamSupplier.get(), lng)) > 1;
    }

    private long validateMutant(Stream<String> streamMutant) {
        return streamMutant.filter(s -> s.contains("AAAA") || s.contains("CCCC") || s.contains("GGGG") || s.contains("TTTT")).count();
    }

}
