import java.io.*;
import java.util.*;
public class SecurePass {
    static final String FILE_NAME = "credential_store.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n--- SecurePass ---");
            System.out.println("1. Add Credential");
            System.out.println("2. View Credentials");
            System.out.println("3. Delete All Credentials");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> addCredential();
                case 2 -> viewCredentials();
                case 3 -> deleteAll();
                case 4 -> {
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addCredential() throws Exception {
        System.out.print("App Name: ");
        String app = sc.nextLine();
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        Credential cred = new Credential(app, user, pass);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(cred.toEncryptedString());
            bw.newLine();
        }

        System.out.println("✅ Credential saved securely.");
    }

    static void viewCredentials() throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠️ No credentials found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n🔐 Saved Credentials:");
            while ((line = br.readLine()) != null) {
                Credential cred = Credential.fromEncryptedString(line);
                System.out.println("App: " + cred.app + " | Username: " + cred.username + " | Password: " + cred.password);
            }
        }
    }

    static void deleteAll() throws IOException {
        new FileWriter(FILE_NAME, false).close(); // overwrite with empty file
        System.out.println("🗑️ All credentials deleted.");
    }
}
