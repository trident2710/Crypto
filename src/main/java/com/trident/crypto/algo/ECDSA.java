/*
 * Copyright 2018 trident.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trident.crypto.algo;

import com.trident.crypto.algo.mpmbehavior.MPMBehavior;
import com.trident.crypto.algo.mpmbehavior.MPMStandardBehavior;
import com.trident.crypto.elliptic.EllipticCurveOperator;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import java.math.BigInteger;
import java.util.Random;


/**
 * implements EDSA (elliptic curve digital signature algorithm)
 * @author trident
 */
public class ECDSA {
    
    /**
     * arithmetics over the elliptic curve 
     * @see EllipticCurveArithmetics
     */
    private final EllipticCurveOperator operator;
    
    /**
     * multiple point multiplication calculation strategy
     */
    private MPMBehavior mpmBehavior;
    
    private final Random random;
        
    public ECDSA(EllipticCurveOperator operator, MPMBehavior behavior){
        this.operator = operator;
        this.random = new Random();
        this.mpmBehavior = behavior;
    }
    
    public ECDSA(EllipticCurveOperator operator){
        this(operator, new MPMStandardBehavior(operator));
    }
    
    /**
     * generate key pair i.e. secret and public
     * such that 
     * secret = b (random BigInteger)
     * public  = Q = b*G (product of generator point of the elliptic curve on b)
     * @return 
     */
    public ECDSAKey generateKeyPair(){
        BigInteger sKey = new BigInteger(getOperator().getEllipticCurve().getN().bitLength(), random);
        EllipticCurvePoint pKey = getOperator().mul(sKey, getOperator().getEllipticCurve().getG());
        return new ECDSAKey(sKey,pKey);
    }
    
    /**
     * returns the digital signature 
     * @param mHash - hash of the plain message M, produced using hash function such as SHA-256
     * @param sKey - secret key of the key pair
     * @return hexadecimal string, digital signature of mHash
     */
    public String sign(byte[] mHash, BigInteger sKey){
        BigInteger n = getOperator().getEllipticCurve().getN();
        EllipticCurvePoint G = getOperator().getEllipticCurve().getG();
        
        BigInteger alpha = new BigInteger(mHash);
        BigInteger e = alpha.mod(n);
        if(e.equals(BigInteger.ZERO)) e = BigInteger.ONE;
        
        BigInteger k;
        EllipticCurvePoint C;
        BigInteger r;
        BigInteger s;
        do{
            do {                
                k = new BigInteger(n.bitLength(), random);
            } while (k.compareTo(BigInteger.ZERO) == -1 || k.compareTo(n) >= 1); // k<0 || k>n
            C = getOperator().mul(k, G);
            r = C.getPointX().mod(n);
            s = r.multiply(sKey).add(k.multiply(e)).mod(n);   
        } while (r.equals(BigInteger.ZERO)||s.equals(BigInteger.ZERO));
        
        return padding(r.toString(16),len16(n))+padding(s.toString(16),len16(n));
    }
    
    /**
     * verify that provided signature corresponds to the provided hash
     * @param mHash - hash of the plain message M, produced using hash function such as SHA-256
     * @param pKey - public key of the key pair
     * @param signature - hexadecimal string, digital signature of mHash
     * @return if the signature is verified
     */
    public boolean verify(byte[] mHash, EllipticCurvePoint pKey, String signature){
        BigInteger n = getOperator().getEllipticCurve().getN();
        EllipticCurvePoint G = getOperator().getEllipticCurve().getG();
        
        BigInteger r = new BigInteger(signature.substring(0, len16(n)), 16);
        BigInteger s = new BigInteger(signature.substring(len16(n), 2*len16(n)), 16);
        
        if(r.compareTo(BigInteger.ZERO)<=0||r.compareTo(n)>=0) return false;
        if(s.compareTo(BigInteger.ZERO)<=0||s.compareTo(n)>=0) return false;

        BigInteger alpha = new BigInteger(mHash);
        BigInteger e = alpha.mod(n);
        if(e.equals(BigInteger.ZERO)) e = BigInteger.ONE;
        
        BigInteger v = e.modInverse(n);
        BigInteger z1 = (s.multiply(v)).mod(n);
        BigInteger z2 = n.add(r.multiply(v).negate()).mod(n);
        
        EllipticCurvePoint C = mpmBehavior.mpm(z1, z2, G, pKey);
        BigInteger R = C.getPointX().mod(n);
        
        return R.equals(r);
    }

    private EllipticCurveOperator getOperator() {
        return operator;
    }  
    
    private int len16(BigInteger n){
        if(n.bitLength()%4==0) 
            return n.bitLength()/4;
        else return n.bitLength()/4+1;
    }
     
    private String padding(String hexString, int byteSize){
        if(byteSize<hexString.length()) throw new RuntimeException("input string length is bigger than required");
        if(byteSize==hexString.length()) return hexString;
        
        StringBuilder sb =  new StringBuilder(hexString).reverse();
        while (sb.length()<byteSize) {            
            sb.append("0");
        }
        return sb.reverse().toString();
    }

    public MPMBehavior getMpmBehavior() {
        return mpmBehavior;
    }

    public void setMpmBehavior(MPMBehavior mpmBehavior) {
        if(mpmBehavior == null) throw new RuntimeException("should not be null");
        this.mpmBehavior = mpmBehavior;
    }  
}
