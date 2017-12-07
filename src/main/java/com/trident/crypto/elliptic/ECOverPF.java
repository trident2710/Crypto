/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.finitefield.PrimeField;
import com.trident.crypto.finitefield.PrimeField.PrimeFieldElementOperator;
import com.trident.crypto.finitefield.PrimeFieldElement;
import java.math.BigInteger;

/**
 * defines elliptic curve over prime field
 * @author trident
 */
public class ECOverPF extends EllipticCurve<PrimeField, PrimeFieldElement, ECPOverPF>{

    private final ECOverPFOperator operator;
    
    public ECOverPF(PrimeField field, PrimeFieldElement a, PrimeFieldElement b, ECPOverPF G, BigInteger n, BigInteger h) {
        super(field, a, b, G, n, h);
        this.operator = new ECOverPFOperator(this);
    }

    @Override
    public ECPOverPF createPoint(PrimeFieldElement x, PrimeFieldElement y) {
        return new ECPOverPF(x, y);
    }
    
    private static class ECOverPFOperator extends EllipticCurvePointOperator<PrimeField, PrimeFieldElement, ECPOverPF>{

        public ECOverPFOperator(EllipticCurve<PrimeField, PrimeFieldElement, ECPOverPF> curve) {
            super(curve);
        }

        @Override
        public boolean belongsTo(ECPOverPF el1) {
            PrimeFieldElementOperator op = getCurve().getField().getOperator();
            PrimeFieldElement y2  = op.mul(el1.getPointY(), el1.getPointY());
            PrimeFieldElement x3  = op.mul(el1.getPointX(), op.mul(el1.getPointX(), el1.getPointX()));
            PrimeFieldElement ax  = op.mul(el1.getPointX(), getCurve().getA());
            return y2.compareTo(op.add(op.add(x3, ax),getCurve().getB()))==0;
        }    
    }
    
    @Override
    public EllipticCurvePointOperator<PrimeField, PrimeFieldElement, ECPOverPF> getOperator() {
        return operator;
    }
    
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("Elliptic curve defined over field '").append(field).append("' with parameters:\n").append(super.toString());
        return b.toString();
    }
}
