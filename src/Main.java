import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, insira sua senha:");
        String senha = scanner.nextLine();

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            // byte[] salt = new byte[16]; // Para gerar um salt do tamanho do MD5
            byte[] salt = new byte[32];
            sr.nextBytes(salt);

            // Método com MD5 inseguro
            /*MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            md.update(senha.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            String mySalt = DatatypeConverter.printHexBinary(salt).toUpperCase();
            System.out.println("O hash MD5 da senha inserida é: " + myHash);
            System.out.println("O salt da senha inserida é: " + mySalt);*/

            // Método com PBKDF2 + Salt - Seguro
            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 1000, 256);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            String myHash = DatatypeConverter.printHexBinary(hash).toUpperCase();
            String mySalt = DatatypeConverter.printHexBinary(salt).toUpperCase();
            System.out.println("O hash PBKDF2 da senha inserida é: " + myHash);
            System.out.println("O salt da senha inserida é: " + mySalt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}