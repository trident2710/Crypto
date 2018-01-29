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

import com.trident.crypto.field.element.BinaryExtensionFieldElement;
import com.trident.crypto.field.element.BinaryExtensionFieldElementFactory;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.element.FiniteFieldElementFactory;
import java.math.BigInteger;
import java.util.Random;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author trident
 */
public class BinaryExtensionFieldElementTest {
    
    @Test
    public void testFromString(){
        Random r = new Random();
        int bound = (1<<10);
        FiniteFieldElementFactory fieldElementFactory  = new BinaryExtensionFieldElementFactory();
        
        for(int i=0;i<bound;i++){
            FiniteFieldElement f = fieldElementFactory.createFrom(new BigInteger(""+r.nextInt(bound)));
            Assert.assertTrue(f.equals(BinaryExtensionFieldElement.fromString(f.toString())));
        }
        
        try {
            FiniteFieldElement f = fieldElementFactory.createFrom(new BigInteger(""+r.nextInt(bound)));
            String wrong = f.toString()+" wrong";
            BinaryExtensionFieldElement.fromString(wrong);
            Assert.fail();
        } catch (Exception e) {}
        
        try {
            FiniteFieldElement f = fieldElementFactory.createFrom(new BigInteger(""+r.nextInt(bound)));
            String wrong = f.toString()+" 1";
            BinaryExtensionFieldElement.fromString(wrong);
            Assert.fail();
        } catch (Exception e) {}
        
        try {
            FiniteFieldElement f = fieldElementFactory.createFrom(new BigInteger(""+r.nextInt(bound)));
            String wrong = f.toString()+" x^-1";
            BinaryExtensionFieldElement.fromString(wrong);
            Assert.fail();
        } catch (Exception e) {}
        
        try {
            FiniteFieldElement f = fieldElementFactory.createFrom(new BigInteger(""+r.nextInt(bound)));
            String wrong = f.toString()+" x^1"+" x^1";
            BinaryExtensionFieldElement.fromString(wrong);
            Assert.fail();
        } catch (Exception e) {}
    }
}
