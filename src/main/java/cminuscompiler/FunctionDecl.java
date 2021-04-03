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
public class FunctionDecl extends Declaration {

    // name + return type
    String name;
    Object returnType;
    Params params; // arraylist
    Statement cmpdStmt;

    public FunctionDecl(String n, Object type, Params p, Statement cs) {
        name = n;
        returnType = type;
        params = p;
        cmpdStmt = cs;

    }

    @Override
    public void print(BufferedWriter w) {
        try {
            w.write("Function " + name + " { ");
            w.write("\n");
            

             w.write("          ");
            if (returnType.equals(0)) {

                w.write("Returns VOID");

            } else {
                w.write("Returns INT");
            }
            w.write("\n");
             w.write("               ");
            w.write("( ");
            params.print(w);
            w.write(" )");
            w.write("{");
            cmpdStmt.print(w);

            // Print closing brackets
             w.write("\n");
             w.write("               ");
            w.write(" }");
            w.write("\n");
             w.write("     ");
            w.write(" }");
        } catch (IOException ex) {
            Logger.getLogger(FunctionDecl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
