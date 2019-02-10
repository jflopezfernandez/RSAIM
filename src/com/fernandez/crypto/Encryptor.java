package com.fernandez.crypto;

import java.math.BigInteger;
import java.util.Random;

public class Encryptor implements Runnable {

	private static final long defaultSeed = 23434;

	private long seed;

	private static Random random = new Random(defaultSeed);
	private static int bits = 100;

	public Encryptor() {
		setSeed(defaultSeed);
		seedRandomNumberGenerator();
	}

	public Encryptor(final long seed) {
		setSeed(seed);
		seedRandomNumberGenerator();
	}

	public void run() {
		// TODO: Implement run() method
	}

	private void setSeed(final long seed) {
		this.seed = seed;
	}

	private void seedRandomNumberGenerator() {
		random = new Random(this.seed);
	}
	/*
	private BigInteger publicKey;
	private BigInteger privateKey;
	private void setKey(BigInteger key, BigInteger exponent) {
		key = exponent;
	}
	*/
	/**
	 * TODO: refactor generateKeys()
	 * Generates Key Pair
	 * Returns the Public Key to share
	 * Saves the private key for internal use
	 *
	 */
	public static KeyPair generateKeys() {
		final BigInteger p = BigInteger.probablePrime(bits, random);
		final BigInteger q = BigInteger.probablePrime(bits, random);
		final BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		final BigInteger e = BigInteger.probablePrime(bits, random);
		final BigInteger d = e.modInverse(totient);
		final BigInteger product = p.multiply(q);
		/*
		setKey(publicKey,  e);
		setKey(privateKey, d);
		*/
		return new KeyPair(new Key(e, product), new Key(d, product));
	}

	public static BigInteger encrypt(Key recipient, BigInteger message) {
		// TODO: Re-implement encrypt()
		System.out.printf("Plain  text: %s%n", message.toString());
		BigInteger cipher = recipient.getTransformedValue(message);
		System.out.printf("Cipher text: %s%n", cipher.toString());
		return cipher;
	}

	public static BigInteger decrypt(Key recipient, BigInteger cipher) {
		// TODO: Re-implement decrypt()
		System.out.printf("Cipher text: %s%n", cipher.toString());
		BigInteger message = recipient.getTransformedValue(cipher);
		System.out.printf("Plain  text: %s%n", message.toString());
		return message;
	}
}
