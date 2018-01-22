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
package com.trident.crypto.elliptic.curve;

import com.trident.crypto.elliptic.point.EllipticCurvePoint;
import com.trident.crypto.finitefield.element.FiniteFieldElement;
import com.trident.crypto.finitefield.field.FiniteField;
import java.math.BigInteger;

/**
 *
 * @author trident
 * @param <T>
 * @param <K>
 * @param <P>
 */
public interface EllipticCurveFactory<T extends FiniteField<K>, K extends FiniteFieldElement, P extends EllipticCurvePoint<K>>{
    EllipticCurve<T, K, P> create(T field, K a, K b, P G, BigInteger n, BigInteger h);
}
