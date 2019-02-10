package com.fernandez.crypto;

public class KeyPair {
    public Key getPublicKey() {
        return publicKey;
    }

    private final Key publicKey;

    public Key getPrivateKey() {
        return privateKey;
    }

    private final Key privateKey;
    public KeyPair(final Key pub, final Key priv) {
        publicKey = pub;
        privateKey = priv;
    }
    public String toString() {
        return String.format("Public Key:%n%s%nPrivate Key: %s%n", publicKey.toString(), privateKey.toString());
    }
}
