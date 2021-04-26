/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.Function;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class AssignExpression extends Expression {

    //var on the left side
    // expr on right side
    //x = x + 1;
    boolean isBracket;
    Expression ld;
    Expression expr1;

    public AssignExpression(boolean b, Expression var, Expression e1) {
        isBracket = b;
        ld = var;
        expr1 = e1;
        
    }
    
    @Override
    public void print(BufferedWriter w) {

        try {
           
            w.write("=");
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");
              w.write("     ");

            if (isBracket == true) {
                w.write("[");
                ld.print(w);
                w.write("]");
            }
            else {
            ld.print(w);
            }
             w.write("\n");
             w.write("     ");
             w.write("     ");
             w.write("     ");
            expr1.print(w); 

        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
     public void genLLCode (Function f) {
        // check for global var
        if (CMinusCompiler.globalHash.containsValue(ld)){
            //create store oper
            Operation storeOper = new Operation (Operation.OperationType.STORE_I, f.getCurrBlock());
            f.getCurrBlock().appendOper(storeOper);
        }
        
        // check for local
        else if (f.getTable().containsValue(ld)){
            //create assign oper
            Operation assignOper = new Operation (Operation.OperationType.ASSIGN, f.getCurrBlock());
            f.getCurrBlock().appendOper(assignOper);
        }
    
    }
}

