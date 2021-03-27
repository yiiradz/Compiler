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
public class ExpressionStmt extends Statement {
    Expression expr;
    
    public ExpressionStmt(Expression e) {
        expr = e;
    }
}
