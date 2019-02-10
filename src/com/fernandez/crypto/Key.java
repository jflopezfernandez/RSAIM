package com.fernandez.crypto;

import java.math.BigInteger;

public class Key {

	protected BigInteger exponent = BigInteger.ZERO;
	protected BigInteger modulus  = BigInteger.ZERO;

	public Key() {
		// Default constructor
	}

	public BigInteger getExponent() {
		return this.exponent;
	}

	public BigInteger getTransformedValue(BigInteger message) {
		return message.modPow(exponent, modulus);
	}

	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}

	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}
}
