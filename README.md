# Crypto
Provides the implementation of ECDSA (elliptic curve digital signature algorithm) 
with the implementation of the modification based on scientific works.

Contains such useful items as 
* finite field ( GF(2^m) and GF(p)) implementation
* elliptic curve over finite field implementation 
* built-in standard parameters for elliptic curves recommeded by SECP
* ECDS algorithm to sign the messages and verify the signature


How to use it: 

To create the elliptic curve form the standard (secure and proven) parameters proposed by SECP: 
```
EllipticCurveOperator ar = EllipticCurveArithmetics.createFrom(SECP.SECP112R1);
```
This class allows to perform operations over elliptic curve points such as:
-addition
-multiplication
-doubling
-negation

Usage example: 
```
EllipticCurvePoint G = ar.getEllipticCurve().getG(); //generator point of the elliptic curve
EllipticCurvePoint newPoint = ar.mul(BigInteger.valueOf(42), G);
```

ECDSA usage example:
```
String message = "Hello world!"; //the message which we want to secure
MessageDigest digest = MessageDigest.getInstance("SHA-256"); // choosing hash algorithm
byte[] hash = digest.digest(message.getBytes(Charset.defaultCharset())); // calculating hash of the message
ECDSA ecdsa = new ECDSA(EllipticCurveArithmetics.createFrom(SECP.SECP112R1); // creating ECDSA instance which works with the standard elliptic curve SECP112R1 
ECDSAKey key = ecdsa.generateKeyPair(); // generating key pair
String signature = ecdsa.sign(hash, key.getKeySec()); // signing the hash
ecdsa.verify(hash, key.getKeyPub(), signature) //verifying if the obtained hash is valid
```
