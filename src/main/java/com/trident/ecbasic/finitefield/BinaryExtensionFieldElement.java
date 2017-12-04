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

/**
 *
 * @author trident
 */
public class BinaryExtensionFieldElement extends FiniteFieldElement{
    
    private int degree;
    
    public BinaryExtensionFieldElement(byte[] binaryRepresentation) {
        super(binaryRepresentation);
        degree = 0;
    }
    
    public BinaryExtensionFieldElement(String stringNumber){
        super(stringNumber);
    }
    
    public BinaryExtensionFieldElement(BigInteger integer){
        super(integer);
    }
    
    
    public int getDegree() {
        if(this.compareTo(ZERO)==0) return -1;
        
        byte[] array = toByteArray();
        if(degree == 0){
            for(int i = 0; i< array.length;i++){
                for(int j=7; j >= 0; j--){
                    if((array[i]&(1<<j))!=0){
                        degree = 8*(array.length-i-1)+j;
                        return degree;
                    }
                }
            }
        }
        return degree;
    }

    @Override
    public String toString(){
        if(this.compareTo(ZERO)==0) return "0";
        
        StringBuilder sb = new StringBuilder();
        byte[] array = toByteArray();
        for(int i = 0; i< array.length;i++){
            for(int j=7; j >= 0; j--){
                if(i==array.length-1&&j==0){
                    if((array[i]&(1<<j))!=0)
                        sb.append("1");
                }else
                    if((array[i]&(1<<j))!=0)
                        sb.append("x^").append(8*(array.length-i-1)+j).append(" ");
            }
        }
        return sb.toString();
    }   
}
