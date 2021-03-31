/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public class Program {

    int indent = 0;
    // decl-list
    public ArrayList<Declaration> DeclList = new ArrayList<>();

    public void printTree(BufferedWriter w) throws IOException {
        // Print "Program { 
        w.write("Program { ");
        indent = 1;
        // Start recursive indented print
        for (int i = 0; i < DeclList.size(); i++) {
            w.write("\n");
           for (int j = 0; j < indent; j++) {             
                w.write("     ");
            }
           DeclList.get(i).print(w, indent);
        }

        // Print closing bracket
        w.write("\n }");
    }

}
