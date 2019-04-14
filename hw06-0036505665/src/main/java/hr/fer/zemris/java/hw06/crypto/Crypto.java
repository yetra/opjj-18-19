package hr.fer.zemris.java.hw06.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is a program that allows the user to encrypt/decrypt a given file using the
 * AES crypto-algorithm and a 128-bit encryption key, or calculate and check the
 * SHA-256 file digest.
 *
 * The desired command is given through the command-line arguments. Valid commands
 * are:
 * checksha file_path - checks the SHA-256 digest of the file specified by file_path
 * encrypt src_file_path dest_file_path - encrypts the given file to dest_file_path
 * decrypt src_file_path dest_file_path - decrypts the given file to dest_file_path
 *
 * @author Bruna Dujmović
 * 
 */
public class Crypto {

    /**
     * The default size of the byte array for reading binary files.
     */
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * The default algorithm for calculating a file's digest.
     */
    private static final String ALGORITHM = "SHA-256";

    /**
     * The main method. Parses the command given in the command-line arguments,
     * prompts the user for further information through the console, and then
     * prints the final results of the command.
     *
     * @param args the command-line arguments which represent
     */
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            switch (args.length) {
                case 2:
                    if (!(args[0].equalsIgnoreCase("checksha"))) {
                        throw new IllegalArgumentException(
                                "Unknown command \"" + args[0] + "\".");
                    }

                    System.out.print("Please provide expected sha-256 digest for "
                            + args[1] + ":\n> ");
                    checksha(args[1], sc.nextLine()); // TODO validate expected sha?
                    break;

                case 3:
                    if (!args[0].equalsIgnoreCase("encrypt") &&
                            !args[0].equalsIgnoreCase("decrypt")) {
                        throw new IllegalArgumentException(
                                "Unknown command \"" + args[0] + "\".");
                    }

                    System.out.print("Please provide password as hex-encoded text " +
                            "(16 bytes, i.e. 32 hex-digits):\n> ");
                    String password = sc.nextLine();
                    System.out.print("Please provide initialization vector as " +
                            "hex-encoded text (32 hex-digits):\n> ");
                    String vector = sc.nextLine();

                    encryptDecrypt(args[1], args[2], password, vector,
                            args[0].equalsIgnoreCase("encrypt"));

                    break;

                default:
                    System.out.println("Wrong number of input arguments: "
                            + args.length + ".");
                    System.exit(1);
            }
        }
    }

    /**
     * Checks if the calculated SHA-256 file digest matches the given digest and
     * prints the results to the console.
     *
     * @param fileName the file whose SHA-256 digest will be checked
     * @param expectedSha the expected SHA-256 digest
     */
    private static void checksha(String fileName, String expectedSha) {
        String actualSha = getDigestOf(fileName);

        System.out.print("Digesting completed. Digest of " + fileName);
        if (actualSha.equals(expectedSha)) {
            System.out.println(" matches expected digest.");
        } else {
            System.out.println(" does not match the expected digest. " +
                    "Digest was: " + actualSha);
        }
    }

    /**
     * Calculates the digest of a given file and returns it as a hex string.
     *
     * @param fileName the file whose digest will be calculated
     * @return the hex string representation of the calculated digest
     * @throws NullPointerException if the obtained digest cannot be represented
     *         as a hex string
     */
    private static String getDigestOf(String fileName) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

            try (InputStream is = new BufferedInputStream(
                    Files.newInputStream(Paths.get(fileName)))) {
                byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
                int bytesRead;

                while((bytesRead = is.read(buff)) > 0) {
                    messageDigest.update(buff, 0, bytesRead);
                }
            }

            return Util.byteToString(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm \"" + ALGORITHM + "\".");
            System.exit(1);

        } catch (IOException e) {
            System.out.println("Cannot read file " + fileName+ " .");
            System.exit(1);
        }

        return null;
    }

    /**
     * Encrypts or decrypts a given file to the specified destination using the
     * given key and initialization vector.
     *
     * @param srcPath the path of the source file
     * @param destPath the path of the destination file
     * @param key the encryption/decryption key
     * @param vector the initialization vector for the encryption/decryption
     * @param encrypt {@code true} if encryption should be performed, {@code false}
     *                if decryption should be perfomed
     * @throws NullPointerException if the given paths, key or vector are {@code null}
     * @throws IllegalArgumentException if the given key or vector cannot be parsed
     *         to a byte array
     */
    private static void encryptDecrypt(String srcPath, String destPath, String key,
                                       String vector, boolean encrypt) {
        Objects.requireNonNull(srcPath);
        Objects.requireNonNull(destPath);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(key), "AES");
            AlgorithmParameterSpec paramSpec = new
                    IvParameterSpec(Util.hexToByte(vector));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
                    keySpec, paramSpec);

            // TODO extract to separate method?
            try (InputStream is = new BufferedInputStream(
                    Files.newInputStream(Paths.get(srcPath)));
                 OutputStream os = new BufferedOutputStream(
                         Files.newOutputStream(Paths.get(destPath)))) {

                byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
                int bytesRead;

                while ((bytesRead = is.read(buff)) > 0) {
                    byte[] output = cipher.update(buff, 0, bytesRead);
                    os.write(output);
                }

                os.write(cipher.doFinal());
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Issue with file " + e.getMessage() + ".");
            System.exit(1);
        }

        System.out.format("%s completed. Generated file %s based on file %s.",
                encrypt ? "Encryption" : "Decryption", destPath, srcPath);
    }
}
