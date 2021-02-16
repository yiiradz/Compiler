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
        ERROR_TOKEN,
        // I'm sure i'm forgetting some 
    }
    
    public enum StateType{
        START,
        INCOMMENT,
        INNUM,
        INID,
        INASSIGN,
        KEYWORD,
        DONE,
    }
 
    
    private TokenType tokenType;
    private Object tokenData;
    
   // somewhere we need ID, Keyword,
    
    public Token (TokenType type, Object data){
        tokenType = type;
        tokenData = data;
    }
    
    //Create helper methods to determine if the token character is an integer, a letter, a symbol, etc.
    
    //Keywords / Reserve words are from the C- file
    
    //Getter
    public TokenType getTokenType() {
        return tokenType;
    }
    
    //Setter
    public void setTokenType(TokenType tok) {
        this.tokenType = tok;
    }
}
