/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public class FunctionDecl extends Declaration {
    // name + return type
    public ArrayList<Param> parameters = new ArrayList<>();
    public FunctionDecl(){
        
    }
    public FunctionDecl(Param p, Statement cs){
        
    }
    
}
