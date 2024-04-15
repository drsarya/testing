package testing.validator.backend;

import testing.validator.Validator;

public class BackendLengthValidator implements Validator {

    @Override
    public void validate(byte[] value) {
        if (value.length < 4)
            throw new IllegalArgumentException("Deserialization error: Minimum buffer size exceeded 4 byte");
        if (value.length > 300)
            throw new IllegalArgumentException("Deserialization error: Maximum buffer size exceeded 300 bytes");
    }
}
