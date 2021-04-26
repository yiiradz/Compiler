/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;
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
        if (expr != null) {
            expr.genLLCode(f);
            Operation jump = new Operation(Operation.OperationType.JMP, f.getCurrBlock());
            // oper to move expr result into return register
            jump.setNum(expr.getRegNum());
            // add jump to exit block
            f.getReturnBlock().appendOper(jump);
        }
        else {
            //jump to return block
            f.getReturnBlock();
        }

    }
}
