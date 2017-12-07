/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.finitefield.FiniteFieldElement;

/**
 *
 * @author trident
 * @param <K>
 * @param <P>
 */
public interface EllipticCurvePointFactory<K extends FiniteFieldElement, P extends EllipticCurvePoint<K>>{
    P createPoint(K x, K y);
}
