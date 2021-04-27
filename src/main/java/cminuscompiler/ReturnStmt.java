/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class ReturnStmt extends Statement {

    Expression expr;

    public ReturnStmt(Expression e) {
        expr = e;
    }

    @Override
    public void print(BufferedWriter w) throws IOException {
        w.write("RETURN ");
        w.write("\n");
        w.write("     ");
        w.write("     ");
        w.write("     ");

        if (expr != null) {

            expr.print(w);
        }
    }

    @Override
    public void genLLCode(Function f) {
        int returnBlockNum = 0;
        if (expr != null) {
            expr.genLLCode(f);
            // new operand with num of return block
            returnBlockNum = f.genReturnBlock().getBlockNum();

        }

        Operation jump = new Operation(Operation.OperationType.JMP, f.getCurrBlock());
        // oper to move expr result into return register
        jump.setNum(expr.getRegNum());
        //set src oper = return block number
        Operand src = new Operand(Operand.OperandType.INTEGER, returnBlockNum);
        jump.setSrcOperand(0, src);
        
        // add jump to exit block
        f.getCurrBlock().appendOper(jump);
    }
}
