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

    // decl-list
    public ArrayList<Declaration> DeclList = new ArrayList<>();

    public void printTree(BufferedWriter w) throws IOException {
        // Print "Program { 
        w.write("Program { ");
        // Start recursive indented print
        for (int i = 0; i < DeclList.size(); i++) {
            w.write("\n");
            w.write("     ");
           DeclList.get(i).print(w);
        }

        // Print closing bracket
        w.write("\n }");
    }

}
