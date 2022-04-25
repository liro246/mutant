package co.com.meli.api.web;

import co.com.meli.api.dto.MutantDTO;
import co.com.meli.usecase.mutant.MutantUseCase;
import io.swagger.annotations.*;
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
@RequestMapping(value = "/mutant")
@RequiredArgsConstructor
public class QueryMutantService {

    private final MutantUseCase mutantUseCase;

    @PostMapping(value = "/")
    @ApiOperation(value = "Servicio que valida si el dna de un humano es mutante")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-api-key", value = "Control de acceso API Key", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Api Key de API Management."),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "DNA humano ingresado es MUTANTE."),
            @ApiResponse(code = 403, message = "DNA humano ingresado NO es MUTANTE."),
            @ApiResponse(code = 400, message = "DNA ingresado NO es HUMANO."),
            @ApiResponse(code = 401, message = "El API Key ingresado no es valido")
    })
    public Mono<ResponseEntity<String>> isMutant(@RequestBody MutantDTO mutantDTO) {
        return mutantUseCase.isMutant(mutantDTO.getDna())
                .map(mutant -> ResponseEntity.status(mutant.getIsMutant().booleanValue() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(""))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body("")));
    }

}
