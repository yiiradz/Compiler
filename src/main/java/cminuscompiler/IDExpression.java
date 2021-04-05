/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 */
public class IDExpression extends Expression {
    // hold IDs
    Object id;

    public IDExpression (Object i) {
        id = i;
    }
    
    
    @Override
    public void print(BufferedWriter w){
        
        try {
            
           
             w.write("     ");
            w.write(id.toString());
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
