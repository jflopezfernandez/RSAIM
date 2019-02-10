package com.fernandez.crypto;

import java.math.BigInteger;

public class Key {

	protected final BigInteger publicKey;
	protected final BigInteger privateKey;
	protected final BigInteger modulus;

	public BigInteger getExponent();

	public BigInteger value(BigInteger message) {
		return message.modPow(exponent, modulus);
	}

	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}

	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}
}
