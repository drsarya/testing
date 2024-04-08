package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testing.model.BackendCommand;
import testing.model.Command;

public class TestCommand {

    @Test
    public void testFrontendCommandValue() {
        Command command = new Command(new byte[1]) {
            @Override
            protected void validateLength(byte[] value) {
            }
        };
        Assertions.assertTrue(command.getValue() instanceof byte[], "command is byte array");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testMinValidationBackendCommandValue(int size) {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new BackendCommand(new byte[size]), "min byte array length");
        Assertions.assertEquals("Deserialization error: Minimum buffer size exceeded 2 byte", exception.getMessage(), "check error message");
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 400})
    public void testMaxValidationBackendCommandValue(int size) {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new BackendCommand(new byte[size]), "min byte array length");
        Assertions.assertEquals("Deserialization error: Maximum buffer size exceeded 300 bytes", exception.getMessage(), "check error message");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 100, 299, 300})
    public void testValidBackendCommandValue(int size) {
        Assertions.assertDoesNotThrow(() -> new BackendCommand(new byte[size]), "min byte array length");
    }

    @ParameterizedTest
    @ValueSource(strings = {"AA", "BB", "AB", "CA", "ZZ"})
    public void testCommandType(String commandType) {
        byte[] value = commandType.getBytes();
        Assertions.assertDoesNotThrow(() -> new BackendCommand(value), "valid command type");
    }

    @ParameterizedTest
    @ValueSource(strings = {"aA", "Aa", "1A", "A1", "aa", "00"})
    public void testNotValidCommandType(String commandType) {
        byte[] value = commandType.getBytes();
        Exception exception = Assertions.assertThrows(Exception.class, () -> new BackendCommand(value), "not valid command type");
        Assertions.assertEquals("Error. Wrong command type", exception.getMessage(), "check error message");
    }
}
