package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testing.model.BackendCommand;
import testing.model.Command;
import testing.service.BackendCommandProcessor;
import testing.validator.BackendLengthValidator;
import testing.validator.CommandTypeValidator;

import java.util.List;

public class TestCommandValue {

    @Test
    public void testBackendNullCommandValue() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new Command(null){}, "null command value");
        Assertions.assertEquals(NullPointerException.class, exception.getClass(), "check error class");
    }

    @Test
    public void testBackendCommandValue() {
        Command command = new Command(new byte[1]) {
        };
        Assertions.assertTrue(command.getValue() instanceof byte[], "command is byte array");
    }
}
