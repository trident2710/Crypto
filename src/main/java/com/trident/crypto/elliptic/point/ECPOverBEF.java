/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic.point;

import com.trident.crypto.finitefield.element.BinaryExtensionFieldElement;

/**
 * point of the elliptic curve defined over binary extension field i.e GF(2^m)
 * @author trident
 */
public class ECPOverBEF extends EllipticCurvePoint<BinaryExtensionFieldElement>{
    
    public ECPOverBEF(BinaryExtensionFieldElement pointX, BinaryExtensionFieldElement pointY) {
        super(pointX, pointY);
    }  
    
    public ECPOverBEF(ECPOverBEF other){
        super(other);
    }
}
