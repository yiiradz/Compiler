/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class IfStmt extends Statement {

    // children + instance variables? abstract pointers to abstract classes
    Expression myExpr;
    Statement s1;
    Statement s2;

    public IfStmt() {

    }

    public IfStmt(Expression e, Statement st1, Statement st2) {
        myExpr = e;
        s1 = st1;
        s2 = st2;
    }

    @Override
    public void print(BufferedWriter w) throws IOException {
        w.write("IF (");
       

        myExpr.print(w);
        w.write("\n");
        w.write("     ");
         w.write("     ");
          w.write("     ");
        w.write(")");

        w.write("\n");
         w.write("     ");
          w.write("     ");
           w.write("     ");
        w.write("THEN ");
        s1.print(w);

        if (s2 != null) {
            w.write("\n");
             w.write("     ");
              w.write("     ");
               w.write("     ");
            w.write("ELSE ");
            s2.print(w);
        }

    }
    
    @Override
    public void genLLCode(Function f){
       
        f.createBlock0();
        
        //make 2-3 blocks: then block, post block, else block
        BasicBlock thenBlock = new BasicBlock(f); //then block
        BasicBlock postBlock = new BasicBlock(f); //post block
        
        //begin with an if without an else block
        if (s2 == null) {
            //genCode on Expr
            myExpr.genLLCode(f);

            //make branch to post or else?
            Operation branch = new Operation(Operation.OperationType.BEQ, postBlock);
            Operand src = new Operand(Operand.OperandType.REGISTER);
            src.setValue(myExpr.getRegNum());
            branch.setSrcOperand(0, src);
            
            Operand dest = new Operand(Operand.OperandType.REGISTER);
            dest.setValue(f.getNewRegNum());
            branch.setDestOperand(0, dest);
            postBlock.appendOper(branch);

            //append then to current block pointer
            f.appendToCurrentBlock(thenBlock);

            //set current block pointer to then block
            f.setCurrBlock(thenBlock);

            //genCode on Then
            s1.genLLCode(f);
            
            //append post block
            f.appendToCurrentBlock(postBlock);
        }
        //if the else statement exists:
        else {
            BasicBlock elseBlock = new BasicBlock(f); //else block
        
            //genCode on Expr
            myExpr.genLLCode(f);

            //make branch to post or else?
            Operation branch = new Operation(Operation.OperationType.BEQ, elseBlock);
            Operand src = new Operand(Operand.OperandType.REGISTER);
            src.setValue(myExpr.getRegNum());
            branch.setSrcOperand(0, src);
            
            Operand dest = new Operand(Operand.OperandType.REGISTER);
            dest.setValue(f.getNewRegNum());
            branch.setDestOperand(0, dest);
            elseBlock.appendOper(branch);

            //append then to current block pointer
            f.appendToCurrentBlock(thenBlock);

            //set current block pointer to then block
            f.setCurrBlock(thenBlock);

            //genCode on Then
            s1.genLLCode(f);

            //append post block
            f.appendToCurrentBlock(postBlock);

            //set current block pointer to else block
            f.setCurrBlock(elseBlock);

            //genCode on Else
            s2.genLLCode(f);

            //add jump to post
            Operation jump = new Operation(Operation.OperationType.JMP, postBlock);
            
            //set src + dest ops
            Operand srcJMP = new Operand(Operand.OperandType.REGISTER);
            srcJMP.setValue(myExpr.getRegNum());
            jump.setSrcOperand(0, srcJMP);
            
            Operand destJMP = new Operand(Operand.OperandType.REGISTER);
            destJMP.setValue(postBlock.getBlockNum());
            jump.setDestOperand(0, destJMP);
            postBlock.appendOper(jump);

            //append else to unconnected chain
            f.appendUnconnectedBlock(elseBlock);

            //set current block pointer to post block
            f.setCurrBlock(postBlock);
        }
    }
}
