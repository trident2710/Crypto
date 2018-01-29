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
package com.trident.crypto.elliptic.nist;

import java.math.BigInteger;

/**
 *
 * @author trident
 */
public enum NistEC {
    SECP112R1(
            "DB7C2ABF62E35E668076BEAD208B",
            "DB7C2ABF62E35E668076BEAD2088",
            "659EF8BA043916EEDE8911702B22",
            "00F50B028E4D696E676875615175290472783FB1",
            "09487239995A5EE76B55F9C2F098",
            "A89CE5AF8724C0A23E0E0FF77500",
            "DB7C2ABF62E35E7628DFAC6561C5",
            "01"),
    SECP112R2(
            "DB7C2ABF62E35E668076BEAD208B",
            "6127C24C05F38A0AAAF65C0EF02C",
            "51DEF1815DB5ED74FCC34C85D709",
            "002757A1114D696E6768756151755316C05E0BD4",
            "4BA30AB5E892B4E1649DD0928643",
            "ADCD46F5882E3747DEF36E956E97",
            "36DF0AAFD8B8D7597CA10520D04B",
            "04"
            ),
    SECT113R1(
            "x^113 x^9 1",
            "003088250CA6E7C7FE649CE85820F7",
            "00E8BEE4D3E2260744188BE0E9C723",
            "10E723AB14D696E6768756151756FEBF8FCB49A9",
            "009D73616F35F4AB1407D73562C10F",
            "00A52830277958EE84D1315ED31886",
            "0100000000000000D9CCEC8A39E56F",
            "02"
            );
    
    private final String PRIME_FIELD_PREFIX = "SECP";
    private final String BINARY_EXTENSION_FIELD_PREFIX = "SECT";
    
    private final BigInteger modulo;
    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger S;
    private final BigInteger Gx;
    private final BigInteger Gy;
    private final BigInteger n;
    private final BigInteger h;

    private NistEC(String modulo, String a, String b, String S, String Gx, String Gy, String n, String h) {
        this.modulo = new BigInteger(modulo, 16);
        this.a = new BigInteger(a, 16);
        this.b = new BigInteger(b, 16);
        this.S = new BigInteger(S, 16);
        this.Gx = new BigInteger(Gx, 16);
        this.Gy = new BigInteger(Gy, 16);
        this.n = new BigInteger(n, 16);
        this.h = new BigInteger(h, 16);
    }

    public boolean getType() {
        return this.toString().startsWith(PRIME_FIELD_PREFIX);
    }
    
    public BigInteger getModulo() {
        return modulo;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getS() {
        return S;
    }

    public BigInteger getGx() {
        return Gx;
    }

    public BigInteger getGy() {
        return Gy;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getH() {
        return h;
    } 
}
