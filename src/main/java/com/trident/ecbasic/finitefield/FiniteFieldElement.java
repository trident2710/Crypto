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
 * defines the element of the finite field 
 * @author trident
 */
public abstract class FiniteFieldElement  {
    
    private final byte[] binaryRepresentation; // element of any finite field can be represented in form of bits

    public FiniteFieldElement(byte[] binaryRepresentation){
        this.binaryRepresentation = binaryRepresentation;
    }
    
    public byte[] getBinaryRepresetation(){
        return binaryRepresentation;
    }
}
