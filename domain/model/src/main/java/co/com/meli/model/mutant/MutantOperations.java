package co.com.meli.model.mutant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MutantOperations {

    public static boolean isInvalidArray(Stream<String> stream, int lng) {
        if (lng < 4)
            return true;
        AtomicBoolean isInvalidString = new AtomicBoolean(false);
        stream.forEach(s -> {
            boolean findChart = s.chars()
                    .mapToObj(i -> (char) i)
                    .anyMatch(l -> l != 'A' && l != 'T' && l != 'C' && l != 'G');
            if (findChart || s.length() != lng) isInvalidString.set(true);
        });
        return isInvalidString.get();
    }

    public static Stream<String> streamVertical(Stream<String> streamMutant, int lng) {

        String[] dnaVertical = new String[lng];
        streamMutant.forEach(s -> {
            AtomicInteger position = new AtomicInteger(0);
            s.chars()
                    .mapToObj(value -> (char) value)
                    .forEach(character -> {
                        String line = dnaVertical[position.intValue()];
                        if (line != null)
                            dnaVertical[position.intValue()] = line.concat(String.valueOf(character));
                        else
                            dnaVertical[position.intValue()] = String.valueOf(character);

                        position.getAndIncrement();
                    });
        });

        return Arrays.stream(dnaVertical);

    }

    public static Stream<String> streamObliqueLeft(Stream<String> streamMutant, int lng) {

        String[] dnaObliqueLeft = new String[(lng * 2) - 1];
        AtomicInteger positionHorizontal = new AtomicInteger(0);
        streamMutant.forEach(s -> {

            AtomicInteger positionVertical = new AtomicInteger(0);
            s.chars()
                    .mapToObj(value -> (char) value)
                    .forEach(character -> {
                        String line = dnaObliqueLeft[positionHorizontal.intValue() + positionVertical.intValue()];
                        if (line != null)
                            dnaObliqueLeft[positionHorizontal.intValue() + positionVertical.intValue()] = line.concat(String.valueOf(character));
                        else
                            dnaObliqueLeft[positionHorizontal.intValue() + positionVertical.intValue()] = String.valueOf(character);

                        positionVertical.getAndIncrement();
                    });
            positionHorizontal.getAndIncrement();
        });

        return Arrays.stream(dnaObliqueLeft);

    }

    public static Stream<String> streamObliqueRight(Stream<String> streamMutant, int lng) {

        String[] dnaObliqueRight = new String[(lng * 2) - 1];
        AtomicInteger positionHorizontal = new AtomicInteger(0);
        streamMutant.forEach(s -> {

            AtomicInteger positionVertical = new AtomicInteger(0);
            s.chars()
                    .mapToObj(value -> (char) value)
                    .forEach(character -> {
                        int positionArray = positionVertical.intValue() - positionHorizontal.intValue();
                        if (positionArray < 0)
                            positionArray = Math.abs(positionArray) + (lng - 1);

                        String line = dnaObliqueRight[positionArray];
                        if (line != null)
                            dnaObliqueRight[positionArray] = line.concat(String.valueOf(character));
                        else
                            dnaObliqueRight[positionArray] = String.valueOf(character);
                        positionVertical.getAndIncrement();
                    });
            positionHorizontal.getAndIncrement();
        });

        return Arrays.stream(dnaObliqueRight);

    }
}

