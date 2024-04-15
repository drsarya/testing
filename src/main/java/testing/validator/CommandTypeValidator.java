package testing.validator;

public class CommandTypeValidator implements Validator {

    @Override
    public void validate(byte[] value) {
        char c1 = (char) value[0];
        char c2 = (char) value[1];

        if (!Character.isAlphabetic(c1) || !Character.isAlphabetic(c2))
            throw new IllegalArgumentException("Error. Wrong command type");
        if (!Character.isUpperCase(c1) || !Character.isUpperCase(c2))
            throw new IllegalArgumentException("Error. Wrong command type");
        if (!isLatin(c1) || !isLatin(c2))
            throw new IllegalArgumentException("Error. Wrong letter in command");
    }

    private boolean isLatin(char val) {
        return val >= 'A' && val <= 'Z';
    }
}
