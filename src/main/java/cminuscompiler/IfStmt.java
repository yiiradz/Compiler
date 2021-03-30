/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;

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
    
    public void print(BufferedWriter w, int indent){
        
    }
    
}
