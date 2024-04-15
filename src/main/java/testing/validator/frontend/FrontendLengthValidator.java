package testing.validator.frontend;

import testing.validator.Validator;

public class FrontendLengthValidator implements Validator {

    @Override
    public void validate(byte[] value) {
        if (value.length < 4)
            throw new IllegalArgumentException("Serialization error: Minimum buffer size exceeded 4 byte");

        if (value.length > 300)
            throw new IllegalArgumentException("Serialization error: Maximum buffer size exceeded 100 bytes");
    }
}
