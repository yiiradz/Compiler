/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author yiradz
 */
// this is functionally a var decl | int x or int x [10]
public class LocalDecl extends Declaration{
    String name;
    Object size = 0;
    public LocalDecl(){
        
    }
    public LocalDecl(String n, Object s){
        name = n;
        size = s;
    }
    

    @Override
    public void print(BufferedWriter w, int indent) throws IOException {
    
       w.write("VarDecl INT " + name);
        // if num is empty, print semicolon
        if (size.equals(0)){
             w.write(";");
        }
        //otherwise print [ num ]
        else {
             w.write("[" + size + "] ;");
        }
    }
    
}
