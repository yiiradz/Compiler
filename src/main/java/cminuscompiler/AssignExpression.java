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
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
public class AssignExpression extends Expression {

    //var on the left side
    // expr on right side
    //x = x + 1;
    boolean isBracket;
    Expression ld;
    Expression expr1;

    public AssignExpression(boolean b, Expression var, Expression e1) {
        isBracket = b;
        ld = var;
        expr1 = e1;
        
    }
    
    @Override
    public void print(BufferedWriter w) {

        try {
           
            w.write("=");
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");
              w.write("     ");

            if (isBracket == true) {
                w.write("[");
                ld.print(w);
                w.write("]");
            }
            else {
            ld.print(w);
            }
             w.write("\n");
             w.write("     ");
             w.write("     ");
             w.write("     ");
            expr1.print(w); 

        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void genLLCode (Function f) {
        
    
    }
}

