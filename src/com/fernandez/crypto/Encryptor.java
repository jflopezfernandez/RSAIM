package com.fernandez.crypto;

import java.math.BigInteger;
import java.util.Random;

public class Encryptor implements Runnable {

	private long defaultSeed = 23434;
	private long seed;

	private final Random random;
	private final int bits;

	//private final PublicKey publicKey;
	//private final PrivateKey privateKey;

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

	public void setPublicKey(Key pKey) {
		publicKey = pKey;
	}

	private void setKey(BigInteger key, BigInteger exponent) {
		key = exponent;
	}

	/* TODO
	 * Generates Key Pair
	 * Returns the Public Key to share
	 * Saves the private key for internal use
	 */
	public void generateKeys() {
		final BigInteger p = BigInteger.probablePrime(bits, random);
		final BigInteger q = BigInteger.probablePrime(bits, random);
		final BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		final BigInteger e = BigInteger.probablePrime(bits, rnd);
		final BigInteger d = e.modInverse(totient);
		final BigInteger product = p.multiply(q);

		publicKey = new PublicKey();
		setKey(publicKey, e);

		privateKey = new PrivateKey();
		setKey(privateKey, d);
	}

	private void encrypt(BigInteger message) {
		publicKey.value(message);
	}

	private void decrypt() {
		privateKey.value(message);
	}
}
