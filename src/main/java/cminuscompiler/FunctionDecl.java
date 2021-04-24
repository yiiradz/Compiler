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
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.FuncParam;
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
public class FunctionDecl extends Declaration {

    // name + return type
    String name;
    Object returnType;
    Params params; // arraylist
    Statement cmpdStmt;

    public FunctionDecl(String n, Object type, Params p, Statement cs) {
        name = n;
        returnType = type;
        params = p;
        cmpdStmt = cs;

    }

    @Override
    public void print(BufferedWriter w) {
        try {
            w.write("Function " + name + " { ");
            w.write("\n");
            

             w.write("          ");
            if (returnType.equals(0)) {

                w.write("Returns VOID");

            } else {
                w.write("Returns INT");
            }
            w.write("\n");
             w.write("               ");
            w.write("( ");
            params.print(w);
            w.write(" )");
            w.write("{");
            cmpdStmt.print(w);

            // Print closing brackets
             w.write("\n");
             w.write("               ");
            w.write(" }");
            w.write("\n");
             w.write("     ");
            w.write(" }");
        } catch (IOException ex) {
            Logger.getLogger(FunctionDecl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public CodeItem genLLCode(){
        Function func = new Function((int)returnType, name);
        
         // Appending params
        for (int i = 0; i < params.params.size(); i++){
            //create local var for Param
            Param currentParam = params.params.get(i);
            
            if(func.getTable().containsValue(currentParam.id)){
                //error
                
            }
            else {
                // Put param in table and call a new register num for it
                func.getTable().put(func.getNewRegNum(),currentParam.id);
            }
            
            //Fill first param with param info from params.(i)
            FuncParam firstParam = new FuncParam(currentParam.type, (String)currentParam.id);
            
            FuncParam tailParam = new FuncParam();
            
            //tail param
            if (i == 0 ){
                firstParam = tailParam;
               // = new func
            }
            else {
               tailParam.setNextParam(firstParam);
               firstParam = tailParam;
            }
            //Set param as head of LL 
            func.setFirstParam(firstParam);
        }
        
        // Set Block
        func.createBlock0();
        //Create new Block
        BasicBlock b2 = new BasicBlock(func);
        
        func.appendBlock(b2);
        
        // Set current Block pointer
        func.setCurrBlock(b2);
        
        // Call genCode on compound stmt
        cmpdStmt.genLLCode(func);
        
        func.appendBlock(func.getReturnBlock());
        
        // append the unconnect chain
        func.appendUnconnectedBlock(b2);
        
        
        // return functionn
        return func;
    }

}
