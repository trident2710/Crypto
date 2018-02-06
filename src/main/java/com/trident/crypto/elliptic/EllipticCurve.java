/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.elliptic.nist.NistEC;
import com.trident.crypto.field.element.BinaryExtensionFieldElement;
import com.trident.crypto.field.element.BinaryExtensionFieldElementFactory;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.element.FiniteFieldElementFactory;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;

// @see http://www.secg.org/SEC2-Ver-1.0.pdf
public class EllipticCurve{
    
    /**
     * parameter a of the curve equation 
     */
    private final FiniteFieldElement a;
    
    /**
     * parameter b of the curve equation 
     */
    private final FiniteFieldElement b;
    
    /**
     * point on the curve with high order
     */
    private final EllipticCurvePoint G;
    
    /**
     * order of the point G
     */
    private final BigInteger n;
    
    /**
     * cofactor - relation between number of points of curve and order of point G
     */
    private final BigInteger h;
    
    private final FiniteFieldElementArithmetics fieldArithmetics;

    public EllipticCurve(FiniteFieldElementArithmetics fieldArithmetics, FiniteFieldElement a, FiniteFieldElement b, EllipticCurvePoint G, BigInteger n, BigInteger h) {
        this.a = a;
        this.b = b;
        this.G = G;
        this.n = n;
        this.h = h;
        this.fieldArithmetics = fieldArithmetics;
    }

    public FiniteFieldElementArithmetics getFieldArithmetics() {
        return fieldArithmetics;
    }

    public FiniteFieldElement getA() {
        return a;
    }

    public FiniteFieldElement getB() {
        return b;
    }

    public EllipticCurvePoint getG() {
        return G;
    }
    
    public BigInteger getN() {
        return n;
    }

    public BigInteger getH() {
        return h;
    }   
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Elliptic curve with:")
                .append("\n")
                .append(getFieldArithmetics())
                .append("\n")
                .append("With params:\n")
                .append("A = ")
                .append(getA())
                .append("\n")
                .append("B = ")
                .append(getB())
                .append("\n")
                .append("G = ")
                .append(getG())
                .append("\n")
                .append("n = ")
                .append(getN())
                .append("\n")
                .append("H = ")
                .append(getH())
                .append("\n");      
        return sb.toString();
    }   
    
    public static EllipticCurve createFrom(NistEC spec){
        return createFrom(spec, spec.getType()?new FiniteFieldElementFactory():new BinaryExtensionFieldElementFactory());
    }
    
    private static EllipticCurve createFrom(NistEC spec, FiniteFieldElementFactory factory){
        FiniteFieldElementArithmetics arithmetics = 
                FiniteFieldElementArithmetics.createFieldElementArithmetics(spec.getType()?new BigInteger(spec.getP(),16):BinaryExtensionFieldElement.fromString(spec.getP()));
        
        return new EllipticCurve(arithmetics,
                factory.createFrom(spec.getA()),
                factory.createFrom(spec.getB()),
                EllipticCurvePoint.create(factory.createFrom(spec.getGx()), factory.createFrom(spec.getGy())),
                spec.getN(),
                spec.getH());
    }   
}
