package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testing.model.ADBackendCommand;

import java.nio.ByteBuffer;
import java.util.List;

public class TestCommandStructure {

    public static List<Arguments> getValidCommandSource() {
        return List.of(getValidCommand1(), getValidCommand2());
    }

    public static List<Arguments> getNotValidCommandSource() {
        return List.of(getNotValidCommand2(), getNotValidCommand3());
    }

    private static Arguments getValidCommand1() {
        char s1 = 'A';
        char s2 = 'D';
        String username = "pasha";
        String password = "passwd";
        byte[] usernameBytes = username.getBytes();
        byte[] passwordBytes = password.getBytes();

        // наполнение буфера
        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.putChar(s1);
        allocate.putChar(s2);
        allocate.putInt(usernameBytes.length);
        allocate.put(usernameBytes);
        allocate.putInt(passwordBytes.length);
        allocate.put(passwordBytes);
        return Arguments.of(allocate.array(), new String[]{"AD", username, password});
    }

    private static Arguments getValidCommand2() {
        char s1 = 'K';
        char s2 = 'K';
        String username = "p";
        String password = "w";
        byte[] usernameBytes = username.getBytes();
        byte[] passwordBytes = password.getBytes();

        // наполнение буфера
        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.putChar(s1);
        allocate.putChar(s2);
        allocate.putInt(usernameBytes.length);
        allocate.put(usernameBytes);
        allocate.putInt(passwordBytes.length);
        allocate.put(passwordBytes);
        return Arguments.of(allocate.array(), new String[]{"KK", username, password});
    }

    private static Arguments getNotValidCommand2() {
        char s1 = 'A';
        char s2 = 'D';
        String username = "pasha";
        String password = "passwd";
        byte[] usernameBytes = username.getBytes();
        byte[] passwordBytes = password.getBytes();

        // наполнение буфера
        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.putChar(s1);
        allocate.putChar(s2);
        allocate.putInt(-1); // negative length
        allocate.put(usernameBytes);
        allocate.putInt(-1); // negative length
        allocate.put(passwordBytes);
        return Arguments.of(allocate.array());
    }

    private static Arguments getNotValidCommand3() {
        char s1 = 'A';
        char s2 = 'D';
        String username = "pasha";
        String password = "passwd";
        byte[] usernameBytes = username.getBytes();
        byte[] passwordBytes = password.getBytes();

        // наполнение буфера
        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.putChar(s1);
        allocate.putChar(s2);
        allocate.putInt(0); // передаём нулевую длину поля, но со значением
        allocate.put(usernameBytes);
        allocate.putInt(0); // передаём нулевую длину поля, но со значением
        allocate.put(passwordBytes);
        return Arguments.of(allocate.array());
    }

    @ParameterizedTest
    @MethodSource("getValidCommandSource")
    public void testCommandStructure(byte[] value, String[] expectedValue) {
        ADBackendCommand command = new ADBackendCommand(value);
        command.deserialize();

        Assertions.assertEquals(expectedValue[0], command.getCommandType(), "Command type");
        Assertions.assertEquals(expectedValue[1], command.getUsername(), "Check username value");
        Assertions.assertEquals(expectedValue[2], command.getPassword(), "Check password value");

        Assertions.assertEquals(expectedValue[1].length(), command.getUsername().length(), "Username length");
        Assertions.assertEquals(expectedValue[2].length(), command.getPassword().length(), "Password length");
    }

    @ParameterizedTest
    @MethodSource("getNotValidCommandSource")
    public void testCommandNotValidStructure(byte[] value) {
        ADBackendCommand command = new ADBackendCommand(value);
        Exception exception = Assertions.assertThrows(Exception.class, command::deserialize, "check not valid source");
        Assertions.assertTrue(exception.getMessage().startsWith("Deserialization error: "), "check error message");
    }
}
