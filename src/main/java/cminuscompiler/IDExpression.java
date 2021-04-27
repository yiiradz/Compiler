/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token;
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
public class IDExpression extends Expression {
    // hold IDs
    Object id;

    public IDExpression (Object i) {
        id = i;
    }
    
    
    @Override
    public void print(BufferedWriter w){
        
        try {
            
            w.write(id.toString());
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
     public void genLLCode(Function f) {
         // is it a local var? check in the table
         if (f.getTable().containsKey(id)){
             //If inside table
             // Set RegNum with the register within the table at that id
             this.setRegNum((int)f.getTable().get((id)));
         }
         else {
             //search global table
             if(CMinusCompiler.globalHash.containsKey(id)){
             //if its in global
             //make load oper. 
             Operation load = new Operation(Operation.OperationType.LOAD_I, f.getCurrBlock());
             // annotate node with destination register of load oper
             this.setRegNum((int)load.getDestOperand((int)id).getValue());
             
             }
         }
        
     }
}
