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
public class NumExpression extends Expression {
    
    SimpleExpression splExpr;
    
    public NumExpression() {
        
    }
    
    public NumExpression(SimpleExpression se) {
        splExpr = se;
    }

}
