/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
// this is functionally a var decl | int x or int x [10]
public class LocalDecl extends Declaration{
    String name;
    Object size = 0;

    public LocalDecl(String n, Object s){
        name = n;
        size = s;
    }
    

    @Override
    public void print(BufferedWriter w) throws IOException {
          w.write("\n");
             w.write("     ");
    if (name != null){
       w.write("INT " + name);
        // if num is empty, print semicolon
        if (size.equals(0)){
             w.write(" ;");
        }
        //otherwise print [ num ]
        else {
             w.write("[" + size + "] ;");
        }
    }
    }
    
    @Override
     public CodeItem genLLCode(){
        Data d = new Data((int)size, name);
        return d;
    }
    
     // Within CmpdStmt
     public CodeItem genLLCode(Function f){
        Data d = new Data((int)size, name);
        return d;
    }
}
