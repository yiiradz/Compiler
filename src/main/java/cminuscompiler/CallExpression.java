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
public class CallExpression extends Expression {

    // where the fundecl is called
    //funct(x);
    Expression funct;
    Expression expr;

    public CallExpression(Expression fun, Expression e1) {
        funct = fun;
        expr = e1;

    }

    @Override
    public void print(BufferedWriter w, int indent) {
        try {
              w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            funct.print(w, 0);
            w.write(" (");
            w.write("\n");
             for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            expr.print(w, indent + 1); 
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write(")");
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
