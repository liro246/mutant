package co.com.meli.api.healthcheck;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HealthService {
    @GetMapping()
    @ApiOperation(value = "Servicio de estado de la aplicacion")
    public Mono<String> health(){
        return Mono.just("Status Ok");
    }
}
