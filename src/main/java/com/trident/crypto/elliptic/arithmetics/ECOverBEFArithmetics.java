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
import com.trident.crypto.elliptic.EllipticCurvePoint;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;

/**
 *
 * @author trident
 */
public class ECOverBEFArithmetics extends EllipticCurveArithmetics{
    
     public ECOverBEFArithmetics(EllipticCurve ellipticCurve) {
        super(ellipticCurve);
    }

    @Override
    public EllipticCurvePoint add(EllipticCurvePoint p1, EllipticCurvePoint p2) {
        if(Math.abs(p1.compareTo(p2))==0) return doub(p1);
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
            
        FiniteFieldElement dy = f.add(p2.getPointY(), p1.getPointY()); //p2.y + p1.y
        FiniteFieldElement dx = f.add(p2.getPointX(), p1.getPointX()); //p2.x + p1.x 
        FiniteFieldElement m  = f.mul(dy, f.inv(dx)); // dy/dx
        FiniteFieldElement p3x = f.add(f.add(f.add(f.add(f.mul(m, m),p1.getPointX()),p2.getPointX()),getEllipticCurve().getA()),m); // m^2 + m + p1.x + p2.x + a
        FiniteFieldElement p3y = f.add(f.add(f.mul(m, f.add(p1.getPointX(), p3x)),p1.getPointY()),p3x); // m*(p1.x + p3.x) + p3.x + p1.y
        return EllipticCurvePoint.create(p3x, p3y);
    }

    @Override
    public EllipticCurvePoint mul(BigInteger times, EllipticCurvePoint p1) {
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
            times = times.shiftRight(1); // times = times/2
        }
        return isOdd?add(tmp, p1):tmp;
    }

    @Override
    public EllipticCurvePoint doub(EllipticCurvePoint p1) {
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
        
        FiniteFieldElement m  = f.add(f.mul(p1.getPointY(), f.inv(p1.getPointX())),p1.getPointX()); // m = p1.x + p1.y/p1.x
        FiniteFieldElement p3x = f.add(f.add(f.mul(m, m),m),getEllipticCurve().getA()); // m^2 + m + a
        FiniteFieldElement p3y = f.add(f.add(f.mul(p1.getPointX(), p1.getPointX()),p3x),f.mul(m, p3x)); // p1.x^2 + m*p3.x + p3.x
        return EllipticCurvePoint.create(p3x, p3y);
    }

    @Override
    public boolean belongsTo(EllipticCurvePoint p1) {
        FiniteFieldElementArithmetics f = ellipticCurve.getFieldArithmetics();
        FiniteFieldElement y2 = f.mul(p1.getPointY(), p1.getPointY()); //y^2
        FiniteFieldElement xy = f.mul(p1.getPointX(), p1.getPointY()); //xy
        FiniteFieldElement lp = f.add(y2, xy); //y^2+xy
        FiniteFieldElement x2 = f.mul(p1.getPointX(), p1.getPointX()); //x^2
        FiniteFieldElement x3 = f.mul(x2,p1.getPointX()); // x^3
        FiniteFieldElement ax2 = f.mul(x2, getEllipticCurve().getA()); // ax^2
        FiniteFieldElement res = f.add(f.add(x3, ax2),getEllipticCurve().getB()); // x^3+ax^2+b
        return lp.equals(res); // y^2 +xy = x^3 +ax^2 + b
    }
}
