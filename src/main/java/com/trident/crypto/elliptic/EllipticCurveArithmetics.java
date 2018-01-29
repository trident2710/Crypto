/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;

public class EllipticCurveArithmetics{
    
    protected final FiniteFieldElementArithmetics arithmetics;
    protected final EllipticCurve ellipticCurve;

    public EllipticCurveArithmetics(FiniteFieldElementArithmetics arithmetics, EllipticCurve ellipticCurve) {
        this.arithmetics = arithmetics;
        this.ellipticCurve = ellipticCurve;
    }
    
    /**
     * add two elliptic curve points
     * @param p1
     * @param p2
     * @return the point p3 = p1 + p2 which also belongs to the elliptic curve
     */
    public EllipticCurvePoint add(EllipticCurvePoint p1, EllipticCurvePoint p2){
        // if one of coordinates is the same
        if(Math.abs(p1.compareTo(p2))<1) return doub(p1);
            
        FiniteFieldElement dy = arithmetics.sub(p2.getPointY(), p1.getPointY());
        FiniteFieldElement dx = arithmetics.sub(p2.getPointX(), p1.getPointX());
        FiniteFieldElement m  = arithmetics.mul(dy, arithmetics.inv(dx));
        FiniteFieldElement p3x = arithmetics.sub(arithmetics.sub(arithmetics.mul(m, m),p1.getPointX()),p2.getPointX());
        FiniteFieldElement p3y = arithmetics.sub(arithmetics.mul(m, arithmetics.sub(p1.getPointX(), p3x)),p1.getPointY());
        return EllipticCurvePoint.create(p3x, p3y);
    }
    
    /**
     * multiply elliptic curve point to the number
     * in fact add point k times to itself
     * i.e. Q = k*P
     * @param times - number k in k*P
     * @param p1 - point P in k*P
     * @return Q = k*P
     */
    public EllipticCurvePoint mul(BigInteger times, EllipticCurvePoint p1){
        if(times.compareTo(BigInteger.ZERO) == 0)
            throw new RuntimeException("multiply to zero");
        
        if(times.compareTo(BigInteger.ONE) == 0)
            return p1;
        
        boolean isOdd = times.testBit(0);
        
        if(isOdd){
            times = times.subtract(BigInteger.ONE);
        }
        
        EllipticCurvePoint tmp = EllipticCurvePoint.create(p1.getPointX(), p1.getPointY());
        // while times is bigger than 1
        while (times.compareTo(BigInteger.ONE)>0) {   
            tmp = doub(tmp);
            times.shiftRight(1); // times = times/2
        }
        return isOdd?add(tmp, p1):tmp;
    }
    
    /**
     * double provided point,
     * i.e. calculate Q = 2*P;
     * @param p1 - point of the elliptic curve
     * @return Q = 2*P
     */
    public EllipticCurvePoint doub(EllipticCurvePoint p1){
        FiniteFieldElement dy = arithmetics.add(arithmetics.mul(arithmetics.mul(arithmetics.getElementFactory().createFrom(new BigInteger("3")),p1.getPointX()),p1.getPointX()),ellipticCurve.getA());
        FiniteFieldElement dx = arithmetics.mul(arithmetics.getElementFactory().createFrom(new BigInteger("3")),p1.getPointY());
        FiniteFieldElement m  = arithmetics.mul(dy, arithmetics.inv(dx));
        FiniteFieldElement p3x = arithmetics.sub(arithmetics.sub(arithmetics.mul(m, m),p1.getPointX()),p1.getPointX());
        FiniteFieldElement p3y = arithmetics.sub(arithmetics.mul(m, arithmetics.sub(p1.getPointX(), p3x)),p1.getPointY());
        return EllipticCurvePoint.create(p3x, p3y);
    }

    public FiniteFieldElementArithmetics getArithmetics() {
        return arithmetics;
    }

    public EllipticCurve getEllipticCurve() {
        return ellipticCurve;
    }
    
}

