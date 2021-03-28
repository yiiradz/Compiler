/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author yiradz
 */
public class ReturnStmt extends Statement {
    Expression expr;
    public ReturnStmt(){
        
    }
    public ReturnStmt(Expression e) {
        expr = e;
    }
}
