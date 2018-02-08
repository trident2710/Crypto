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


import com.trident.crypto.field.BinaryExtensionField;
import com.trident.crypto.field.element.BinaryExtensionFieldElementFactory;
import com.trident.crypto.field.element.FiniteFieldElement;
import com.trident.crypto.field.exception.MultiplicativeGroupException;
import com.trident.crypto.util.Tuple;
import java.math.BigInteger;

/**
 * arithmetics of elements in binary extension field
 * @author trident
 */
class BinaryExtensionFieldElementArithmetics extends FiniteFieldElementArithmetics{

    private final FiniteFieldElement irreduciblePoly;
    
    public BinaryExtensionFieldElementArithmetics(BinaryExtensionField field, BinaryExtensionFieldElementFactory elementFactory) {
        super(field, elementFactory);
        this.irreduciblePoly = field.getIrreduciblePoly();
    }

    /**
    * division using Euclid algorithm
    * @param a
    * @param b
    * @return K ->  a mod b,  V -> a div b
    */
   private Tuple<FiniteFieldElement,FiniteFieldElement> divEuclid(FiniteFieldElement a, FiniteFieldElement b){
       if(b.compareTo(BigInteger.ZERO)==0) throw new RuntimeException("cannot div by zero");

       if(a.getDegree()<b.getDegree()){
           return new Tuple<>(a,getElementFactory().createFrom(BigInteger.ZERO));
       }
       
       if(a.equals(b)){
           return new Tuple<>(getElementFactory().createFrom(BigInteger.ZERO),getElementFactory().createFrom(BigInteger.ONE));
       }

       FiniteFieldElement q = getElementFactory().createFrom(BigInteger.ZERO);
       FiniteFieldElement rst = getElementFactory().createFrom(a);
       while(rst.getDegree()>=b.getDegree()){
               int pivot = rst.getDegree() - b.getDegree();
               q = getElementFactory().createFrom(q.xor(BigInteger.ONE.shiftLeft(pivot)));
               rst = getElementFactory().createFrom(rst.xor(getElementFactory().createFrom(b.shiftLeft(pivot))));  
       }
       return new Tuple<>(rst,q);
   }
    
    @Override
    public FiniteFieldElement add(FiniteFieldElement el1, FiniteFieldElement el2) {
        return mod(getElementFactory().createFrom(el1.xor(el2)));
    }

    @Override
    public FiniteFieldElement sub(FiniteFieldElement el1, FiniteFieldElement el2) {
        return add(el1, el2);
    }

    @Override
    public FiniteFieldElement mul(FiniteFieldElement el1, FiniteFieldElement el2) {  
        if(el1.equals(BigInteger.ZERO)||el2.equals(BigInteger.ZERO)) return getElementFactory().createFrom(BigInteger.ZERO);
        el1 = mod(el1);
        el2 = mod(el2);
        BigInteger el2b = el2;
        BigInteger product = BigInteger.ZERO;
        for(int i=0;i<=el1.getDegree();i++){
            if(el1.testBit(i)) product = product.xor(el2b); 
            el2b = el2b.shiftLeft(1);
        }
        return mod(getElementFactory().createFrom(product));
    }

    @Override
    public FiniteFieldElement inv(FiniteFieldElement el1) throws MultiplicativeGroupException{
        if(mod(el1).equals(BigInteger.ZERO)) throw new MultiplicativeGroupException();
        el1 = mod(el1);
        
        FiniteFieldElement t = getElementFactory().createFrom(BigInteger.ZERO);
        FiniteFieldElement nt = getElementFactory().createFrom(BigInteger.ONE);
        FiniteFieldElement r = irreduciblePoly;
        FiniteFieldElement nr = getElementFactory().createFrom(el1);
        
        while (nr.compareTo(BigInteger.ZERO)>0) {      
            FiniteFieldElement q = divEuclid(r,nr).getV();
            FiniteFieldElement tmp = getElementFactory().createFrom(t);
            t = getElementFactory().createFrom(nt);
            nt = sub(tmp, mul(q, nt));
            tmp = getElementFactory().createFrom(r);
            r = getElementFactory().createFrom(nr);
            nr = sub(tmp, mul(q, nr));
        }
        return t;
    }

    @Override
    public FiniteFieldElement mod(FiniteFieldElement el1) {
        return divEuclid(el1,irreduciblePoly).getK();
    }  

    @Override
    public FiniteFieldElement complement(FiniteFieldElement el1) {
        return mod(el1);
    }
}
