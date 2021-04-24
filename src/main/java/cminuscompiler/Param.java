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
public class Param {
    Object id;
    int type;
    public Param (Object i, int t){
        id = i;
        type = t;
    }
    public void print(BufferedWriter w) throws IOException{
        w.write((String) id);
    }
}
