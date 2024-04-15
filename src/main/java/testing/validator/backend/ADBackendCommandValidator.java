package testing.validator.backend;

import testing.validator.CommandTypeValidator;
import testing.validator.Validator;

import java.nio.ByteBuffer;

public class ADBackendCommandValidator implements Validator {


    public String commandType;
    public String username;
    public String password;

    public String getCommandType() {
        return commandType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    // Byte2('AD') - код команды.
    //Int32 - длина  имени пользователя.
    //String - имя пользователя.
    //Int32 - длина пароля пользователя.
    //String - пароль пользователя.

    @Override
    public void validate(byte[] value) {
        ByteBuffer buffer = ByteBuffer.wrap(value);

        char symbol1 = buffer.getChar();
        char symbol2 = buffer.getChar();

        int usernameLength = buffer.getInt();
        byte[] usernameArray = new byte[usernameLength];
        buffer.get(usernameArray);

        int passwordLength = buffer.getInt();
        byte[] passwordArray = new byte[passwordLength];
        buffer.get(passwordArray);
    }
}
