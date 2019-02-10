package com.fernandez.crypto;

import java.math.BigInteger;
import java.util.Random;

public class Encryptor implements Runnable {

	private static final long defaultSeed = 23434;

	private long seed;

	private Random random;
	private int bits;

	private BigInteger publicKey;
	private BigInteger privateKey;

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

	private void setKey(BigInteger key, BigInteger exponent) {
		key = exponent;
	}

	/**
	 * TODO: refactor generateKeys()
	 * Generates Key Pair
	 * Returns the Public Key to share
	 * Saves the private key for internal use
	 *
	 */
	public void generateKeys() {
		final BigInteger p = BigInteger.probablePrime(bits, random);
		final BigInteger q = BigInteger.probablePrime(bits, random);
		final BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		final BigInteger e = BigInteger.probablePrime(bits, random);
		final BigInteger d = e.modInverse(totient);
		final BigInteger product = p.multiply(q);

		setKey(publicKey,  e);
		setKey(privateKey, d);
	}

	private void encrypt(BigInteger message) {
		// TODO: Re-implement encrypt()
		// publicKey.getTransformedValue(message);
	}

	private void decrypt() {
		// TODO: Re-implement decrypt()
		// privateKey.getTransformedValue(message);
	}
}
