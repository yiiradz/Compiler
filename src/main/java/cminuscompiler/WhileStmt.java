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
import lowlevel.Operation;

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
        
        //make 2-3 blocks: then block, post block, else block
        BasicBlock thenBlock = new BasicBlock(f); //then block
        BasicBlock postWhileBlock = new BasicBlock(f); //post block
        
        //If BEQ
        if (stmt == null) {
            //genCode on Expr
            myExpr.genLLCode(f);

            //make branch to post or else?
            Operation branch = new Operation(Operation.OperationType.BEQ, postWhileBlock);
            postWhileBlock.appendOper(branch);

            //append then to current block pointer
            f.appendToCurrentBlock(thenBlock);

            //set current block pointer to then block
            f.setCurrBlock(thenBlock);

            //append post block
            f.appendToCurrentBlock(postWhileBlock); 
        }
        //if BNE
        else {
            // repeat the body of the code
        
            //genCode on Expr
            myExpr.genLLCode(f);

            //make branch to post or else?
            Operation branch = new Operation(Operation.OperationType.BEQ, postWhileBlock);
            postWhileBlock.appendOper(branch);

            //append then to current block pointer
            f.appendToCurrentBlock(thenBlock);

            //set current block pointer to then block
            f.setCurrBlock(thenBlock);

            //genCode on Stmt
            stmt.genLLCode(f);

            //append post block
            f.appendToCurrentBlock(postWhileBlock);

            //set current block pointer to then block
            f.setCurrBlock(thenBlock);

            //add jump to post
            Operation jump = new Operation(Operation.OperationType.JMP, postWhileBlock);
            postWhileBlock.appendOper(jump);
        
            //set current block pointer to post block
            f.setCurrBlock(postWhileBlock);
        }
       
    }
}
