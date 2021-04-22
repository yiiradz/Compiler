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
    
    public CodeItem genLLCode(){
        Function func = new Function((int)returnType, name);
        // Set Block
        func.createBlock0();
        
        cmpdStmt.genLLCode(func);
        
        // Appending params
        for (int i = 0; i < params.params.size(); i++){
            if(func.getTable().containsValue(params.params.indexOf(i))){
                //error
            }
            else {
                func.getTable().put(i,params.params.indexOf(i));
            }
            FuncParam firstParam = new FuncParam();
            //need to fill first param with param info from params.(i)
            
            //Set param as head of LL 
            func.setFirstParam(firstParam);
        }
        
        func.appendBlock(newBlock);
        return func;
    }

}
