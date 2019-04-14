package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Crypto {

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

    private static void checksha(String fileName, String expectedSha) {
        String actualSha = getDigestOf(fileName, "SHA-256");

        System.out.print("Digesting completed. Digest of " + fileName);
        if (actualSha.equals(expectedSha)) {
            System.out.println(" matches expected digest.");
        } else {
            System.out.println(" does not match the expected digest. " +
                    "Digest was: " + actualSha);
        }
    }

    private static String getDigestOf(String fileName, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            try (InputStream is = new BufferedInputStream(
                    Files.newInputStream(Paths.get(fileName)))) {
                byte[] buff = new byte[1024];
                int bytesRead;

                while((bytesRead = is.read(buff)) > 0) {
                    messageDigest.update(buff, 0, bytesRead);
                }
            }

            return Util.byteToString(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm \"" + algorithm + "\".");
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
