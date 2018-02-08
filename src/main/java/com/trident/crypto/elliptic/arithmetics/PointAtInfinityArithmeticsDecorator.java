/*
 * Copyright 2018 trident.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trident.crypto.elliptic.arithmetics;

import com.trident.crypto.elliptic.EllipticCurve;
import com.trident.crypto.elliptic.EllipticCurveOperator;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import java.math.BigInteger;

/**
 * decorator pattern usage for edge cases check while facing with 
 * points on infinity
 * @author trident
 */
public class PointAtInfinityArithmeticsDecorator implements EllipticCurveOperator{
    
    private final EllipticCurveArithmetics arithmetics;
    
    public PointAtInfinityArithmeticsDecorator(EllipticCurveArithmetics arithmetics){
        this.arithmetics = arithmetics;
    }

    @Override
    public EllipticCurvePoint add(EllipticCurvePoint p1, EllipticCurvePoint p2) {
        if(p1.equals(negate(p2))) return EllipticCurvePoint.POINT_ON_INFINITY;
        if(p1.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return p2;
        if(p2.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return p1;
        return arithmetics.add(p1, p2);
    }

    @Override
    public EllipticCurvePoint mul(BigInteger times, EllipticCurvePoint p1) {
        if(p1.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return EllipticCurvePoint.POINT_ON_INFINITY;
        return arithmetics.mul(times, p1);
    }

    @Override
    public EllipticCurvePoint doub(EllipticCurvePoint p1) {
        if(p1.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return EllipticCurvePoint.POINT_ON_INFINITY;
        return arithmetics.doub(p1);
    }

    @Override
    public boolean belongsTo(EllipticCurvePoint p1) {
        if(p1.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return true;
        return arithmetics.belongsTo(p1);
    }

    @Override
    public EllipticCurvePoint negate(EllipticCurvePoint p1) {
        if(p1.equals(EllipticCurvePoint.POINT_ON_INFINITY)) return EllipticCurvePoint.POINT_ON_INFINITY;
        return arithmetics.negate(p1);
    }

    @Override
    public EllipticCurve getEllipticCurve() {
        return arithmetics.getEllipticCurve();
    }
    
    @Override
    public String toString(){
        return arithmetics.toString();
    }
    
}
