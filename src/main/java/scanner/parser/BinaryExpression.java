/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner.parser;

/**
 *
 * @author yiradz
 */
public class BinaryExpression extends Expression{
    //takes two expressions and an operand 
    //enums?
    public BinaryExpression (Expression e1, Expression e2, int opType){
        BinaryExpression be = new BinaryExpression(e1,e2,opType);
    }
}
