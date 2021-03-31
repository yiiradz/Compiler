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
    public void print (BufferedWriter w, int indent) throws IOException{
         w.write("RETURN ");
            w.write("\n");
            indent += 1;
            
            if (expr != null){
             w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Expression");
            expr.print(w, indent + 1);
            }
    }
}
