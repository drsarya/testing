package testing.model;

public abstract class Command {

    private final byte[] value;

    protected Command(byte[] value) {
        validateLength(value);
        validateCommandType(value);
        this.value = value;
    }

    protected void validateCommandType(byte[] value) {
        char c1 = (char) value[0];
        char c2 = (char) value[1];

        if (!Character.isAlphabetic(c1) || !Character.isAlphabetic(c2)) throw new IllegalArgumentException("Error. Wrong command type");
        if (!Character.isUpperCase(c1) || !Character.isUpperCase(c2)) throw new IllegalArgumentException("Error. Wrong command type");
    }

    public byte[] getValue() {
        return value;
    }

    protected abstract void validateLength(byte[] value);
}
