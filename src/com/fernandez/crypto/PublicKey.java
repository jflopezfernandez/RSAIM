package com.fernandez.crypto;

import java.math.BigInteger;

public class PublicKey extends Key {

	@Override
	public BigInteger getExponent() {
		return this.exponent;
	}

}
