/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author yiradz
 * @author mpoh98
 */
public class Token {
    public enum TokenType {
        //Keywords
        IF_TOKEN,
        ELSE_TOKEN,
        INT_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        RETURN_TOKEN,
        //other tokens
        EOF_TOKEN,
        ERROR_TOKEN,
        ID_TOKEN,
        NUM_TOKEN,
        VARCALL_TOKEN,
        //2 character symbol tokens
        LESSTHANEQUAL_TOKEN,
        GREATERTHANEQUAL_TOKEN,
        EQUALEQUAL_TOKEN,
        PLUSEQUAL_TOKEN,
        PLUSPLUS_TOKEN,
        MINUSEQUAL_TOKEN,
        MINUSMINUS_TOKEN,
        NOTEQUAL_TOKEN,
        //single character tokens
        LESSTHAN_TOKEN,
        GREATERTHAN_TOKEN,
        EQUAL_TOKEN,
        PLUS_TOKEN,
        MINUS_TOKEN,
        BRACEOPEN_TOKEN,
        BRACECLOSE_TOKEN,
        PARANOPEN_TOKEN,
        PARANCLOSE_TOKEN,
        MULTIPLY_TOKEN,
        DIVIDE_TOKEN,
        SEMICOLON_TOKEN,
        PERIOD_TOKEN,
        COMMA_TOKEN,
    }
    
    public enum StateType {
        START,
        ISSYMBOL,
        ISPLUS,
        ISMINUS,
        ISDOUBLE,
        ISDIVIDE,
        ISCOMMENT,
        ISFINISHCOMMENT,
        ISNUM,
        ISID,
        ISKEYWORD,
        DONE,
    }

    private TokenType tokenType;
    private Object tokenData;
    private StringBuffer sb = new StringBuffer();
    
   // somewhere we need ID, Keyword,
    public Token(){
        
    }
    
    public Token (TokenType type, Object data){
        tokenType = type;
        tokenData = data;
    }
    
    //Getter
    public TokenType getTokenType() {
        return tokenType;
    }
    
    //Setter
    public void setTokenType(TokenType tok) {
        this.tokenType = tok;
    }
    
    //Getter
    public Object getTokenData() {
        return tokenData;
    }
    
    //Setter
    public void setTokenData(Object data) {
        this.tokenData = data;
    }
}
