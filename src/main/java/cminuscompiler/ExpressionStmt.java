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
public class ExpressionStmt extends Statement {
    Expression expr;
     public ExpressionStmt() {
       
    }
    public ExpressionStmt(Expression e) {
        expr = e;
    }
    @Override
    public void print (BufferedWriter w, int indent) throws IOException{
            
            if (expr != null){
            expr.print(w, indent);
            }
    }
}
