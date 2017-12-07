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
 * performs arithmetic operations with the points of the elliptic curve defined over finite field
 * @author trident
 * @param <T> - type of the finite field over which the elliptic curve is defined
 * @param <K> - type of the element of this finite field
 * @param <P> - type of the point of this elliptic curve
 */
public abstract class EllipticCurvePointOperator<T extends FiniteField<K>, K extends FiniteFieldElement, P extends EllipticCurvePoint<K>> {
    
    /**
     * elliptic curve over which the arithmetic operations are performed
     */
    protected final EllipticCurve<T,K,P> curve;
    
    public EllipticCurvePointOperator(EllipticCurve<T,K,P> curve){
        this.curve = curve;
    }
    
    /**
     * add two elliptic curve points
     * @param p1
     * @param p2
     * @return the point p3 = p1 + p2 which also belongs to the elliptic curve
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
     * multiply elliptic curve point to the number
     * in fact add point k times to itself
     * i.e. Q = k*P
     * @param times - number k in k*P
     * @param p1 - point P in k*P
     * @return Q = k*P
     */
    public P mul(BigInteger times, P p1){
        if(times.compareTo(BigInteger.ZERO) == 0)
            throw new RuntimeException("multiply to zero");
        
        if(times.compareTo(BigInteger.ONE) == 0)
            return p1;
        
        boolean isOdd = times.testBit(0);
        
        if(isOdd){
            times = times.subtract(BigInteger.ONE);
        }
        
        P tmp = getCurve().createPoint(p1.getPointX(), p1.getPointY());
        // while times is bigger than 1
        while (times.compareTo(BigInteger.ONE)>0) {   
            tmp = doub(tmp);
            times.shiftRight(1); // times = times/2
        }
        
        return isOdd?add(tmp, p1):tmp;
    }
    
    /**
     * check if the point belongs to this curve
     * by putting it's coordinates to the curve equation
     * @param el1 - point of the elliptic curve
     * @return belongs or not
     */
    public abstract boolean belongsTo(P el1);
    
    /**
     * double provided point,
     * i.e. calculate Q = 2*P;
     * @param p1 - point of the elliptic curve
     * @return Q = 2*P
     */
    public P doub(P p1){
        FiniteFieldElementOperator<K> o = curve.getField().getOperator();
        K dy = o.add(o.mul(o.mul(curve.getField().create(new BigInteger("3")),p1.getPointX()),p1.getPointX()),curve.getA());
        K dx = o.mul(curve.getField().create(new BigInteger("3")),p1.getPointY());
        K m  = o.mul(dy, o.inv(dx));
        K p3x = o.sub(o.sub(o.mul(m, m),p1.getPointX()),p1.getPointX());
        K p3y = o.sub(o.mul(m, o.sub(p1.getPointX(), p3x)),p1.getPointY());
        return curve.createPoint(p3x, p3y);
    }

    /**
     * get the elliptic curve over which this operator is defined
     * @return 
     */
    public EllipticCurve<T, K, P> getCurve() {
        return curve;
    }
}

