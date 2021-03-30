/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public class Params {
    Param param;
     public ArrayList<Param> params = new ArrayList<>();
     
    public Params(){
        
    }
     public Params(Param p){
        param = p;
    }
      public Params(Param p, ArrayList<Param> alp){
          param = p;
          for (int i = 0; i < alp.size(); i++){
             params.add(alp.get(i));
          }       
        
    }
      
     public void add (Param p){
         params.add(p);
     }
     
     public void print(BufferedWriter w){
        
     }
}
