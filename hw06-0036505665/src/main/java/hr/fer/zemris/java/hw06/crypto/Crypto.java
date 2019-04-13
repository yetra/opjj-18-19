package hr.fer.zemris.java.hw06.crypto;

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

                    System.out.println("Please provide expected sha-256 digest for "
                            + args[1] + ":\n> ");
                    checksha(args[1], sc.nextLine()); // TODO validate expected sha?
                    break;

                case 3:
                    if (!args[0].equalsIgnoreCase("encrypt") &&
                            !args[0].equalsIgnoreCase("decrypt")) {
                        throw new IllegalArgumentException(
                                "Unknown command \"" + args[0] + "\".");
                    }

                    System.out.println("Please provide password as hex-encoded text " +
                            "(16 bytes, i.e. 32 hex-digits):\n> ");
                    String password = sc.nextLine();
                    System.out.println("Please provide initialization vector as " +
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

    }

    private static void encrypt(String srcPath, String destPath) {

    }

    private static void decrypt(String srcPath, String destPath) {

    }
}
