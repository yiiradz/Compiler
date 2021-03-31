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
public class CompoundStmt extends Statement{
    
    Declaration localDecl;
    Statement stmt;
     public CompoundStmt(){
         
     }
    public CompoundStmt(Declaration ld, Statement s) {
        localDecl = ld;
        stmt = s;
    }
    
    public CompoundStmt(Declaration ld) {
        localDecl = ld;
        stmt = null;
    }
    
    public CompoundStmt(Statement s) {
        localDecl = null;
        stmt = s;
    }
    
    @Override
    public void print(BufferedWriter w, int indent) throws IOException{
        w.write("\n");
        for (int j = 0; j < indent; j++) {
            w.write("     ");
        }
        if (localDecl == null){
            stmt.print(w, indent);
        }
        else if (stmt == null){
            localDecl.print(w, indent);
        }
        else {
             stmt.print(w, indent);
             localDecl.print(w, indent);
        }
        
        
    }
}
