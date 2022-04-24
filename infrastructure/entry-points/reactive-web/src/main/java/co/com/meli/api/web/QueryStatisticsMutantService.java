package co.com.meli.api.web;

import co.com.meli.model.mutant.StatisticsMutant;
import co.com.meli.usecase.mutant.QueryStatisticsMutantUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Log
@Controller
@RequestMapping("/stats")
@RequiredArgsConstructor
public class QueryStatisticsMutantService {

    private final QueryStatisticsMutantUseCase queryStatisticsMutantUseCase;

    @PostMapping()
    public Mono<ResponseEntity<StatisticsMutant>> queryStatisticsMutant() {
        return queryStatisticsMutantUseCase.queryStatisticsMutant()
                .map(statisticsMutant -> ResponseEntity.status(HttpStatus.OK).body(statisticsMutant))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatisticsMutant.builder().build())));
    }
}
