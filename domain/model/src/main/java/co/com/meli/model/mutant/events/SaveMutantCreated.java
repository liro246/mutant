package co.com.meli.model.mutant.events;

import co.com.meli.model.events.gateways.Event;
import co.com.meli.model.mutant.Mutant;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class SaveMutantCreated implements Event {

    private static final String EVENT_NAME = "meli-mutant-save-dna";
    private final Mutant data;
    private final Date date;

    @Override
    public String name() {
        return EVENT_NAME;
    }

    @Override
    public Object data() {
        return data;
    }
}
