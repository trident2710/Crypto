/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;

import com.trident.crypto.finitefield.FiniteField;
import com.trident.crypto.finitefield.FiniteFieldElement;
import java.math.BigInteger;

/**
 * defines elliptic curve following by the NIST standard which can be found on 
 * @see http://www.secg.org/SEC2-Ver-1.0.pdf
 * @author trident
 * @param <T> defines the finite field over which elliptic curve is defined
 * @param <K> defines the class which represents the element of the finite field over which this elliptic curve is defined
 * @param <P> defines the class which represents the point on this elliptic curve
 */
public abstract class EllipticCurve<T extends FiniteField<K>, K extends FiniteFieldElement, P extends EllipticCurvePoint<K>> implements EllipticCurvePointFactory<K, P>{
    
    /**
     * finite field over which this curve is defined
     */
    protected final T field;
    
    /**
     * parameter a of the curve equation 
     * @see http://www.secg.org/SEC2-Ver-1.0.pdf
     */
    private final K a;
    /**
     * parameter b of the curve equation 
     * @see http://www.secg.org/SEC2-Ver-1.0.pdf
     */
    private final K b;
    /**
     * point on the curve with high order
     * @see http://www.secg.org/SEC2-Ver-1.0.pdf
     */
    private final P G;
    /**
     * order of the point G
     * @see http://www.secg.org/SEC2-Ver-1.0.pdf
     */
    private final BigInteger n;
    /**
     * cofactor - relation between number of points of curve and order of point G
     * @see http://www.secg.org/SEC2-Ver-1.0.pdf
     */
    private final BigInteger h;

    public EllipticCurve(T field, K a, K b, P G, BigInteger n, BigInteger h) {
        this.field = field;
        this.a = a;
        this.b = b;
        this.G = G;
        this.n = n;
        this.h = h;
    }
    
    /**
     * get operator to perform the arithmetic operations between finite field elements
     * @return 
     */
    public abstract EllipticCurvePointOperator<T,K,P> getOperator();

    public T getField() {
        return field;
    }

    public K getA() {
        return a;
    }

    public K getB() {
        return b;
    }

    public P getG() {
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
        StringBuilder b = new StringBuilder();
               b.append("A = ")
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
        return b.toString();
    }
}
