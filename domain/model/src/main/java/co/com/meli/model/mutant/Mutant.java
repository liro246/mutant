package co.com.meli.model.mutant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Mutant {
    private final Long id;
    private final String dna;
    private final Boolean isMutant;
}
