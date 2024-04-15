package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testing.model.Command;

public class TestCommandValue {

    @Test
    public void testBackendNullCommandValue() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new Command(null) {
        }, "null command value");
        Assertions.assertEquals(NullPointerException.class, exception.getClass(), "check error class");
    }

    @Test
    public void testBackendCommandValue() {
        Command command = new Command(new byte[1]) {
        };
        Assertions.assertTrue(command.getValue() instanceof byte[], "command is byte array");
    }
}
