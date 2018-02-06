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
package com.trident.crypto.field;

import com.trident.crypto.field.element.BinaryExtensionFieldElement;
import java.math.BigInteger;

/**
 *
 * @author trident
 */
public class BinaryExtensionField extends FiniteField{
    
    private final int  orderQ; // order exponent
    private final BinaryExtensionFieldElement irreduciblePoly; // irreducible polynom
    
    public BinaryExtensionField(BinaryExtensionFieldElement irreduciblePoly) {
        super(new BigInteger("2"),new BigInteger("2").pow(irreduciblePoly.getDegree()));
        this.orderQ = irreduciblePoly.getDegree();
        this.irreduciblePoly = irreduciblePoly;
    }

    public int getOrderQ() {
        return orderQ;
    }

    public BinaryExtensionFieldElement getIrreduciblePoly() {
        return irreduciblePoly;
    }
    
    @Override
    public String toString(){
        return "Binary extension field over "+getOrderP()+"^"+getOrderQ()+" with irreductible poly "+getIrreduciblePoly();
    }

    @Override
    public BigInteger getOrder() {
        return getOrderP().multiply(new BigInteger(Integer.toString(orderQ)));
    }
    
}
