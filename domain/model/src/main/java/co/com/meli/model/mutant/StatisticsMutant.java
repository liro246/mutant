package co.com.meli.model.mutant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class StatisticsMutant {
    private final long count_mutant_dna;
    private final long count_human_dna;
    private final double ratio;
}
