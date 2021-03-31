/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public abstract class Expression {
    
    public Expression createNumExpr(Token t){
        Expression n = null;
        return n;
    }
    
    public Expression createIDExpr(Token t){
       Expression n = null;
        return n; 
    }
    
    public Expression createBinoExpr (Token.TokenType opType, Expression e1, Expression e2){
        Expression n = null;
        return n;
    }
    public void print(BufferedWriter w, int indent){
        
    }
  
}
