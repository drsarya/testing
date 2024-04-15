package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testing.model.ADBackendCommand;

import java.nio.ByteBuffer;
import java.util.List;

public class TestCommandStructure {

    @ParameterizedTest
    @MethodSource("valueSource")
    public void testCommandStructure(byte[] commandValue) {
        ADBackendCommand command = new ADBackendCommand(commandValue);
        command.deserialize();

        Assertions.assertEquals("AD", command.getCommandType());
        Assertions.assertEquals("pasha", command.getUsername());
        Assertions.assertEquals("passwd", command.getPassword());

        Assertions.assertEquals(5, command.getUsername().length(), "Username");
        Assertions.assertEquals(6, command.getPassword().length(), "Password");
    }

    public static List<Arguments> valueSource() {
        String username = "pasha";
        String password = "passwd";
        char s1 = 'A';
        char s2 = 'D';

        int length = 4 // char + char
                + 4 // username length
                + 4 // password length
                + password.getBytes().length + username.getBytes().length;
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putChar(s1);
        allocate.putChar(s2);
        allocate.putInt(username.length());
        allocate.put(username.getBytes());
        allocate.putInt(password.length());
        allocate.put(password.getBytes());
        return List.of(Arguments.of(allocate.array()));
    }
}
