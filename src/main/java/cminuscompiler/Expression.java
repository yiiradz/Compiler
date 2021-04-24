/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import lowlevel.Function;

/**
 *
 * @author yiradz
 */
public abstract class Expression {  
    int regNum;
    public void print(BufferedWriter w){
        
    }
    
    public void genLLCode (Function f) {
        
    
    }
    
    public void setRegNum(int i){
        regNum = i;
    }
    
    public int getRegNum(){
        return regNum;
    }
  
}
