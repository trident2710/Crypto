/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.finitefield.FiniteFieldElement;
import com.trident.crypto.finitefield.PrimeFieldElement;

/**
 * point on the elliptic curve with the coordinates over finite field
 * @author trident
 * @param <T> - type of finite field element
 */
public abstract class EllipticCurvePoint<T extends FiniteFieldElement> implements Comparable<EllipticCurvePoint<T>>{
    private final T pointX;
    private final T pointY;

    public EllipticCurvePoint(T pointX, T pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }
    
    public EllipticCurvePoint(EllipticCurvePoint<T> other){
        this(other.getPointX(),other.getPointY());
    }

    public T getPointX() {
        return pointX;
    }

    public T getPointY() {
        return pointY;
    }   
    
    @Override
    public String toString(){
        return "X:"+pointX+"; Y:"+pointY;
    }
    
    @Override
    public int compareTo(EllipticCurvePoint<T> o) {
        return o.getPointX().compareTo(this.getPointX())+o.getPointY().compareTo(o.getPointY());
    }
}
