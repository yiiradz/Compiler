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
public class ReturnStmt extends Statement {
    Expression expr;
    
    public ReturnStmt(Expression e) {
        expr = e;
    }
    
    @Override
    public void print (BufferedWriter w) throws IOException{
         w.write("RETURN ");
            w.write("\n");
            
            
            if (expr != null){
            
            expr.print(w);
            }
    }
}
