/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic.curve;

import com.trident.crypto.elliptic.point.ECPOverBEF;
import com.trident.crypto.elliptic.point.EllipticCurvePointOperator;
import com.trident.crypto.finitefield.field.BinaryExtensionField;
import com.trident.crypto.finitefield.element.BinaryExtensionFieldElement;
import com.trident.crypto.finitefield.element.FiniteFieldElementOperator;
import java.math.BigInteger;

/**
 * defines elliptic curve over prime field i.e. GF(2^m)
 * with the equation y^2 + xy = x^3 + ax^2 + b
 * @author trident
 */
public class ECOverBEF extends EllipticCurve<BinaryExtensionField, BinaryExtensionFieldElement, ECPOverBEF>{

    private final ECOverBEFOperator operator;
    
    ECOverBEF(BinaryExtensionField field, BinaryExtensionFieldElement a, BinaryExtensionFieldElement b, ECPOverBEF G, BigInteger n, BigInteger h) {
        super(field, a, b, G, n, h);
        operator = new ECOverBEFOperator(this);
    }

    @Override
    public ECPOverBEF createPoint(BinaryExtensionFieldElement x, BinaryExtensionFieldElement y) {
        return new ECPOverBEF(x, y);
    }
    
    private static class ECOverBEFOperator extends EllipticCurvePointOperator<BinaryExtensionField, BinaryExtensionFieldElement, ECPOverBEF>{

        public ECOverBEFOperator(EllipticCurve<BinaryExtensionField, BinaryExtensionFieldElement, ECPOverBEF> curve) {
            super(curve);
        }

        /**
         * checks if the coordinates of the point satisfy the equation
         * y^2 + xy = x^3 + ax^2 + b
         * @param el1 - point of the elliptic curve
         * @return satisfy of not
         */
        @Override
        public boolean belongsTo(ECPOverBEF el1) {
            FiniteFieldElementOperator<BinaryExtensionFieldElement> op = getCurve().getField().getOperator();
            BinaryExtensionFieldElement y2  = op.mul(el1.getPointY(), el1.getPointY());
            BinaryExtensionFieldElement xy  = op.mul(el1.getPointY(), el1.getPointX());
            BinaryExtensionFieldElement x3  = op.mul(el1.getPointX(), op.mul(el1.getPointX(), el1.getPointX()));
            BinaryExtensionFieldElement ax2  = op.mul(op.mul(el1.getPointX(), getCurve().getA()),el1.getPointX());
            return op.add(y2,xy).compareTo(op.add(op.add(x3, ax2),getCurve().getB()))==0;
        } 
    }

    @Override
    public EllipticCurvePointOperator<BinaryExtensionField, BinaryExtensionFieldElement, ECPOverBEF> getOperator() {
        return operator;
    }
    
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("Elliptic curve defined over field '").append(field).append("' with parameters:\n").append(super.toString());
        return b.toString();
    }
}
