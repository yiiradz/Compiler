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
public class WhileStmt extends Statement{
    Expression myExpr;
    Statement stmt;
    public WhileStmt(){
        
    }
    
    public WhileStmt(Expression e, Statement s){
        myExpr = e;
        stmt = s;
    }
    
    @Override
    public void print (BufferedWriter w, int indent) throws IOException{
         w.write("WHILE STMT ");
            w.write("\n");
            indent += 1;
            
            if (myExpr != null){
             w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Expression");
            myExpr.print(w, indent + 1);
            }
            
            else if (stmt != null){
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Statements");
            stmt.print(w, indent + 1);
            }
    }
}
