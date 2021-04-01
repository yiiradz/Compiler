/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author yiradz
 */
public class IfStmt extends Statement {

    // children + instance variables? abstract pointers to abstract classes
    Expression myExpr;
    Statement s1;
    Statement s2;

    public IfStmt() {

    }

    public IfStmt(Expression e, Statement st1, Statement st2) {
        myExpr = e;
        s1 = st1;
        s2 = st2;
    }

    @Override
    public void print(BufferedWriter w, int indent) throws IOException {
        w.write("IF (");
        indent += 1;

        myExpr.print(w, indent);
        w.write("\n");
        for (int j = 0; j < indent; j++) {
            w.write("     ");
        }
        w.write(")");

        w.write("\n");
        for (int j = 0; j < indent; j++) {
            w.write("     ");
        }
        w.write("THEN ");
        s1.print(w, indent + 1);

        if (s2 != null) {
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }

            w.write("ELSE ");
            s2.print(w, indent + 1);
        }

    }
}
