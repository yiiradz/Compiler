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
        BasicBlock elseBlock = new BasicBlock(f); //else block
        
        //begin with an if without an else block
        
        
        //genCode on Expr
        myExpr.genLLCode(f);
        
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
        
        //add jump to post?
        
        
        //append else to unconnected chain
        f.appendUnconnectedBlock(elseBlock);
        
        //set current block pointer to post block
        f.setCurrBlock(postBlock);
    }
}
