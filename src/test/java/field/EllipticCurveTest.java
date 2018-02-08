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

import com.trident.crypto.elliptic.EllipticCurve;
import com.trident.crypto.elliptic.EllipticCurveOperator;
import com.trident.crypto.elliptic.arithmetics.EllipticCurveArithmetics;
import com.trident.crypto.elliptic.EllipticCurvePoint;
import com.trident.crypto.elliptic.arithmetics.ECOverPFArithmetics;
import com.trident.crypto.elliptic.arithmetics.PointAtInfinityArithmeticsDecorator;
import com.trident.crypto.elliptic.nist.SECP;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
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
            EllipticCurveOperator ar = EllipticCurveArithmetics.createFrom(SECP.values()[j]);
            EllipticCurvePoint p = ar.getEllipticCurve().getG();
            BigInteger n = ar.getEllipticCurve().getN();
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
    
    
    @Test
    public void testSymmetry(){
        for(int j = 0;j<SECP.values().length;j++){
            EllipticCurveOperator ar = EllipticCurveArithmetics.createFrom(SECP.values()[j]);
            System.out.println(ar);
            EllipticCurvePoint p = ar.getEllipticCurve().getG();
            EllipticCurvePoint p31= ar.add(ar.add(p, p),p);
            EllipticCurvePoint p32= ar.mul(new BigInteger("3"), p);
            EllipticCurvePoint p33= ar.add(ar.doub(p),p);
            
            Assert.assertEquals(p31, p32);
            Assert.assertEquals(p32, p33);
        }
    }
    
    
    @Test
    public void testPointOnInfinity(){
        for(int j = 0;j<SECP.values().length;j++){
            EllipticCurveOperator ar = EllipticCurveArithmetics.createFrom(SECP.values()[j]);
            EllipticCurvePoint G = ar.getEllipticCurve().getG();
            EllipticCurvePoint nG = ar.mul(ar.getEllipticCurve().getN(), G);
            EllipticCurvePoint notG = ar.negate(G);
            EllipticCurvePoint inf = ar.add(G, notG);
            EllipticCurvePoint minf = ar.mul(new BigInteger("42"), inf);
            EllipticCurvePoint ainf = ar.add(inf, inf);
            EllipticCurvePoint ninf = ar.negate(inf);
            EllipticCurvePoint pinf = ar.add(inf, G);
            
            Assert.assertTrue(!G.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(nG.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(!notG.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(inf.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(minf.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(ainf.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(ninf.equals(EllipticCurvePoint.POINT_ON_INFINITY));
            Assert.assertTrue(!pinf.equals(EllipticCurvePoint.POINT_ON_INFINITY));
        }
    }
    
    
    @Test
    public void testCustomEC(){
        FiniteFieldElementArithmetics fa = FiniteFieldElementArithmetics.createFieldElementArithmetics(BigInteger.valueOf(17));
        FiniteFieldElement a = fa.getElementFactory().createFrom(BigInteger.valueOf(2));
        FiniteFieldElement b = fa.getElementFactory().createFrom(BigInteger.valueOf(3));
        EllipticCurvePoint ecp = EllipticCurvePoint.create(fa.getElementFactory().createFrom(BigInteger.valueOf(3)), fa.getElementFactory().createFrom(BigInteger.valueOf(6)));
        EllipticCurve ec = new EllipticCurve(fa, a, b, ecp, BigInteger.ONE, BigInteger.ONE);
        EllipticCurveOperator op = new PointAtInfinityArithmeticsDecorator(new ECOverPFArithmetics(ec));
        
        
        for(int j = 1;j<20;j++){
            System.out.println(op.mul(BigInteger.valueOf(j), op.getEllipticCurve().getG()));
        }
    }
}
