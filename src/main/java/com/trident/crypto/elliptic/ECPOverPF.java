/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.finitefield.PrimeFieldElement;

/**
 * point of the elliptic curve defined over prime field i.e. GF(2^p)
 * @author trident
 */
public class ECPOverPF extends EllipticCurvePoint<PrimeFieldElement>{
    
    public ECPOverPF(PrimeFieldElement pointX, PrimeFieldElement pointY) {
        super(pointX, pointY);
    }
    
    public ECPOverPF(ECPOverPF other){
        super(other);
    }   
}
