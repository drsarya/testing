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

public class TestCommandValidator {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testMinValidationBackendCommandValue(int size) {
        BackendCommand command = new BackendCommand(new byte[size]);
        BackendCommandProcessor commandProcessor = new BackendCommandProcessor(List.of(new BackendLengthValidator()));
        Exception exception = Assertions.assertThrows(Exception.class, () -> commandProcessor.validateCommand(command), "min byte array length");
        Assertions.assertEquals("Deserialization error: Minimum buffer size exceeded 4 byte", exception.getMessage(), "check error message");
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 400})
    public void testMaxValidationBackendCommandValue(int size) {
        BackendCommand command = new BackendCommand(new byte[size]);
        BackendCommandProcessor commandProcessor = new BackendCommandProcessor(List.of(new BackendLengthValidator()));
        Exception exception = Assertions.assertThrows(Exception.class, () -> commandProcessor.validateCommand(command), "min byte array length");
        Assertions.assertEquals("Deserialization error: Maximum buffer size exceeded 300 bytes", exception.getMessage(), "check error message");
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 100, 299, 300})
    public void testValidBackendCommandValue(int size) {
        BackendCommand command = new BackendCommand(new byte[size]);
        BackendCommandProcessor commandProcessor = new BackendCommandProcessor(List.of(new BackendLengthValidator()));
        Assertions.assertDoesNotThrow(() -> commandProcessor.validateCommand(command), "valid backend command");
    }

    @ParameterizedTest
    @ValueSource(strings = {"HH", "BB", "AB", "CA", "ZZ"})
    public void testCommandType(String commandType) {
        BackendCommand command = new BackendCommand(commandType.getBytes());
        BackendCommandProcessor commandProcessor = new BackendCommandProcessor(List.of(new CommandTypeValidator()));
        Assertions.assertDoesNotThrow(() -> commandProcessor.validateCommand(command), "valid command type");
    }

    @ParameterizedTest
    @ValueSource(strings = {"aA", "Aa", "1A", "A1", "aa", "00", ". ", "!п", "гг"})
    public void testNotValidCommandType(String commandType) {
        BackendCommand command = new BackendCommand(commandType.getBytes());
        BackendCommandProcessor commandProcessor = new BackendCommandProcessor(List.of(new CommandTypeValidator()));
        Exception exception = Assertions.assertThrows(Exception.class, () -> commandProcessor.validateCommand(command), "not valid command type");
        Assertions.assertEquals("Error. Wrong command type", exception.getMessage(), "check error message");
    }
}
