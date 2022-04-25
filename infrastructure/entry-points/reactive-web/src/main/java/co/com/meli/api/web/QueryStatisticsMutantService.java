package co.com.meli.api.web;

import co.com.meli.api.config.SwaggerConfiguration;
import co.com.meli.model.mutant.StatisticsMutant;
import co.com.meli.usecase.mutant.QueryStatisticsMutantUseCase;
import io.swagger.annotations.*;
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
@Api(tags = {SwaggerConfiguration.MUTANT_TAG})
public class QueryStatisticsMutantService {

    private final QueryStatisticsMutantUseCase queryStatisticsMutantUseCase;

    @PostMapping()
    @ApiOperation(value = "Servicio que arroja datos estadisticos de cuantos de los humanos consultados  son MUTANTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-api-key", value = "Control de acceso API Key", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Api Key de API Management."),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "DNA humano ingresado es MUTANTE."),
            @ApiResponse(code = 500, message = "Error al calcular las estadisticas"),
            @ApiResponse(code = 401, message = "El API Key ingresado no es valido")
    })
    public Mono<ResponseEntity<StatisticsMutant>> queryStatisticsMutant() {
        return queryStatisticsMutantUseCase.queryStatisticsMutant()
                .map(statisticsMutant -> ResponseEntity.status(HttpStatus.OK).body(statisticsMutant))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatisticsMutant.builder().build())));
    }
}
