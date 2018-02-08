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
package com.trident.crypto.field.operator;

import com.trident.crypto.field.PrimeField;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.element.FiniteFieldElementFactory;
import com.trident.crypto.field.exception.MultiplicativeGroupException;
import java.math.BigInteger;

/**
 *
 * @author trident
 */
class PrimeFieldElementArithmetics extends FiniteFieldElementArithmetics{
    
    public PrimeFieldElementArithmetics(PrimeField field, FiniteFieldElementFactory elementFactory){
        super(field, elementFactory);
    }

    @Override
    public FiniteFieldElement add(FiniteFieldElement el1, FiniteFieldElement el2) {
        return mod(getElementFactory().createFrom(el1.add(el2)));
    }

    @Override
    public FiniteFieldElement sub(FiniteFieldElement el1, FiniteFieldElement el2) {
        return mod(getElementFactory().createFrom(el1.add(complement(el2))));
    }

    @Override
    public FiniteFieldElement mul(FiniteFieldElement el1, FiniteFieldElement el2) throws MultiplicativeGroupException{
        if(el1.equals(BigInteger.ZERO)||el2.equals(BigInteger.ZERO)) return getElementFactory().createFrom(BigInteger.ZERO);
        return mod(getElementFactory().createFrom(el1.multiply(el2)));
    }

    @Override
    public FiniteFieldElement inv(FiniteFieldElement el1) {
        if(el1.equals(BigInteger.ZERO)) throw new MultiplicativeGroupException();
        return getElementFactory().createFrom(mod(el1).modInverse(getField().getOrderP()));
    }

    @Override
    public FiniteFieldElement mod(FiniteFieldElement el1) {
        return getElementFactory().createFrom(el1.mod(getField().getOrderP()));
    }

    @Override
    public FiniteFieldElement complement(FiniteFieldElement el1) {
        return getElementFactory().createFrom(el1.negate().mod(getField().getOrderP()));
    }
    
}
