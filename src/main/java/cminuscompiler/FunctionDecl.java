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
public class FunctionDecl extends Declaration {
    // name + return type
    Params params;
    Statement cmpdStmt;
    
    public FunctionDecl(Params p, Statement cs){
        params = p;
        cmpdStmt = cs;
             
    }
    
    public void printFunDecl(BufferedWriter w, FunctionDecl fd) throws IOException{
        // print ( params ) cmpdStmt
        w.write("(" + fd.params + ")" + fd.cmpdStmt);
        
    }
    
}
