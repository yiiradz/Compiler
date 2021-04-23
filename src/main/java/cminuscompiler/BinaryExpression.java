/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token.TokenType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class BinaryExpression extends Expression {

    // op type
    Object operator;
    /*
    0 - none
    1 - ==
    2 - *
    3 - /
     */
    //takes two expressions
    Expression expr1;
    Expression expr2;

    public BinaryExpression(Object op, Expression e1, Expression e2) {
        operator = op;
        expr1 = e1;
        expr2 = e2;
    }

    public BinaryExpression(Object op, int i, Expression e1, Expression e2) {
        operator = op;
        expr1 = e1;
        expr2 = e2;
    }

    @Override
    public void print(BufferedWriter w) {

        try {
            if (operator.equals(0)) {

                w.write("");

            } else {
                w.write("\n");
                w.write("     ");
                w.write("     ");
                w.write("     ");
                w.write("     ");
                w.write(operator.toString());

            }
            w.write("\n");
            w.write("     ");
            w.write("     ");
            w.write("     ");
            w.write("     ");
            w.write("     ");
            expr1.print(w);
            if (expr2 != null) {
                w.write("\n");
                w.write("     ");
                w.write("     ");
                w.write("     ");
                w.write("     ");
                w.write("     ");
                expr2.print(w);
            }

        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void genLLCode(Function f) {

        //genCode the rhs
        expr1.genLLCode();
        // Make new Block
        BasicBlock b = new BasicBlock(f);
        //Make new operationn
        //conversion routine for my oper type
        Operation oper1 = new Operation(Operation.OperationType.ADD_I, f.getCurrBlock());

        //Append new Operation to Block
        b.appendOper(oper1);

        if (expr2 instanceof ExprType) {
            //??
            //get and set regnum as new destination oper
            int destReg = f.getNewRegNum();
            expr2.setRegNum(destReg);
        }

    }

}
