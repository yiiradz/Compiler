/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.compiler;

/**
 *
 * @author yiradz
 */
public class Token {
    public enum TokenType{
        INT_TOKEN,
        DOUBLE_TOKEN,
        IF_TOKEN,
        ELSE_TOKEN,
        WHILE_TOKEN,
        STRING_TOKEN,
        EOF_TOKEN,
        LESSTHANEQUAL_TOKEN,
        LESSTHAN_TOKEN,
        GREATERTHANEQUAL_TOKEN,
        GREATERTHAN_TOKEN,
        ASSIGN_TOKEN,
        // I'm sure i'm forgetting some 
    }
    
    private TokenType tokenType;
    private Object tokenData;
    
   // somewhere we need ID, Keyword,
    
    public Token (TokenType type, Object data){
        tokenType = type;
        tokenData = data;
    }
    
    //there is more to go here
}
