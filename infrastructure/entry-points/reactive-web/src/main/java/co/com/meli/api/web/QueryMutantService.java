package co.com.meli.api.web;

import co.com.meli.api.dto.MutantDTO;
import co.com.meli.usecase.mutant.MutantUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Log
@Controller
@RequestMapping(value= "/mutant")
@RequiredArgsConstructor
public class QueryMutantService {

    private final MutantUseCase mutantUseCase;

    @PostMapping(value = "/")
    public Mono<ResponseEntity<String>> isMutant(@RequestBody MutantDTO mutantDTO) {
        return mutantUseCase.isMutant(mutantDTO.getDna())
                .map(mutant -> ResponseEntity.status(mutant.getIsMutant().booleanValue() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(""))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body("")));
    }

}
