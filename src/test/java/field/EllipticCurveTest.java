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
package field;

import com.trident.crypto.elliptic.arithmetics.EllipticCurveArithmetics;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import com.trident.crypto.elliptic.nist.SECP;
import java.math.BigInteger;
import java.util.Random;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author trident
 */
public class EllipticCurveTest {
    private int times;
    private Random random;
    
    @Before
    public void init(){
        random = new Random();
        times = 1<<10;
    }
    
    @Test
    public void testPointOperations(){
        for(int j = 0;j<SECP.values().length;j++){
            EllipticCurveArithmetics ar = EllipticCurveArithmetics.createFrom(SECP.values()[j]);
            EllipticCurvePoint p = ar.getEllipticCurve().getG();
            System.out.println(ar);
        
            for(int i=0;i<times;i++){
                p = ar.doub(p);
                Assert.assertTrue(ar.belongsTo(p));
            }

            for(int i=0;i<times;i++){
                EllipticCurvePoint doub = ar.doub(p);
                p = ar.add(p,p);
                Assert.assertTrue(ar.belongsTo(p)&&p.equals(doub));
            }

            for(int i=0;i<times;i++){
                EllipticCurvePoint doub = ar.doub(p);
                p = ar.mul(new BigInteger("2"), p);
                Assert.assertTrue(ar.belongsTo(p)&&p.equals(doub));
            }
        }    
    }   
}
