package co.com.meli.jpa.mutant;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "MUTANT")
public class MutantData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DNA")
    private String dna;
    @Column(name = "ISMUTANT")
    private Boolean isMutant;

}
