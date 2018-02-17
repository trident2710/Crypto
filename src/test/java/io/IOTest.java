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
package io;

import com.trident.crypto.algo.ECDSA;
import com.trident.crypto.algo.ECDSAKey;
import com.trident.crypto.algo.io.ECDSAKeyReader;
import com.trident.crypto.algo.io.ECDSAKeyWriter;
import com.trident.crypto.elliptic.EllipticCurveOperator;
import com.trident.crypto.elliptic.arithmetics.EllipticCurveArithmetics;
import com.trident.crypto.elliptic.nist.SECP;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author trident
 */
public class IOTest {
    @Test
    public void testKeyPairIO(){
        int i = 0;
        for(SECP secp: SECP.values()){
            String name = "test"+i++;
            File f = new File(name);
            
            EllipticCurveOperator operator = EllipticCurveArithmetics.createFrom(secp);
            ECDSA ecdsa = new ECDSA(operator);
            ECDSAKey key = ecdsa.generateKeyPair();
            ECDSAKey key2 = null;
            
            try(ECDSAKeyWriter writer = new ECDSAKeyWriter(new FileWriter(f))){
                writer.write(key);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
            }
            
            try(ECDSAKeyReader reader = new ECDSAKeyReader(new FileReader(f))){
                key2 = reader.readECDSAKey();
                reader.close();
            } catch (IOException ex) {
            }
                  
            Assert.assertEquals(key, key2);
            f.delete();
        }    
    }
}
