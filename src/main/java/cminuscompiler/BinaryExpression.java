/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token.TokenType;

/**
 *
 * @author yiradz
 */
public class BinaryExpression extends Expression{
    //takes two expressions
    Expression expr1;
    Expression expr2;
     public static BinaryExpression createBinoExpr (TokenType opType, Expression e1, Expression e2){
        
    }
}
