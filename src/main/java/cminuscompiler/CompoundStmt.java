/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public class CompoundStmt extends Statement{
    // compound stmt is arraylist of local decls and statements
    public ArrayList<LocalDecl> cmpd = new ArrayList<>();
    
    LocalDecl localDecl;
    Statement stmt;
    
    public CompoundStmt(LocalDecl ld, Statement s) {
        localDecl = ld;
        stmt = s;
    }
    
    public CompoundStmt(LocalDecl ld) {
        localDecl = ld;
        stmt = null;
    }
    
    public CompoundStmt(Statement s) {
        localDecl = null;
        stmt = s;
    }
}
