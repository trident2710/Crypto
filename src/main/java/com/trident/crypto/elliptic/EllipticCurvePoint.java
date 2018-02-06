/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.elliptic;
import com.trident.crypto.field.element.FiniteFieldElement;
import java.util.Objects;


public class EllipticCurvePoint implements Comparable<EllipticCurvePoint>{
    
    /**
     * X coordinate of the point
     */
    private final FiniteFieldElement pointX;
    
    /**
     * Y coordinate of the point
     */
    private final FiniteFieldElement pointY;

    private EllipticCurvePoint(FiniteFieldElement pointX, FiniteFieldElement pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }
    
    public FiniteFieldElement getPointX() {
        return pointX;
    }

    public FiniteFieldElement getPointY() {
        return pointY;
    }   
    
    @Override
    public String toString(){
        return "X:"+pointX+"; Y:"+pointY;
    }
    
    /**
     * compares the coordinates of 2 points of the elliptic curve
     * @param o - point which this point is compared with
     * @return 0 if they have the same coordinates
     *         +-1 if they have one same coordinate
     *         +-2 if they have different coordinates
     */
    @Override
    public int compareTo(EllipticCurvePoint o) {
        return o.getPointX().compareTo(this.getPointX())+o.getPointY().compareTo(o.getPointY());
    }
    
    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(other == null) return false;
        if(!(other instanceof EllipticCurvePoint)) return false;
        EllipticCurvePoint o = (EllipticCurvePoint)other;
        return o.getPointX().equals(this.getPointX())&&o.getPointY().equals(this.getPointY());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.pointX);
        hash = 37 * hash + Objects.hashCode(this.pointY);
        return hash;
    }
    
    public static EllipticCurvePoint create(FiniteFieldElement pointX, FiniteFieldElement pointY){
        return new EllipticCurvePoint(pointX, pointY);
    }
    
    
}
