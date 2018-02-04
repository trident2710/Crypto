package field;


import com.trident.crypto.field.element.BinaryExtensionFieldElement;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.element.IrreduciblePoly;
import com.trident.crypto.field.exception.MultiplicativeGroupException;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.math.BigInteger;
import java.util.Random;
import junit.framework.Assert;
import org.junit.Before;
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

public class BinaryExtensionFieldArithmeticsTest {
    private FiniteFieldElementArithmetics arithmetics;
    private int generator;
    private int generatorPow;
    private int times;
    private Random random;
    private IrreduciblePoly irreduciblePoly;
    
    @Before
    public void init(){
        random = new Random();
        generatorPow = random.nextInt(16);
        generator = 1<<generatorPow;
        times = 1000;
        irreduciblePoly = IrreduciblePoly.getPredefined()[generatorPow];
        arithmetics = FiniteFieldElementArithmetics.createFieldElementArithmetics(irreduciblePoly);
        System.out.println(arithmetics.getField());

    }
    
    @Test
    public void testAddition(){
        for(int i=0;i<times;i++){
            FiniteFieldElement x = arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            FiniteFieldElement y = arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            FiniteFieldElement res = arithmetics.add(x, y);
            Assert.assertEquals(res, arithmetics.mod(x).xor(arithmetics.mod(y)));
        } 
        
    }
    
    @Test
    public void testSubstraction(){
        for(int i=0;i<times;i++){
            FiniteFieldElement x = arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            FiniteFieldElement y = arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            FiniteFieldElement res = arithmetics.sub(x, y);
            Assert.assertEquals(res, arithmetics.mod(x).xor(arithmetics.mod(y)));
        } 
        
    }
      
    @Test
    public void testMultiplication(){
        for(int i=0;i<times;i++){
            BinaryExtensionFieldElement x = (BinaryExtensionFieldElement)arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            BinaryExtensionFieldElement y = (BinaryExtensionFieldElement)arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));            
            BinaryExtensionFieldElement lres = (BinaryExtensionFieldElement)arithmetics.mul(x, y);
            Assert.assertTrue(lres.getDegree()<irreduciblePoly.getDegree());
        } 
    }
    
    @Test
    public void testInverse(){
        for(int i=0;i<times;i++){
            BinaryExtensionFieldElement x = (BinaryExtensionFieldElement)arithmetics.getElementFactory().createFrom(new BigInteger(Integer.toString(random.nextInt(generator))));
            if(!(arithmetics.mod(x).equals(BigInteger.ZERO))){
                BinaryExtensionFieldElement res = (BinaryExtensionFieldElement)arithmetics.inv(x);
                FiniteFieldElement mul = arithmetics.mul(res, x);
                Assert.assertTrue(mul.equals(BigInteger.ONE));
            }   
        }   
    }
    
    @Test(expected = MultiplicativeGroupException.class)
    public void testInvMultiplicativeException(){
        BigInteger x = BigInteger.ZERO;
        arithmetics.inv(arithmetics.getElementFactory().createFrom(x));
    }
}
