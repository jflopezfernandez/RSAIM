
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
/*
cipher = (message ^ publicKeyExponent ) % modulus
message = (cipher ^ privateKeyExponent ) % modulus
*/

/*
My Key Pair:
    Modulus M1
    PublicKey J1
    PrivateKey J2

Your KeyPair
    Modulus M2
    PublicKey F1
    PrivateKey F2

You send me a message using Message1^J1 % M1
I decrypt the message using Cipher1^J2 % M1

I send you message using Message2^F` % M2
You decrypt the message using Cipher2^F2 % M2

I need
J2, M1
F1, M2

You need
J1, M1
F2, M2
*/
