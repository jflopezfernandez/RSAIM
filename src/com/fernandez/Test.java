package com.fernandez;

import com.fernandez.crypto.*;
import com.fernandez.app.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Test {

	private static com.fernandez.app.Version CurrentVersion;

	public static void main(final String[] args) {
		System.out.println("Generating Key Pair\n");
		KeyPair one = Encryptor.generateKeys();
		System.out.println(one.toString());
		System.out.println("Encrypting Message");
		BigInteger result = Encryptor.encrypt(one.getPublicKey(), BigInteger.valueOf(1234567890));
		System.out.println("\nDecrypting Result");
		Encryptor.decrypt(one.getPrivateKey(), result);
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

	private static BigInteger decrypt (final BigInteger cipher, final BigInteger d, final BigInteger product) {
		return cipher.modPow(d, product);
	}

	private static BigInteger encrypt (final BigInteger message, final BigInteger e, final BigInteger product) {
		return message.modPow(e, product);
	}
}
