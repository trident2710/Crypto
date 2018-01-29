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
package com.trident.crypto.field.element;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 *
 * @author trident
 */
@SuppressWarnings("serial")
public class IrreduciblePoly extends BinaryExtensionFieldElement{
    
    private static final IrreduciblePoly[] PREDEFINED_POLYS;
    static {
        PREDEFINED_POLYS = new IrreduciblePoly[32];
        PREDEFINED_POLYS[0]  = (new IrreduciblePoly(0b000000000000000000000000000000011l)); // x + 1
        PREDEFINED_POLYS[1]  = (new IrreduciblePoly(0b000000000000000000000000000000111l)); // x^2 + x + 1
        PREDEFINED_POLYS[2]  = (new IrreduciblePoly(0b000000000000000000000000000001011l)); // x^3 + x + 1
        PREDEFINED_POLYS[3]  = (new IrreduciblePoly(0b000000000000000000000000000010011l)); // x^4 + x + 1
        PREDEFINED_POLYS[4]  = (new IrreduciblePoly(0b000000000000000000000000000100101l)); // x^5 + x^2 + 1
        PREDEFINED_POLYS[5]  = (new IrreduciblePoly(0b000000000000000000000000001000011l)); // x^6 + x^1 + 1
        PREDEFINED_POLYS[6]  = (new IrreduciblePoly(0b000000000000000000000000010000011l)); // x^7 + x^1 + 1
        PREDEFINED_POLYS[7]  = (new IrreduciblePoly(0b000000000000000000000000100011101l)); // x^8 + x^4 + x^3 + x^2 + 1
        PREDEFINED_POLYS[8]  = (new IrreduciblePoly(0b000000000000000000000001000010001l)); // x^9 + x^4 + 1
        PREDEFINED_POLYS[9]  = (new IrreduciblePoly(0b000000000000000000000010000001001l)); // x^10 + x^3 + 1
        PREDEFINED_POLYS[10] = (new IrreduciblePoly(0b000000000000000000000100000000101l)); // x^11 + x^2 + 1
        PREDEFINED_POLYS[11] = (new IrreduciblePoly(0b000000000000000000001000001010011l)); // x^12 + x^6 + x^4 + x^1 + 1
        PREDEFINED_POLYS[12] = (new IrreduciblePoly(0b000000000000000000010000000011011l)); // x^13 + x^4 + x^3 + x^1 + 1
        PREDEFINED_POLYS[13] = (new IrreduciblePoly(0b000000000000000000100000101000011l)); // x^14 + x^8 + x^6 + x^1 + 1
        PREDEFINED_POLYS[14] = (new IrreduciblePoly(0b000000000000000001000000000000011l)); // x^15 + x^1 + 1
        PREDEFINED_POLYS[15] = (new IrreduciblePoly(0b000000000000000010000001111011101l)); // x^16 + x^9 + x^8 + x^7 + x^6 + x^4 + x^3 + x^2 + 1
        PREDEFINED_POLYS[16] = (new IrreduciblePoly(0b000000000000000100000000000001001l)); // x^17 + x^3 + 1
        PREDEFINED_POLYS[17] = (new IrreduciblePoly(0b000000000000001000000000010000001l)); // x^18 + x^7 + 1
        PREDEFINED_POLYS[18] = (new IrreduciblePoly(0b000000000000010000000000000100111l)); // x^19 + x^5 + x^2 + x^1 + 1
        PREDEFINED_POLYS[19] = (new IrreduciblePoly(0b000000000000100000000000000001001l)); // x^20 + x^3 + 1
        PREDEFINED_POLYS[20] = (new IrreduciblePoly(0b000000000001000000000000000000101l)); // x^21 + x^2 + 1
        PREDEFINED_POLYS[21] = (new IrreduciblePoly(0b000000000010000000000000000000011l)); // x^22 + x^1 + 1
        PREDEFINED_POLYS[22] = (new IrreduciblePoly(0b000000000100000000000000000100001l)); // x^23 + x^5 + 1
        PREDEFINED_POLYS[23] = (new IrreduciblePoly(0b000000001000000000000000010000111l)); // x^24 + x^7 + x^2 + x^1 + 1
        PREDEFINED_POLYS[24] = (new IrreduciblePoly(0b000000010000000000000000000001001l)); // x^25 + x^3 + 1
        PREDEFINED_POLYS[25] = (new IrreduciblePoly(0b000000100000000000000000001000111l)); // x^26 + x^6 + x^2 + x^1 + 1
        PREDEFINED_POLYS[26] = (new IrreduciblePoly(0b000001000000000000000000000100111l)); // x^27 + x^5 + x^2 + x^1 + 1
        PREDEFINED_POLYS[27] = (new IrreduciblePoly(0b000010000000000000000000000001001l)); // x^28 + x^3 + 1
        PREDEFINED_POLYS[28] = (new IrreduciblePoly(0b000100000000000000000000000000101l)); // x^29 + x^2 + 1
        PREDEFINED_POLYS[29] = (new IrreduciblePoly(0b001000000100000000000000000000111l)); // x^30 + x^23 + x^2 + x^1 + 1
        PREDEFINED_POLYS[30] = (new IrreduciblePoly(0b010000000000000000000000000001001l)); // x^31 + x^3 + 1
        PREDEFINED_POLYS[31] = (new IrreduciblePoly(0b100000000100000000000000000000111l)); // x^32 + x^22 + x^2 + x^1 + 1
        //TODO: add all irreductible polys up to 32
    }

    private IrreduciblePoly(long longValue) {
        super(new BigInteger(ByteBuffer.allocate(8).putLong(longValue).array()));
    }
    
    public IrreduciblePoly(BinaryExtensionFieldElement element){
        super(element);
    }
    /**
     * get the list of predefined irreducible polys of each degree up to 32
     * @return 
     */
    public static final IrreduciblePoly[] getPredefined(){
        return PREDEFINED_POLYS;
    }
}