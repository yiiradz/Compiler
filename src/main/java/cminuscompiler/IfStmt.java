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
public class IfStmt extends Statement {
    // children + instance variables? abstract pointers to abstract classes
    Expression myExpr;
    Statement s1;
    Statement s2;
    
    public IfStmt(){
        
    }
    public IfStmt (Expression e, Statement st1, Statement st2){
        myExpr = e;
        s1 = st1;
        s2 = st2;
    }
    
    @Override
    public void print(BufferedWriter w, int indent) throws IOException{
         w.write("IF (");
            w.write("\n");
            indent += 1;
            
            if (myExpr != null){
            myExpr.print(w, indent);
            }
            
            else if (s1 != null || s2 != null){
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write("Statements");
            s1.print(w, indent + 1);
            s2.print(w, indent + 1);
            }
            w.write("\n");
            for (int j = 0; j < indent; j++) {
                w.write("     ");
            }
            w.write(")");
    }
    
}
