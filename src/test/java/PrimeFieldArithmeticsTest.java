
import com.trident.crypto.field.exception.MultiplicativeGroupException;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;
import java.util.Random;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

/**
 *
 * @author trident
 */

public class PrimeFieldArithmeticsTest {
    private FiniteFieldElementArithmetics arithmetics;
    private int generator;
    private BigInteger biGenerator;
    private int times;
    private Random random;
    
    @Before
    public void init(){
        generator = 1009;
        times = 1000;
        biGenerator = new BigInteger(Integer.toString(generator));
        arithmetics = FiniteFieldElementArithmetics.createPrimeFieldElementArithmetics(biGenerator);
        System.out.println(arithmetics.getField());
        random = new Random();
    }
    
    @Test
    public void testAddition(){
        for(int i=0;i<times;i++){
            BigInteger x = new BigInteger(Integer.toString(random.nextInt(generator)));
            BigInteger y = new BigInteger(Integer.toString(random.nextInt(generator)));
            BigInteger res = x.add(y).mod(biGenerator);
            Assert.assertEquals(res.compareTo(arithmetics.add(arithmetics.getElementFactory().createFrom(x), arithmetics.getElementFactory().createFrom(y))),0);
        } 
    }
    
    @Test
    public void testSubstraction(){
        for(int i=0;i<times;i++){
            BigInteger x = new BigInteger(Integer.toString(random.nextInt(generator)));
            BigInteger y = new BigInteger(Integer.toString(random.nextInt(generator)));
            BigInteger res = x.subtract(y).mod(biGenerator);
            BigInteger lres = arithmetics.sub(arithmetics.getElementFactory().createFrom(x), arithmetics.getElementFactory().createFrom(y));
            Assert.assertEquals(res.compareTo(lres),0);
        } 
    }
    
    @Test
    public void testMultiplication(){
        for(int i=0;i<times;i++){
            BigInteger x = new BigInteger(Integer.toString(random.nextInt(generator)));
            BigInteger y = new BigInteger(Integer.toString(random.nextInt(generator)));
            if(!(x.mod(biGenerator).equals(BigInteger.ZERO)||y.mod(biGenerator).equals(BigInteger.ZERO))){
                BigInteger res = x.multiply(y).mod(biGenerator);
                BigInteger lres = arithmetics.mul(arithmetics.getElementFactory().createFrom(x), arithmetics.getElementFactory().createFrom(y));
                Assert.assertEquals(res.compareTo(lres),0);
            }    
        } 
    }
    
    @Test
    public void testInverse(){
        for(int i=0;i<times;i++){
            BigInteger x = new BigInteger(Integer.toString(random.nextInt(generator)));
            if(!(x.mod(biGenerator).equals(BigInteger.ZERO))){
                BigInteger res = x.mod(biGenerator).modInverse(biGenerator);
                BigInteger lres = arithmetics.inv(arithmetics.getElementFactory().createFrom(x));
                Assert.assertEquals(res.compareTo(lres),0);
            }   
        } 
    }
    
    @Test(expected = MultiplicativeGroupException.class)
    public void testMulMultiplicativeException(){
        BigInteger x = BigInteger.ZERO;
        arithmetics.mul(arithmetics.getElementFactory().createFrom(x), arithmetics.getElementFactory().createFrom(x));
    }
    
    @Test(expected = MultiplicativeGroupException.class)
    public void testInvMultiplicativeException(){
        BigInteger x = BigInteger.ZERO;
        arithmetics.inv(arithmetics.getElementFactory().createFrom(x));
    }
}
