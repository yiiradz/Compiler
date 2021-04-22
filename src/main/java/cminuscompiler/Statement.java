/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
public abstract class Statement {
    public ArrayList<Statement> stmtList = new ArrayList<Statement>();
    public void print(BufferedWriter w) throws IOException{

        
    }
    
    public void genLLCode (Function f) {
        
    
    }
}
