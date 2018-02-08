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
package field;

import com.trident.crypto.algo.ECDSA;
import com.trident.crypto.algo.ECDSAKey;
import com.trident.crypto.elliptic.arithmetics.EllipticCurveArithmetics;
import com.trident.crypto.elliptic.nist.SECP;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author trident
 */
public class ECDSATest {
    
    @Test
    public void testECDSA() throws NoSuchAlgorithmException{
        String message = "Hello world!";
        String fMessage = "Not Hello world!";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(message.getBytes(Charset.defaultCharset()));
        byte[] fHash = digest.digest(fMessage.getBytes(Charset.defaultCharset()));
        for(int j = 0;j<SECP.values().length;j++){
            
            ECDSA ecdsa = new ECDSA(EllipticCurveArithmetics.createFrom(SECP.values()[j]));
            ECDSAKey key = ecdsa.generateKeyPair();
            System.out.println(key);
            String signature = ecdsa.sign(hash, key.getKeySec());
            System.out.println("Signature: "+signature);
            Assert.assertTrue(ecdsa.verify(hash, key.getKeyPub(), signature));
            Assert.assertFalse(ecdsa.verify(fHash, key.getKeyPub(), signature));
        }
        
    }
}
