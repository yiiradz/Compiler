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
public abstract class Declaration {
    FunctionDecl fd;
    LocalDecl ld;
    private Object ID;
    private Object NUM;
      //Getter
    public Object getID() {
        return ID;
    }
    
    //Setter
    public void setID(Object i) {
        this.ID = i;
    }
    
    //Getter
    public Object getNUM() {
        return NUM;
    }
    
    //Setter
    public void setNUM(Object n) {
        this.NUM = n;
    }

    public void printDecl(BufferedWriter w, FunctionDecl f) throws IOException{
        w.write("int" + getID());
        fd.printFunDecl(w, f);
        
    }
}
