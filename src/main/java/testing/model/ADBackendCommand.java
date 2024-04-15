package testing.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ADBackendCommand extends BackendCommand {
    public String commandType;
    public String username;
    public String password;


    public ADBackendCommand(byte[] value) {
        super(value);
    }

    public String getCommandType() {
        return commandType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void deserialize() {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(this.getValue());

            char symbol1 = buffer.getChar();
            char symbol2 = buffer.getChar();

            int usernameLength = buffer.getInt();
            byte[] usernameArray = new byte[usernameLength];
            buffer.get(usernameArray);

            int passwordLength = buffer.getInt();
            byte[] passwordArray = new byte[passwordLength];
            buffer.get(passwordArray);

            this.commandType = String.valueOf(new char[]{symbol1, symbol2});
            this.username = new String(usernameArray, StandardCharsets.UTF_8);
            this.password = new String(passwordArray, StandardCharsets.UTF_8);
        } catch (Exception | Error e) {
            throw new RuntimeException("Deserialization error: " + e.getMessage());
        }
    }
}
