package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

                    if (args[0].equalsIgnoreCase("encrypt")) {
                        encrypt(args[1], args[2]);
                    } else {
                        decrypt(args[1], args[2]);
                    }
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

    private static void encrypt(String srcPath, String destPath) {

    }

    private static void decrypt(String srcPath, String destPath) {

    }
}
