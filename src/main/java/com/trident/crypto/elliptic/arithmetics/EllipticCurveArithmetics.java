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
import com.trident.crypto.elliptic.nist.SECP;

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
    public static EllipticCurveArithmetics createFrom(SECP spec){
        return spec.getType()?
                new ECOverPFArithmetics(EllipticCurve.createFrom(spec)):
                new ECOverBEFArithmetics(EllipticCurve.createFrom(spec));
    }
    
    /**
     * provides the human readable information about this arithmetics and its elliptic curve
     * @return 
     */
    @Override
    public String toString(){
        return new StringBuilder().append("Elliptic curve arithmetics defined over:\n").append(getEllipticCurve()).toString();
    }
}
