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
    public void print(BufferedWriter w) {
        try {
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");

            funct.print(w);
            w.write(" (");
            w.write("\n");
             w.write("     ");
              w.write("     ");
              w.write("     ");
            expr.print(w); 
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");
            w.write(")");
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
