/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.compiler;

import java.io.BufferedReader;

/**
 *
 * @author yiradz
 */
public abstract class CMinusScanner implements Scanner {
    
    private BufferedReader inFile;
    private Token nextToken;
    
    public CMinusScanner (BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }
    
    public Token getNextToken() {
        Token returnToken = nextToken;
        if (nextToken.getTokenType() != Token.TokenType.EOF_TOKEN)
                nextToken = scanToken();
    }
    
    public Token viewNextToken() {
        return nextToken;
    }
    
    //scanToken method
    public TokenType getToken() {
        
    }
    
}
