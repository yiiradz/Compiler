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
public class AssignExpression extends Expression {

    //var on the left side
    // expr on right side
    //x = x + 1;
    Declaration ld;
    Expression expr1;

    public AssignExpression(Declaration locdec, Expression e1) {
        ld = locdec;
        expr1 = e1;
        
    }
    
    @Override
    public void print(BufferedWriter w, int indent) {

        try {
            w.write("(");
            w.write("\n");
            indent += 1;

            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("\n");
            indent += 1;
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
           w.write(")");

        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
