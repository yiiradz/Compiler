/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    Expression expr;
    public ArrayList<Expression> args = new ArrayList<>();

    public CallExpression(Expression fun, Expression e1) {
        funct = fun;
        expr = e1;

    }

    public void add(Expression e) {
        args.add(e);
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
            for (int i = 0; i < args.size(); i++) {

                if (args.get(i) != null) {
                    w.write("\n");
                    w.write("               ");
                    args.get(i).print(w);
                }
            }
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
    public void genLLCode(Function f) {
        // local variable for array size
        int argNum = args.size();
        
        // Attribute for call Oper with param size
        Attribute numParams = new Attribute("PARAM_SIZE", Integer.toString(argNum));
        
        //Call Oper
        Operation call = new Operation(Operation.OperationType.CALL, f.getCurrBlock());
        
        // Add param attribute to call oper
        call.addAttribute(numParams);
        
        // Pass Oper
        Operation pass = new Operation(Operation.OperationType.PASS, f.getCurrBlock());

        // for each param
        for (int i = 0; i < argNum; i++) {
            //gencode
            args.get(i).genLLCode(f);

            // pass attribute to identify param
            pass.addAttribute(new Attribute("PARAM_NUM", Integer.toString(i)));

            // pass R1
            // call oper
            //new reg = retreg() <- move from special to generic register
            
        }

    }
}
