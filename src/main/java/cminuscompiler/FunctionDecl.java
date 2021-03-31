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
    public void print(BufferedWriter w, int indent) {
        try {
            w.write("Function " + name + " { ");
            w.write("\n");
            indent += 1;

            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }

            if (returnType.equals(0)) {

                w.write("Returns VOID");

            } else {
                w.write("Returns INT");
            }
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Params");
            params.print(w, indent + 1);
            
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Statements");
            cmpdStmt.print(w, indent + 1);

            // Print closing bracket
             w.write("\n");
            for (int j = 0; j < indent - 1; j++) {
                w.write("     ");
            }
            w.write(" }");
        } catch (IOException ex) {
            Logger.getLogger(FunctionDecl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
