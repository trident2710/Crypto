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
import com.trident.crypto.elliptic.nist.SECP;
import java.math.BigInteger;

/**
 *
 * defines class representing the arithmetics over provided elliptic curve, i.e. 
 * point addition, multiplication, doubling, belonging test
 * @author trident
 */
public abstract class EllipticCurveArithmetics implements EllipticCurveOperator{
    
    //olliptic curve over which this arithmetics is defined
    protected final EllipticCurve ellipticCurve;

    public EllipticCurveArithmetics(EllipticCurve ellipticCurve) {
        this.ellipticCurve = ellipticCurve;
    }
    
    @Override
    public EllipticCurve getEllipticCurve() {
        return ellipticCurve;
    }
    
    /**
     * static factory method to create the arithmetics over the elliptic curve 
     * defined by the standard specification
     * 
     * should prefer this instead of constructor call
     * @param spec
     * @return 
     */
    public static EllipticCurveOperator createFrom(SECP spec){
        return new PointAtInfinityArithmeticsDecorator(spec.getType()?
                new ECOverPFArithmetics(EllipticCurve.createFrom(spec)):
                new ECOverBEFArithmetics(EllipticCurve.createFrom(spec)));
    }
    
    /**
     * provides the human readable information about this arithmetics and its elliptic curve
     * @return 
     */
    @Override
    public String toString(){
        return new StringBuilder().append("Elliptic curve arithmetics defined over:\n").append(getEllipticCurve()).toString();
    }
       
    @Override
    public EllipticCurvePoint mul(BigInteger times, EllipticCurvePoint p1){
        if(times.signum()==-1) throw new RuntimeException("negative times");
        if(times.compareTo(BigInteger.ZERO) == 0)
            throw new RuntimeException("multiply to zero");
        if(times.compareTo(BigInteger.ONE) == 0)
            return p1;
        
        EllipticCurvePoint temp = EllipticCurvePoint.create(p1.getPointX(), p1.getPointY());
        times = times.subtract(BigInteger.ONE);
        while (times.compareTo(BigInteger.ZERO)>0){
            if (times.testBit(0)){
                if (temp.equals(p1))
                    temp = doub(temp);
                else{
                    if(temp.equals(negate(p1))) // if adding p + (-p)
                        temp = EllipticCurvePoint.POINT_ON_INFINITY;
                    else
                        temp = add(temp,p1);
                }   
                times = times.subtract(BigInteger.ONE);
            }
            times = times.shiftRight(1);
            p1 = doub(p1);
        }
        return temp;
    }
}
