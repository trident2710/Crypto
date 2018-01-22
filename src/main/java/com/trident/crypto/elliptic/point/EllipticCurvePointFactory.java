/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic.point;

import com.trident.crypto.finitefield.element.FiniteFieldElement;

/**
 * factory interface to yield the elliptic curve point from 2 coordinates which belong to the finite field
 * @author trident
 * @param <K> - finite field which the coordinates belong to
 * @param <P> - type of the elliptic curve point to yield
 */
public interface EllipticCurvePointFactory<K extends FiniteFieldElement, P extends EllipticCurvePoint<K>>{
    /**
     * creates the instance of EllipticCurvePoint from 2 coordinates
     * @param x
     * @param y
     * @return 
     */
    P createPoint(K x, K y);
}
