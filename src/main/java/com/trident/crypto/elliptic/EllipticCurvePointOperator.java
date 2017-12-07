/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;


import com.trident.crypto.finitefield.FiniteField;
import com.trident.crypto.finitefield.FiniteFieldElement;
import com.trident.crypto.finitefield.FiniteFieldElementOperator;
import java.math.BigInteger;


/**
 *
 * @author trident
 * @param <T>
 * @param <K>
 * @param <P>
 */
public abstract class EllipticCurvePointOperator<T extends FiniteField<K>, K extends FiniteFieldElement, P extends EllipticCurvePoint<K>> {
    
    protected final EllipticCurve<T,K,P> curve;
    
    public EllipticCurvePointOperator(EllipticCurve<T,K,P> curve){
        this.curve = curve;
    }
    /**
     * add two points
     * @param p1
     * @param p2
     * @return sum of elements
     */
    public P add(P p1, P p2){
        // if one of coordinates is the same
        if(Math.abs(p1.compareTo(p2))<1) return doub(p1);
            
        FiniteFieldElementOperator<K> o = curve.getField().getOperator();
        K dy = o.sub(p2.getPointY(), p1.getPointY());
        K dx = o.sub(p2.getPointX(), p1.getPointX());
        K m  = o.mul(dy, o.inv(dx));
        K p3x = o.sub(o.sub(o.mul(m, m),p1.getPointX()),p2.getPointX());
        K p3y = o.sub(o.mul(m, o.sub(p1.getPointX(), p3x)),p1.getPointY());
        return curve.createPoint(p3x, p3y);
    }
    
    /**
     * multiply two points
     * @param times
     * @param p1
     * @return multiple of elements
     */
    public P mul(BigInteger times, P p1){
        P temp = curve.createPoint(p1.getPointX(), p1.getPointY());
        while (times.compareTo(BigInteger.ZERO)>0) {   
            temp = add(temp, p1);
            times = times.subtract(BigInteger.ONE);
        }
        return temp;
    }
    
    /**
     * check if the point belongs to this curve
     * @param el1
     * @return 
     */
    public abstract boolean belongsTo(P el1);
    
    
    public P doub(P p1){
        FiniteFieldElementOperator<K> o = curve.getField().getOperator();
        K dy = o.add(o.mul(o.mul(curve.getField().create(new BigInteger("3")),p1.getPointX()),p1.getPointX()),curve.getA());
        K dx = o.mul(curve.getField().create(new BigInteger("3")),p1.getPointY());
        K m  = o.mul(dy, o.inv(dx));
        K p3x = o.sub(o.sub(o.mul(m, m),p1.getPointX()),p1.getPointX());
        K p3y = o.sub(o.mul(m, o.sub(p1.getPointX(), p3x)),p1.getPointY());
        return curve.createPoint(p3x, p3y);
    }

    public EllipticCurve<T, K, P> getCurve() {
        return curve;
    }
}

