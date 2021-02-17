/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.compiler;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author yiradz
 */
public abstract class CMinusScanner implements Scanner {

    static int linepos = 0;
    static String temp = "";
    static String line = null;
    static int lnum = 0;

    private BufferedReader inFile;
    private Token nextToken;
    
    //List of Keywords
    

    public CMinusScanner(BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }

    public Token getNextToken() {
        Token returnToken = nextToken;
        if (nextToken.getTokenType() != Token.TokenType.EOF_TOKEN) {
            nextToken = scanToken();
        }
        return returnToken;
    }

    public Token viewNextToken() {
        return nextToken;
    }

    // method to munch the next character in token
    public char getNextChar() {
        //gets the next character of the line buff
        
    }
    
    // method to rewind to the previous character in token
    public void ungetNextChar() {
        linepos--;
    }
    
    public void isDigit (char c){
        
    }
    
    public void isAlpha (char c){
        
    }

    //scanToken method
    public Token scanToken() {
        Token.StateType state = Token.StateType.START;
        while (state != Token.StateType.DONE) {
            char c = getNextChar();
            //SWITCH STATEMENT
            switch (state) {
                case START:
                    //Check if character is a digit
                    if (Character.isDigit(c)) {
                        state = Token.StateType.ISNUM;
                    } 
                    //Check if character is an alpha
                    else if (Character.isLetter(c)) {
                        state = Token.StateType.ISID;
                    }
                    //Check if it is a divide or comment symbol
                    else if (c == '/') {
                        state = Token.StateType.ISDIVIDE;
                    }
                    //Check if it is a symbol
                    else if (/*Regex to determine if it is a symbol*/) {
                        state = Token.StateType.ISSYMBOL;
                    }
                    break;
                case ISNUM:
                    if (!Character.isDigit(c)) {
                        ungetNextChar();
                        state = Token.StateType.DONE;
                    }
                    
                    break;
                case ISID:
                    break;
                case ISDIVIDE:
                    break;
                case ISSYMBOL:
                    break;
                    
                    
                    
                    
                    
                    //else is a single character token
                    else {

                    }
            }

        }

    }

    //main method
    /*While current-token (or next token?) is not equal to end of file token
    Print tokens (print end of file token)*/
}
