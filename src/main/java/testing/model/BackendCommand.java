package testing.model;

public class BackendCommand extends Command {


    public BackendCommand(byte[] value) {
        super(value);
    }

    @Override
    protected void validateLength(byte[] value) {
        if (value.length < 2)
            throw new IllegalArgumentException("Deserialization error: Minimum buffer size exceeded 2 byte");
        if (value.length > 300)
            throw new IllegalArgumentException("Deserialization error: Maximum buffer size exceeded 300 bytes");
    }
}
