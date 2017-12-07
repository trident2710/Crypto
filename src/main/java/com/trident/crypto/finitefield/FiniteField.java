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
 * defines the Galois field 
 * @author trident
 * @param <T>
 */
public abstract class FiniteField<T extends FiniteFieldElement> implements FiniteFieldElementFactory<T>{
    
    protected final BigInteger orderP; // prime order
    protected final BigInteger size; // how many elements are in this finite field
   
    protected FiniteField(BigInteger orderP){
        this.orderP = orderP;
        this.size = orderP;
    }
    
    protected FiniteField(BigInteger orderP, BigInteger size){
        this.orderP = orderP;
        this.size = size;
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
    public abstract Iterator<T> getIterator();
    
    /**
     * get operator to perform the arithmetic operations between finite field elements
     * @return 
     */
    public abstract FiniteFieldElementOperator<T> getOperator();  
}
