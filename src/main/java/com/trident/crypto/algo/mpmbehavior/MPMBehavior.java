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
package com.trident.crypto.algo.mpmbehavior;

import com.trident.crypto.elliptic.EllipticCurvePoint;
import java.math.BigInteger;

/**
 * defines the interface for the algorithm of multiple point multiplication
 * i.e. calculation of kP + lQ which can be optimized
 * @author trident
 */
public interface MPMBehavior {
    
    /**
     * calculate the kP + lQ i.e. perform the multiple point multiplication
     * @param k
     * @param l
     * @param P
     * @param Q
     * @return the result of multiple point multiplication
     */
    EllipticCurvePoint mpm(BigInteger k, BigInteger l, EllipticCurvePoint P,EllipticCurvePoint Q);
}
