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
import lowlevel.Attribute;
import lowlevel.Function;
import lowlevel.Operation;

/**
 *
 * @author yiradz
 */
public class CallExpression extends Expression {

    // where the fundecl is called
    //funct(x);
    Expression funct;
    Expression expr; // should this be a param?

    public CallExpression(Expression fun, Expression e1) {
        funct = fun;
        expr = e1;

    }

    @Override
    public void print(BufferedWriter w) {
        try {
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");

            funct.print(w);
            w.write(" (");
            w.write("\n");
             w.write("     ");
              w.write("     ");
              w.write("     ");
            expr.print(w); 
            w.write("\n");
              w.write("     ");
              w.write("     ");
              w.write("     ");
            w.write(")");
        } catch (IOException ex) {
            Logger.getLogger(NumExpression.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
     public void genLLCode (Function f) {
         Attribute numParams = new Attribute("name", "value");
         Operation pass = new Operation (Operation.OperationType.PASS, f.getCurrBlock());
         Operation call = new Operation (Operation.OperationType.CALL, f.getCurrBlock());
         pass.addAttribute(new Attribute("PARAM_NUM", Integer.toString(/*argNum*/0)));
         
        // for each param
        //gencode
        //pass 
        // call oper
        //new reg = retreg() <- move from special to generic register
    
    }
}
