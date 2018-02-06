/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic.arithmetics;

import com.trident.crypto.elliptic.EllipticCurve;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;


/**
 * for elliptic curves over prime finite fields the 
 * y^2 = x^3 + ax +b mod p equation is used
 * @author trident
 */
public class ECOverPFArithmetics extends EllipticCurveArithmetics{
    
    public ECOverPFArithmetics(EllipticCurve ellipticCurve) {
        super(ellipticCurve);
    }
    
    @Override
    public EllipticCurvePoint add(EllipticCurvePoint p1, EllipticCurvePoint p2){
        if(Math.abs(p1.compareTo(p2))==0) return doub(p1);
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
            
        FiniteFieldElement dy = f.sub(p2.getPointY(), p1.getPointY()); //p2.y - p1.y
        FiniteFieldElement dx = f.sub(p2.getPointX(), p1.getPointX()); //p2.x - p1.x 
        FiniteFieldElement m  = f.mul(dy, f.inv(dx)); // dy/dx
        FiniteFieldElement p3x = f.sub(f.sub(f.mul(m, m),p1.getPointX()),p2.getPointX()); // m^2 - p1.x - p2.x
        FiniteFieldElement p3y = f.sub(f.mul(m, f.sub(p1.getPointX(), p3x)),p1.getPointY()); // m*(p1.x -p3.x) - p1.y
        return EllipticCurvePoint.create(p3x, p3y);
    }
    
    @Override
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
        while (times.compareTo(BigInteger.ONE)>0) {   
            tmp = doub(tmp);
            times = times.shiftRight(1);
        }
        return isOdd?add(tmp, p1):tmp;
    }
    
    @Override
    public EllipticCurvePoint doub(EllipticCurvePoint p1){
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
        FiniteFieldElement dy = f.add(f.mul(f.mul(f.getElementFactory().createFrom(new BigInteger("3")),p1.getPointX()),p1.getPointX()),ellipticCurve.getA());
        FiniteFieldElement dx = f.mul(f.getElementFactory().createFrom(new BigInteger("2")),p1.getPointY());
        FiniteFieldElement m  = f.mul(dy, f.inv(dx));
        FiniteFieldElement p3x = f.sub(f.sub(f.mul(m, m),p1.getPointX()),p1.getPointX());
        FiniteFieldElement p3y = f.sub(f.mul(m, f.sub(p1.getPointX(), p3x)),p1.getPointY());
        return EllipticCurvePoint.create(p3x, p3y);
    }

    /**
     * check if y^2 = x^3 + ax +b mod p
     * @param p1
     * @return 
     */
    @Override
    public boolean belongsTo(EllipticCurvePoint p1) {
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
        FiniteFieldElement y2 = f.mul(p1.getPointY(), p1.getPointY()); //y^2
        FiniteFieldElement x3 = f.mul(f.mul(p1.getPointX(), p1.getPointX()),p1.getPointX()); // x^3
        FiniteFieldElement ax = f.mul(p1.getPointX(), getEllipticCurve().getA()); // ax
        FiniteFieldElement res = f.add(f.add(x3, ax),getEllipticCurve().getB()); // x^3+ax+b
        return y2.equals(res);
    }
}

