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
public class CompoundStmt extends Statement{
    // compound stmt is arraylist of local decls and statements
    public ArrayList<LocalDecl> cmpd = new ArrayList<>();
}
