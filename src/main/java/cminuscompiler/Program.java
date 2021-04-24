/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import lowlevel.CodeItem;

/**
 *
 * @author yiradz
 */
public class Program {

    // decl-list
    public ArrayList<Declaration> DeclList = new ArrayList<>();
    // list of CodeItems
    public ArrayList<CodeItem> cIList = new ArrayList<>();

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

    public CodeItem genLLCode() {
        int i;
        // need to build a list of Code Items
        for (i = 0; i < DeclList.size(); i++) {
            CodeItem c = DeclList.get(i).genLLCode();
            cIList.add(i, c);
            
        }
            //head and tail pointer for ll to return
        return cIList.get(i); // TODO: This needs to return the whole list?
    }
}
