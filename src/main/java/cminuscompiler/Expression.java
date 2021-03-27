/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public abstract class Expression {
    
    //ArrayList of Expressions
    public ArrayList<Expression> exprList = new ArrayList<Expression>();
    
    public Expression createNumExpr(Token t){
        Expression n = null;
        return n;
    }
    
    public Expression createIDExpr(Token t){
       Expression n = null;
        return n; 
    }
  
}
