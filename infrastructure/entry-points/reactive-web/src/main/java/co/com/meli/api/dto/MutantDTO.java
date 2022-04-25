package co.com.meli.api.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MutantDTO {
    @NonNull
    @ApiModelProperty(position = 1,
            dataType = "Array String", value = "Secuencia DNA Humano para validar si es Mutante <br>",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]", required = true)
    private String[] dna;
}
