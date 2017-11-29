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
package com.trident.ecbasic.finitefield;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * defines prime field
 * @author trident
 */
class PrimeField extends FiniteField{
    
    private final PrimeFieldElementIterator iterator;
    private final PrimeFieldElementOperator operator;

    
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
    
    private static class PrimeFieldElement extends FiniteFieldElement{
       
        public PrimeFieldElement(byte[] binaryRepresentation) {
            super(binaryRepresentation);
        }

        @Override
        public String toString(){
            return new BigInteger(getBinaryRepresetation()).toString();
        }       
    }

    private class PrimeFieldElementOperator implements FiniteFieldElementOperator<PrimeFieldElement>{

        @Override
        public PrimeFieldElement add(PrimeFieldElement el1, PrimeFieldElement el2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PrimeFieldElement mul(PrimeFieldElement el1, PrimeFieldElement el2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PrimeFieldElement inv(PrimeFieldElement el1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }  
    }
    
    private class PrimeFieldElementIterator implements Iterator<PrimeFieldElement>{

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PrimeFieldElement next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }  
}
