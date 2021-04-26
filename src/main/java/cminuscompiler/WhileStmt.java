/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
public class WhileStmt extends Statement{
    Expression myExpr;
    Statement stmt;
    public WhileStmt(){
        
    }
    
    public WhileStmt(Expression e, Statement s){
        myExpr = e;
        stmt = s;
    }
    
    @Override
    public void print (BufferedWriter w) throws IOException{

         w.write("WHILE (");
            
            
            if (myExpr != null){
             
            myExpr.print(w);
            }
            
            w.write("\n");
             w.write("     ");
              w.write("     ");
               w.write("     ");
            w.write(")");

            stmt.print(w);
            
    }
    
    @Override
    public void genLLCode(Function f){
        
        f.createBlock0();
        
        /*//make 2-3 blocks: then block, post block, else block
        BasicBlock thenBlock = new BasicBlock(f); //then block
        BasicBlock postBlock = new BasicBlock(f); //post block
        
        //begin with an if without an else block
        if (s2 == null) {
            //genCode on Expr
            myExpr.genLLCode(f);

            //make branch to post or else?
            Operation branch = new Operation(Operation.OperationType.BEQ, postBlock);
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
            postBlock.appendOper(jump);

            //append else to unconnected chain
            f.appendUnconnectedBlock(elseBlock);

            //set current block pointer to post block
            f.setCurrBlock(postBlock);
        }*/
       
    }
}
