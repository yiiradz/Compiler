/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yiradz
 */
public class Params {

    Param param;
    public ArrayList<Param> params = new ArrayList<>();


    public Params(Param p) {
        param = p;

    }

    public void add(Param p) {
        params.add(p);
    }

    public void print(BufferedWriter w) throws IOException {
        
        if (!params.isEmpty()){
         for (int i = 0; i < params.size(); i++) {
            params.get(i).print(w);
            w.write("\n");
             w.write("     ");
        }
        }
        else if (param != null){
            param.print(w);
        }

    }
}
