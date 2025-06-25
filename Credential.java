public class Credential {
    String app;
    String username;
    String password;

    public Credential(String app, String username, String password) {
        this.app = app;
        this.username = username;
        this.password = password;
    }

    public String toEncryptedString() throws Exception {
        return AESUtil.encrypt(app) + "," +
               AESUtil.encrypt(username) + "," +
               AESUtil.encrypt(password);
    }

    public static Credential fromEncryptedString(String encryptedLine) throws Exception {
        String[] parts = encryptedLine.split(",");
        return new Credential(
            AESUtil.decrypt(parts[0]),
            AESUtil.decrypt(parts[1]),
            AESUtil.decrypt(parts[2])
        );
    }
}
