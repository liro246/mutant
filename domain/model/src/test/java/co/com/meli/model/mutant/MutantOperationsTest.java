package co.com.meli.model.mutant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class MutantOperationsTest {

    @Test
    public void isValidArray(){
        final String[] dnaMutantArray = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        boolean isInvalid= MutantOperations.isInvalidArray(Arrays.stream(dnaMutantArray),dnaMutantArray.length);
        assertThat(isInvalid).isFalse();
    }

    @Test
    public void isInValidArray(){
        final String[] dnaMutantArray = {"AEGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        boolean isInvalid= MutantOperations.isInvalidArray(Arrays.stream(dnaMutantArray),dnaMutantArray.length);
        assertThat(isInvalid).isTrue();
    }

    @Test
    public void isInValidSize(){
        final String[] dnaMutantArray = {"AEGCGA", "CAGTGC", "TTATGT"};
        boolean isInvalid= MutantOperations.isInvalidArray(Arrays.stream(dnaMutantArray),dnaMutantArray.length);
        assertThat(isInvalid).isTrue();
    }

    @Test
    public void  streamVertical() {

        final String[] dnaMutantArray = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        final String[] dnaMutantVertical = {"ACTACT", "TATGCC", "GGAACA", "CTTACC", "GGGGTT", "ACTGAG"};

        Stream<String> streamVertical= MutantOperations.streamVertical(Arrays.stream(dnaMutantArray),dnaMutantArray.length);

        assertThat(streamVertical).isNotNull();
        assertThat(streamVertical.toArray()).isEqualTo(dnaMutantVertical);
    }

    @Test
    public void streamObliqueLeft() {

        final String[] dnaMutantArray = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        final String[] dnaMutantObliqueLeft = {"A", "TC", "GAT", "CGTA", "GTAGC", "AGTACT", "CGACC", "TGCA", "GTC", "AT", "G"};

        Stream<String> streamObliqueLeft= MutantOperations.streamObliqueLeft(Arrays.stream(dnaMutantArray),dnaMutantArray.length);

        assertThat(streamObliqueLeft).isNotNull();
        assertThat(streamObliqueLeft.toArray()).isEqualTo(dnaMutantObliqueLeft);

    }

    @Test
    public void streamObliqueRight() {

        final String[] dnaMutantArray = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        final String[] dnaMutantObliqueRight = {"AAAATG", "TGTGA", "GTGG", "CGT", "GC", "A", "CTACT", "TGCC", "ACA", "CC", "T"};

        Stream<String> streamObliqueRight= MutantOperations.streamObliqueRight(Arrays.stream(dnaMutantArray),dnaMutantArray.length);

        assertThat(streamObliqueRight).isNotNull();
        assertThat(streamObliqueRight.toArray()).isEqualTo(dnaMutantObliqueRight);

    }
}
