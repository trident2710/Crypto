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
import com.trident.crypto.field.element.IrreduciblePoly;
import com.trident.crypto.field.operator.FiniteFieldElementArithmetics;
import java.util.Random;
import org.junit.Before;

/**
 *
 * @author trident
 */
public class EllipticCurveTest {
    private FiniteFieldElementArithmetics arithmetics;
    private int irredPolyDegree;
    private int times;
    private Random random;
    private IrreduciblePoly irreduciblePoly;
    private EllipticCurve ellipticCurve;
    
    @Before
    public void init(){
        random = new Random();
        irredPolyDegree = random.nextInt(IrreduciblePoly.getPredefined().length);
        times = 1000;
        irreduciblePoly = IrreduciblePoly.getPredefined()[irredPolyDegree];
        arithmetics = FiniteFieldElementArithmetics.createFieldElementArithmetics(irreduciblePoly);
        System.out.println(arithmetics.getField());
    }
    
}
