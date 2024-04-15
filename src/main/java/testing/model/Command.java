package testing.model;

import java.util.Objects;

public abstract class Command {

    private final byte[] value;

    protected Command(byte[] value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }
}
