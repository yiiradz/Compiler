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
public class CompoundStmt extends Statement {

    Declaration localDecl;
    public ArrayList<Declaration> ldList = new ArrayList<>();
    Statement stmt;
    public ArrayList<Statement> stList = new ArrayList<>();

    public CompoundStmt(Declaration ld, Statement s) {
        localDecl = ld;
        stmt = s;
    }

    public void add(Declaration d) {
        ldList.add(d);
    }

    public void add(Statement s) {
        stList.add(s);
    }

    @Override
    public void print(BufferedWriter w) throws IOException {
        w.write("\n");
        for (int i = 0; i < ldList.size(); i++) {
            
            if (ldList.get(i) != null){
                w.write("\n");
                w.write("               ");
            ldList.get(i).print(w);
            
        }
            }
        
        
        for (int i = 0; i < stList.size(); i++) {
            
            if (stList.get(i) != null){
                w.write("\n");
                w.write("               ");
            stList.get(i).print(w);
           
        }
            }
        }
    

}
