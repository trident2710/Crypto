/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trident.crypto.algo.io;

import com.trident.crypto.algo.ECDSAKey;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author trident
 */
public class ECDSAKeyPairWriter extends Writer{
    
    private final Writer writer;
    
    public ECDSAKeyPairWriter(Writer writer){
        this.writer = writer;
    }

    public void write(ECDSAKey key) throws IOException{
        try(BufferedWriter w = new BufferedWriter(writer)){
            w.write(key.toString());
        }   
    }
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
    
}
