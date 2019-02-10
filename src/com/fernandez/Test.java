package com.fernandez;

import com.fernandez.crypto.*;
import com.fernandez.app.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Test {

	private static Version CurrentVersion;

	public static void main(final String[] args) {
		Scanner scanner = null;

		do {
			printMenu();

			scanner = new Scanner(System.in);

			String[] userInput = scanner.nextLine().split(" ");

			if (userInput[0].length() == 0) {
				exit(ExitCode.SUCCESS);
			}

			System.out.println("User input: " + userInput[0]);

			switch (userInput[0])
			{
				case "1":
				case "encrypt": {
					// TODO: Call encrypt();
					System.out.println("<Encrypt>");
				} break;

				case "2":
				case "decrypt": {
					//decrypt();
					System.out.println("<Decrypt>");
				} break;

				case "3":
				case "help": {
					printHelp();
					exit(ExitCode.SUCCESS);
				} break;

				case "4":
				case "version": {
					// TODO: Implement printVersion();
					System.out.println("<Print Version>");
					exit(ExitCode.SUCCESS);
				} break;

				case "exit": {
					exit(ExitCode.SUCCESS);
				} break;

				default: {
					// TODO: Implement printVersion();
					System.out.println("<Print Version>");
					printHelp();
					exit(ExitCode.SUCCESS);
				}
			}

		} while (scanner.hasNextLine());

		// final Random rnd = new Random(23434);
		// final int bits = 100;
		// final BigInteger p = BigInteger.probablePrime(bits, rnd);
		// final BigInteger q = BigInteger.probablePrime(bits, rnd);
		// final BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		// final BigInteger e = BigInteger.probablePrime(bits, rnd);
		// final BigInteger d = e.modInverse(totient);
		// final BigInteger product = p.multiply(q);
		//
		// final Scanner in = new Scanner(System.in);
		//
		// while (in.hasNextLine()) {
		//     final String[] input = in.nextLine().split("\\s+");
		//
		//     // Exit if the user doesn't enter anything.
		//     if (input[0].length() == 0) { break; }
		//
		//     if (input[0].equals("message")) {
		//         final BigInteger encrypted = encrypt(new BigInteger(input[1]),
		//                                              e, product);
		//         System.out.printf("cipher  %s%n", encrypted.toString());
		//     } else {
		//         final BigInteger decrypted = decrypt(new BigInteger(input[1]),
		//                                              d, product);
		//         System.out.printf("message %s%n", decrypted.toString());
		//     }
		// }
	}

	private static void printMenu() {
		System.out.println("\n\n\t\tMessage Encryptor\n\n");
		System.out.println("\t1. - [encrypt <plain text>] ");
		System.out.println("\t2. - [decrypt <cyphertext>] ");
		System.out.println("\t3. - [help]                 ");
		System.out.println("\t4. - [version]              ");
		System.out.println("\t                          \n");
		System.out.println("\t   Press [ENTER] to exit the application.\n");
	}

	private static void printHelp() {
		// TODO: Implement printHelp()
		System.out.println("<A Help Menu>");
	}

	private static void printVersion() {
		System.out.println("Version: " + CurrentVersion.getString());
	}

	private static enum ExitCode {
		SUCCESS(0),
		FAILURE(1);

		private final int value;

		private ExitCode(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private static void exit(int exitCode) {
		System.exit(exitCode);
	}

	/**
	 * I'm not actually sure if using enums as constants for these values is
	 * helping to make the code much clearer. For now I'm just going to keep
	 * using both integers and enums to allow anyone accessing the API to use
	 * whatever feels most natural, and I'll reevaluate once we've had some time
	 * to see whether static typing constants helps or is just code noise.
	 * - Jose Fernando Lopez Fernandez 9-Feb-2019
	 */
	private static void exit(final ExitCode exitCode) {
		System.exit(exitCode.getValue());
	}

	// private static BigInteger decrypt (final BigInteger cipher, final BigInteger d,
	//                                   final BigInteger product) {
	//     return cipher.modPow(d, product);
	// }
	//
	// private static BigInteger encrypt (final BigInteger message, final BigInteger e,
	//                                   final BigInteger product) {
	//     return message.modPow(e, product);
	// }
}
