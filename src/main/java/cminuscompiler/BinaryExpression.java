/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token.TokenType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 */
public class BinaryExpression extends Expression {

    // op type
    Object operator;
    /*
    0 - none
    1 - =
    2 - *
    3 - /
     */
    //takes two expressions
    Expression expr1;
    Expression expr2;

    public BinaryExpression(Object op, Expression e1, Expression e2) {
        operator = op;
        expr1 = e1;
        expr2 = e2;
    }

    @Override
    public void print(BufferedWriter w, int indent) {

        try {
            if (operator.equals(0)) {

                w.write("");

            } else {
                w.write("\n");
                for (int j = 0; j < indent; j++) {
                    w.write("     ");
                }
                w.write(operator.toString());
            }

            w.write("\n");
            expr1.print(w, indent + 1);
            w.write("\n");
            expr2.print(w, indent + 1);

        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
