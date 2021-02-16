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
    public String getNextChar() {
        try {
            if (linepos == 0) {
                if ((line = inFile.readLine()) == null) {
                    return null;
                } else {
                    lnum += 1;
                }
            }
            try {
                temp = new String(line.charAt(linepos) + "");
            } catch (IndexOutOfBoundsException e) {
                linepos = 0;
                return " ";
            }
            linepos += 1;
        } catch (IOException e) {
            System.err.println("Error reading input file ");
        }
        return temp;
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
            String c = getNextChar();
            //SWITCH STATEMENT
            switch (state) {
                case START:
                    //create helper function
                    if (isDigit(c)) {

                    } //else is a single character token
                    else {

                    }
            }

        }

    }

    //main method
    /*While current-token (or next token?) is not equal to end of file token
    Print tokens (print end of file token)*/
}
