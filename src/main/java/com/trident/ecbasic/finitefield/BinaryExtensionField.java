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
 * defines binary extension field
 * @author trident
 */
public class BinaryExtensionField extends FiniteField {
    
    private final int  orderQ; // order exponent
    private final BinaryExtensionFieldElementIterator iterator;
    private final BinaryExtensionFieldElementOperator operator;
    
    public BinaryExtensionField(int orderQ){
        super(new BigInteger("2"),new BigInteger("2").pow(orderQ));
        this.orderQ = orderQ;
        this.iterator = new BinaryExtensionFieldElementIterator();
        this.operator = new BinaryExtensionFieldElementOperator();
    }

    public int getOrderQ() {
        return orderQ;
    }
    
    @Override
    public String getOrder() {
        return getOrderP()+"^"+getOrderQ();
    }
    
    private static class BinaryExtensionFieldElement extends FiniteFieldElement{
       
        public BinaryExtensionFieldElement(byte[] binaryRepresentation) {
            super(binaryRepresentation);
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<getBinaryRepresetation().length;i++){
                for(int j=0;j<8;j++){
                    if((getBinaryRepresetation()[i]&(1<<j))!=0)
                        sb.append(8*i+j).append("^x").append(" ");
                }
            }
            return sb.reverse().toString();
        }       
    }

    private class BinaryExtensionFieldElementOperator implements FiniteFieldElementOperator<BinaryExtensionFieldElement>{

        @Override
        public BinaryExtensionFieldElement add(BinaryExtensionFieldElement el1, BinaryExtensionFieldElement el2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
        }

        @Override
        public BinaryExtensionFieldElement mul(BinaryExtensionFieldElement el1, BinaryExtensionFieldElement el2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public BinaryExtensionFieldElement inv(BinaryExtensionFieldElement el1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }  
    }
    
    private class BinaryExtensionFieldElementIterator implements Iterator<BinaryExtensionFieldElement>{

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public BinaryExtensionFieldElement next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }   
    }
    
    @Override
    public Iterator<? extends FiniteFieldElement> getIterator(){
        return iterator;
    }
    
    @Override
    public FiniteFieldElementOperator<? extends FiniteFieldElement> getOperator(){
        return operator;
    }
      
}
