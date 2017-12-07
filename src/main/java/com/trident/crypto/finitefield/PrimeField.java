/*
 * Copyright 2017 trident.
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
package com.trident.crypto.finitefield;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * defines prime field
 * @author trident
 */
public class PrimeField extends FiniteField<PrimeFieldElement>{
    
    private final PrimeFieldElementIterator iterator;
    private final PrimeFieldElementOperator operator;

    /**
     * @param orderP should be prime
     */
    public PrimeField(BigInteger orderP) {
        super(orderP);
        this.iterator = new PrimeFieldElementIterator();
        this.operator = new PrimeFieldElementOperator();
    }

    @Override
    public String getOrder() {
        return ""+getOrderP();
    }

    @Override
    public PrimeFieldElementIterator getIterator() {
        return iterator;
    }

    @Override
    public PrimeFieldElementOperator getOperator() {
        return operator;
    }

    @Override
    public PrimeFieldElement create(BigInteger val) {
        return operator.mod(new PrimeFieldElement(val));
    }
    
    public class PrimeFieldElementOperator implements FiniteFieldElementOperator<PrimeFieldElement>{

        @Override
        public PrimeFieldElement add(PrimeFieldElement el1, PrimeFieldElement el2) {
            return new PrimeFieldElement(el1.add(el2).mod(orderP));
        }

        @Override
        public PrimeFieldElement mul(PrimeFieldElement el1, PrimeFieldElement el2) {
            return new PrimeFieldElement(el1.multiply(el2).mod(orderP));
        }

        @Override
        public PrimeFieldElement inv(PrimeFieldElement el1) {
            return new PrimeFieldElement(el1.modInverse(orderP));
        }  

        @Override
        public PrimeFieldElement mod(PrimeFieldElement el1) {
            return new PrimeFieldElement(el1.mod(orderP));
        }

        @Override
        public PrimeFieldElement sub(PrimeFieldElement el1, PrimeFieldElement el2) {
            return new PrimeFieldElement(el1.subtract(el2).mod(orderP));
        }
    }
    
    public class PrimeFieldElementIterator implements Iterator<PrimeFieldElement>{

        private PrimeFieldElement current;
        
        private PrimeFieldElementIterator(){
            current = new PrimeFieldElement("0");
        }
        
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public PrimeFieldElement next() {
            PrimeFieldElement res = new PrimeFieldElement(current);
            current = new PrimeFieldElement(current.add(new BigInteger("1")).mod(orderP));
            return res;
        }
    }  
    
    @Override
    public String toString(){
        return "Prime field over "+orderP;
    }
}