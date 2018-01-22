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
package com.trident.crypto.finitefield.field;

import com.trident.crypto.finitefield.element.FiniteFieldElementOperator;
import com.trident.crypto.finitefield.element.IrreduciblePoly;
import com.trident.crypto.finitefield.element.BinaryExtensionFieldElement;
import com.trident.crypto.finitefield.element.BinaryExtensionFieldElementFactory;
import com.trident.crypto.finitefield.util.Tuple;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Iterator;

/**
 * defines binary extension field
 * @author trident
 */
public class BinaryExtensionField extends FiniteField<BinaryExtensionFieldElement> {
    
    private final int  orderQ; // order exponent
    private final IrreduciblePoly irreduciblePoly; // irreducible polynom
    
    private BinaryExtensionField(int orderQ, IrreduciblePoly irreduciblePoly, BinaryExtensionFieldElementIterator iterator, BinaryExtensionFieldElementOperator operator, BinaryExtensionFieldElementFactory elementFactory){
        super(new BigInteger("2"),new BigInteger("2").pow(orderQ),iterator,operator, elementFactory);
        this.orderQ = orderQ;
        this.irreduciblePoly = irreduciblePoly;
    }

    public int getOrderQ() {
        return orderQ;
    }
    
    @Override
    public String getOrder() {
        return getOrderP()+"^"+getOrderQ();
    }
    
    public IrreduciblePoly getIrreduciblePoly() {
        return irreduciblePoly;
    }
    
    /**
     * provides arithmetic operations for the binary field
     */
    private class BinaryExtensionFieldElementOperator implements FiniteFieldElementOperator<BinaryExtensionFieldElement>{
        
        /**
         * 
         * @param el1
         * @param el2
         * @return  el1 + el2 mod irreducible poly
         */
        @Override
        public BinaryExtensionFieldElement add(BinaryExtensionFieldElement el1, BinaryExtensionFieldElement el2) {
            return mod(getElementFactory().create(el1.xor(el2)));
        }

        /**
         * 
         * @param el1
         * @param el2
         * @return el1 * el2 mod irreducible poly
         */
        @Override
        public BinaryExtensionFieldElement mul(BinaryExtensionFieldElement el1, BinaryExtensionFieldElement el2) {
            BigInteger product = BigInteger.ZERO;
            byte[] el1b = mod(el1).toByteArray();
            BitSet el1s = BitSet.valueOf(el1b);
            BinaryExtensionFieldElement el2n = mod(el2);
            for(int i=0;i<el1b.length*8;i++){
                if(el1s.get(i)) product = product.xor(el2n.shiftLeft(i));
            }
            return divEuclid(getElementFactory().create(product),irreduciblePoly).getK();
        }

        /**
         * finds the inverse of el1 in this field. i.e. t*el1 = 1 mod irreducible poly
         * @param el1
         * @return inverse of el1
         */
        @Override
        public BinaryExtensionFieldElement inv(BinaryExtensionFieldElement el1) {
            BinaryExtensionFieldElement t = getElementFactory().create(BigInteger.ZERO);
            BinaryExtensionFieldElement nt = getElementFactory().create(BigInteger.ONE);
            BinaryExtensionFieldElement r = getElementFactory().create(irreduciblePoly);
            BinaryExtensionFieldElement nr = getElementFactory().create(mod(el1));
            BinaryExtensionFieldElement q;
            
            while (nr.compareTo(BigInteger.ZERO)>0) {      
                q = divEuclid(r,nr).getV();
                BinaryExtensionFieldElement tmp = getElementFactory().create(t);
                t = getElementFactory().create(nt);
                nt = add(tmp, mul(q, nt));
                tmp = getElementFactory().create(r);
                r = getElementFactory().create(nr);

                nr = add(tmp, mul(q, nr));
            }
            return t;
        }  
            
        /**
         * division using Euclid algorithm
         * @param a
         * @param b
         * @return K ->  a mod b,  V -> a div b
         */
        private Tuple<BinaryExtensionFieldElement,BinaryExtensionFieldElement> divEuclid(BinaryExtensionFieldElement a,BinaryExtensionFieldElement b){
            if(b.compareTo(BigInteger.ZERO)==0) throw new RuntimeException("cannot div by zero");
            
            if(a.getDegree()<b.getDegree()){
                return new Tuple<>(a,getElementFactory().create(BigInteger.ZERO));
            }

            BinaryExtensionFieldElement q = getElementFactory().create(BigInteger.ZERO);
            BinaryExtensionFieldElement rst = getElementFactory().create(a);
            while(rst.getDegree()>=b.getDegree()){
                    int pivot = rst.getDegree() - b.getDegree();
                    q = getElementFactory().create(q.xor(BigInteger.ONE.shiftLeft(pivot)));
                    rst = getElementFactory().create(rst.xor(getElementFactory().create(b.shiftLeft(pivot))));  
            }
            return new Tuple<>(rst,q);
        }

        /**
         * @param el1 finite field element
         * @return el1 mod irreducible poly
         */
        @Override
        public BinaryExtensionFieldElement mod(BinaryExtensionFieldElement el1) {
            return divEuclid(el1, irreduciblePoly).getK();
        }

        @Override
        public BinaryExtensionFieldElement sub(BinaryExtensionFieldElement el1, BinaryExtensionFieldElement el2) {
            return add(el1, el2);
        }        
    }
    
    /**
     * iterates through the elements of this field
     * Be careful because this iterator is infinite because fields are enclosed
     */
    private class BinaryExtensionFieldElementIterator implements Iterator<BinaryExtensionFieldElement>{
        
        private BinaryExtensionFieldElement current;
        
        public BinaryExtensionFieldElementIterator(){
            this.current = getElementFactory().create(BigInteger.ONE);
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public BinaryExtensionFieldElement next() {
            current =  operator.mul(current, getElementFactory().create(new BigInteger("2")));
            return current;
        }   
    }
        
    @Override
    public String toString(){
        return "Binary extension field over "+orderP+"^"+orderQ+" with irreductible poly "+irreduciblePoly;
    }    
}
