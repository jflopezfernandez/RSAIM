package com.fernandez.crypto;

import java.math.BigInteger;

public class Key {

	protected final BigInteger exponent;
	protected final BigInteger modulus;

	public Key() {
		this(BigInteger.ZERO, BigInteger.ZERO);
	}
	public Key(final BigInteger exp, final BigInteger mod) {
		exponent = exp;
		modulus = mod;
	}
	public BigInteger getExponent() {
		return this.exponent;
	}

	public BigInteger getTransformedValue(BigInteger message) {
		return message.modPow(exponent, modulus);
	}
	public String toString() {
		return String.format("Exponent: %s%nModulus: %s%n", exponent.toString(), modulus.toString());
	}
}
