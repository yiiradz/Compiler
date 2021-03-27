/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author yiradz
 */
public abstract class Declaration {
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
}
