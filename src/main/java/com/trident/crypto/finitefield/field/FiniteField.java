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
import com.trident.crypto.finitefield.element.FiniteFieldElement;
import com.trident.crypto.finitefield.element.FiniteFieldElementFactory;
import java.math.BigInteger;
import java.util.Iterator;

/**
 * defines the Galois field 
 * @author trident
 * @param <T>
 */
public abstract class FiniteField<T extends FiniteFieldElement>{
    
    protected final BigInteger orderP; // prime order
    protected final BigInteger size; // how many elements are in this finite field
    protected final Iterator<T> iterator;
    protected final FiniteFieldElementOperator<T> operator; // operator which is used to perform arithmetic operations
    protected final FiniteFieldElementFactory<T> elementFactory;
   
    protected FiniteField(BigInteger orderP, Iterator<T> iterator, FiniteFieldElementOperator<T> operator, FiniteFieldElementFactory<T> elementFactory){
        this.orderP = orderP;
        this.size = orderP;
        this.iterator = iterator;
        this.operator = operator;
        this.elementFactory = elementFactory;
    }
    
    protected FiniteField(BigInteger orderP, BigInteger size, Iterator<T> iterator, FiniteFieldElementOperator<T> operator, FiniteFieldElementFactory<T> elementFactory){
        this.orderP = orderP;
        this.size = size;
        this.iterator = iterator;
        this.operator = operator;
        this.elementFactory = elementFactory;
    }

    public BigInteger getOrderP() {
        return orderP;
    }
     
    /**
     * return size of the finite field
     * @return 
     */
    public BigInteger getSize(){
        return size;
    }
    
    /**
     * get string representation of the order of finite field
     * @return 
     */
    public abstract String getOrder();
    
    /**
     * get iterator through elements of field
     * @return 
     */
    public Iterator<T> getIterator(){
        return iterator;
    };
    
    /**
     * get operator to perform the arithmetic operations between finite field elements
     * @return 
     */
    public FiniteFieldElementOperator<T> getOperator(){
        return operator;
    };  

    /**
     * return the factory of elements of this field
     * @return 
     */
    public FiniteFieldElementFactory<T> getElementFactory() {
        return elementFactory;
    }
    
    
}
