/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 */
public class NumExpression extends Expression {
// hold ints
    Object num;

    public NumExpression (Object n) {
        num = n;
    }
    
    
    @Override
    public void print(BufferedWriter w, int indent){
        
        try {

            
            w.write(num.toString());
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
