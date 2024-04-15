package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testing.model.Command;

public class TestCommandValue {

    @DisplayName("передача в команду null значения приводит к ошибке NullPointerException")
    @Test
    public void testBackendNullCommandValue() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new Command(null) {
        }, "null command value");
        Assertions.assertEquals(NullPointerException.class, exception.getClass(), "check error class");
    }

    @DisplayName("передача в команду не null значения не приводит к генерации исключений")
    @Test
    public void testBackendCommandValue() {
        Command command = new Command(new byte[1]) {
        };
        Assertions.assertTrue(command.getValue() instanceof byte[], "command is byte array");
    }
}
