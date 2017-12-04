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

/**
 *
 * @author trident
 * @param <T> operand
 */
public interface FiniteFieldElementOperator<T extends FiniteFieldElement>{
    /**
     * add two finite field elements
     * @param el1
     * @param el2
     * @return sum of elements
     */
    public T add(T el1, T el2);
    
    /**
     * multiply finite field elements
     * @param el1
     * @param el2
     * @return multiple of elements
     */
    public T mul(T el1, T el2);
    
    /**
     * find inverse of element
     * @param el1
     * @return inverse
     */
    public T inv(T el1);
    
    /**
     * find the rest of element which belongs to the field
     * @param el1
     * @return 
     */
    public T mod(T el1);
    
    /**
     * check if the element belongs to this finite field
     * @param el1
     * @return 
     */
    public boolean belongsTo(T el1);
    
    
}
