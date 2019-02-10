
# RSAIM
Encrypted messaging over a network

---

### Building

To create a jar
jar cmf <ManfiestFile> <JarFile> <IncludedFiles>
jar cmf Test.mf Test.jar Test.class Test.java Test$ExitCode.class
  
To run a jar
java -jar Test.jar

=======

## Message Encryption and Decryption

    cipher = (message ^ publicKeyExponent ) % modulus
    message = (cipher ^ privateKeyExponent ) % modulus
    
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


### Authors
* [Jose Fernando Lopez Fernandez](https://github.com/lopezfjose)
* [Javier Munoz](https://github.com/JavierMunozFdez)

---

#### Initial Code

This was the original `main` method.

    private static void main(String[] args) {
        final Random rnd = new Random(23434);
            final int bits = 100;
            final BigInteger p = BigInteger.probablePrime(bits, rnd);
            final BigInteger q = BigInteger.probablePrime(bits, rnd);
            final BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            final BigInteger e = BigInteger.probablePrime(bits, rnd);
            final BigInteger d = e.modInverse(totient);
            final BigInteger product = p.multiply(q);
           
            final Scanner in = new Scanner(System.in);
           
            while (in.hasNextLine()) {
                final String[] input = in.nextLine().split("\\s+");
           
                // Exit if the user doesn't enter anything.
                if (input[0].length() == 0) { break; }
           
                if (input[0].equals("message")) {
                    final BigInteger encrypted = encrypt(new BigInteger(input[1]),
                                                         e, product);
                    System.out.printf("cipher  %s%n", encrypted.toString());
                } else {
                    final BigInteger decrypted = decrypt(new BigInteger(input[1]),
                                                         d, product);
                    System.out.printf("message %s%n", decrypted.toString());
                }
        }
    }

These were the original methods for encryption and decryption.

    private static BigInteger decrypt (final BigInteger cipher, final BigInteger d, final BigInteger product) {
        return cipher.modPow(d, product);
    }
    
    private static BigInteger encrypt (final BigInteger message, final BigInteger e, final BigInteger product) { 
        return message.modPow(e, product);
    }

