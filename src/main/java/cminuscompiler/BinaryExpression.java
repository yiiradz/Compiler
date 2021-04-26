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
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class BinaryExpression extends Expression {

    Operation.OperationType op;
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

    @Override
    public void genLLCode(Function f) {

        //genCode the rhs
        expr1.genLLCode(f);
        // genCode the lhs
        expr2.genLLCode(f);

        if (null != operator.toString()) //convert binary oper type into oper type
        {
            switch (operator.toString()) {
                case "+" ->
                    op = Operation.OperationType.ADD_I;
                case "-" ->
                    op = Operation.OperationType.SUB_I;
                case "*" ->
                    op = Operation.OperationType.MUL_I;
                case "/" ->
                    op = Operation.OperationType.DIV_I;
                case "<" ->
                    op = Operation.OperationType.LT;
                case "<=" ->
                    op = Operation.OperationType.LTE;
                case ">" ->
                    op = Operation.OperationType.GT;
                case ">=" ->
                    op = Operation.OperationType.GTE;
                case "==" ->
                    op = Operation.OperationType.EQUAL;
                case "!=" ->
                    op = Operation.OperationType.NOT_EQUAL;
                default -> {
                }
            }
        }

        //Make new operation + sets pointer
        Operation oper1 = new Operation(op, f.getCurrBlock());

        //Append new Operation to Block
        f.getCurrBlock().appendOper(oper1);

        // if lhs is num, 
        if (expr2 instanceof NumExpression) {
            //make number operand
            Operand numOper = new Operand(Operand.OperandType.INTEGER);
            numOper.setValue(expr2.getRegNum());
            //put overands into the operation 
            oper1.setSrcOperand(regNum, numOper);
        } //else if register, make register operand with that reg num in it
        else {
            Operand regOper = new Operand(Operand.OperandType.REGISTER);
            regOper.setValue(expr2.getRegNum());
            //put overands into the operation 
            oper1.setSrcOperand(regNum, regOper);
        }

        // if rhs is num, 
        if (expr1 instanceof NumExpression) {
            //make number operand
            Operand numOper = new Operand(Operand.OperandType.INTEGER);
            numOper.setValue(expr1.getRegNum());
            //put overands into the operation 
            oper1.setSrcOperand(regNum, numOper);
        } //else if register, make register operand with that reg num in it
        else {
            Operand regOper = new Operand(Operand.OperandType.REGISTER);
            regOper.setValue(expr1.getRegNum());
            //put overands into the operation 
            oper1.setSrcOperand(regNum, regOper);

        }

        
        //Setting Destination Operand
        Operand destOper = new Operand(Operand.OperandType.REGISTER);
        destOper.setValue(f.getNewRegNum());

        oper1.setDestOperand(0, destOper);

        // annotate the node..
        int destReg = f.getNewRegNum();
        expr1.setRegNum(destReg);

    }

}
