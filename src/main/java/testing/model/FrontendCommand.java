package testing.model;

public class FrontendCommand extends Command {


    public FrontendCommand(byte[] value) {
        super(value);
    }

    @Override
    protected void validateLength(byte[] value) {
        if (value.length < 2)
            throw new IllegalArgumentException("Serialization error: Minimum buffer size exceeded 2 byte");

        if (value.length > 300)
            throw new IllegalArgumentException("Serialization error: Maximum buffer size exceeded 100 bytes");
    }
}
