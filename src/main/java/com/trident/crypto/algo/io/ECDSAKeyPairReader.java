/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.algo.io;

import com.trident.crypto.algo.ECDSAKey;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import com.trident.crypto.field.element.FiniteFieldElementFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;

/**
 *
 * @author trident
 */
public class ECDSAKeyPairReader extends Reader{
    
    private final Reader reader;
    
    public ECDSAKeyPairReader(Reader reader){
        this.reader = reader;
    }
    
    public ECDSAKey readECDSAKey() throws IOException{
        FiniteFieldElementFactory factory = new FiniteFieldElementFactory();
        try(BufferedReader r = new BufferedReader(reader)) {
            String kSecS = r.readLine();
            String kPubS = r.readLine();
            
            if(kSecS==null|| kPubS==null)
                throw new RuntimeException("unable to read key");
            if(!kSecS.contains("Secret: "))
                throw new RuntimeException("unable to read secret key");
            if(!kPubS.contains("Public: "))
                throw new RuntimeException("unable to read public key");
            kSecS = kSecS.replace("Secret: ", "");
            kPubS = kPubS.replace("Public: ", "");
            
            BigInteger kSec = new BigInteger(kSecS, 16);
            String[] kPubSk = kPubS.split(";");
            if(kPubSk.length!=2)
                throw new RuntimeException("unable to read public key");
            EllipticCurvePoint kPub = EllipticCurvePoint.create(factory.createFrom(new BigInteger(kPubSk[0],16)), factory.createFrom(new BigInteger(kPubSk[1],16)));
            return new ECDSAKey(kSec, kPub);
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return reader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
    
}
