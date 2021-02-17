/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import scanner.Token.TokenType;
import scanner.Token.StateType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 * @author mpoh98
 */
public abstract class MinusScanner implements Scanner {

    private char tokenString[];
    private int mark_position = 0;
    private int stringIndex = 0;
    private BufferedReader inFile;
    private Token nextToken;
    //List of Keywords
    private String Keywords[] = {
        "if", "else", "int", "void", "while", "return"
    };

    public void CMinusScanner(BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }

    public Token getNextToken() {
        Token returnToken = nextToken;
        if (nextToken.getTokenType() != TokenType.EOF_TOKEN) {
            nextToken = scanToken();
        }
        return returnToken;
    }

    public Token viewNextToken() {
        return nextToken;
    }

    // method to munch the next character in token
    public char getNextChar() {
        char readChar = ' ';
        try {
            inFile.mark(mark_position);
            readChar = (char) inFile.read();
            mark_position += 1;
        } catch (IOException ex) {
            Logger.getLogger(MinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readChar;
    }

    // method to rewind to the previous character in token
    public void ungetNextChar() {
        try {
            inFile.reset();
        } catch (IOException ex) {
            Logger.getLogger(MinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        mark_position -= 1;
    }

    //function to compare identifier to keywords
    public TokenType keywordLookup(char tokenString[]) {
        TokenType keywords = TokenType.ID_TOKEN;
        String tString = new String(tokenString);
        for (int i = 0; i < Keywords.length; i++) {
            if (tString == Keywords[i]) {
                if (tString == "if") {
                    return keywords = TokenType.IF_TOKEN;
                } else if (tString == "else") {
                    return keywords = TokenType.ELSE_TOKEN;
                } else if (tString == "int") {
                    return keywords = TokenType.INT_TOKEN;
                } else if (tString == "void") {
                    return keywords = TokenType.VOID_TOKEN;
                } else if (tString == "while") {
                    return keywords = TokenType.WHILE_TOKEN;
                } else if (tString == "return") {
                    return keywords = TokenType.RETURN_TOKEN;
                }
            }
        }
        return keywords;
    }

    //scanToken method
    private Token scanToken() {
        //Set the starting state to START
        StateType state = StateType.START;
        //Create the tokenType variable
        Token currentToken = null;
        //Index for storing into tokenString
        int tokenStringIndex = 0;
        //Flag to indicate save to tokenString
        boolean save;
        while (state != StateType.DONE) {
            char c = getNextChar();
            save = true;
            //SWITCH STATEMENT
            switch (state) {
                case START:
                    //Check if character is a digit
                    if (Character.isDigit(c)) {
                        state = StateType.ISNUM;
                    } //Check if character is an alpha
                    else if (Character.isLetter(c)) {
                        state = StateType.ISID;
                    } //Check if it is a divide or comment symbol
                    else if (c == '/') {
                        state = StateType.ISDIVIDE;
                    } //Check if it is a symbol other than + or -
                    else if (c == '=' || c == '>' || c == '<' || c == '!') {
                        state = StateType.ISDOUBLE;
                    } else if (c == '+') {
                        state = StateType.ISPLUS;
                    } else if (c == '-') {
                        state = StateType.ISMINUS;
                    } else if (c == ' ' || c == '\t' || c == '\n') {
                        save = false;
                    } else {
                        state = StateType.DONE;
                        switch (c) {
                            case (char) (-1):
                                save = false;
                                currentToken.setTokenType(TokenType.EOF_TOKEN);
                                break;
                            case '<':
                                currentToken.setTokenType(TokenType.LESSTHAN_TOKEN);
                                break;
                            case '>':
                                currentToken.setTokenType(TokenType.GREATERTHAN_TOKEN);
                                break;
                            case '=':
                                currentToken.setTokenType(TokenType.EQUAL_TOKEN);
                                break;
                            case '+':
                                currentToken.setTokenType(TokenType.PLUS_TOKEN);
                                break;
                            case '-':
                                currentToken.setTokenType(TokenType.MINUS_TOKEN);
                                break;
                            case '{':
                                currentToken.setTokenType(TokenType.BRACEOPEN_TOKEN);
                                break;
                            case '}':
                                currentToken.setTokenType(TokenType.BRACECLOSE_TOKEN);
                                break;
                            case '(':
                                currentToken.setTokenType(TokenType.PARANOPEN_TOKEN);
                                break;
                            case ')':
                                currentToken.setTokenType(TokenType.PARANCLOSE_TOKEN);
                                break;
                            case '*':
                                currentToken.setTokenType(TokenType.MULTIPLY_TOKEN);
                                break;
                            case '/':
                                currentToken.setTokenType(TokenType.DIVIDE_TOKEN);
                                break;
                            case ';':
                                currentToken.setTokenType(TokenType.SEMICOLON_TOKEN);
                            default:
                                currentToken.setTokenType(TokenType.ERROR_TOKEN);
                                break;
                        }
                    }
                    break;
                case ISNUM:
                    if (!Character.isDigit(c)) {
                        ungetNextChar();
                        save = false;
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.INT_TOKEN);
                    }
                    break;
                case ISID:
                    if (!Character.isLetter(c)) {
                        ungetNextChar();
                        save = false;
                        state = StateType.ISKEYWORD;
                        currentToken.setTokenType(TokenType.ID_TOKEN);
                    }
                    break;
                case ISDIVIDE:
                    if (c == '*') {
                        state = StateType.ISCOMMENT;
                    } else {
                        ungetNextChar();
                        state = StateType.DONE;
                    }
                    break;
                case ISCOMMENT:
                    save = false;
                    if (c == '*') {
                        state = StateType.ISFINISHCOMMENT;
                    }
                    break;
                case ISFINISHCOMMENT:
                    save = false;
                    if (c == '/') {
                        state = StateType.START;
                    } else {
                        ungetNextChar();
                        state = StateType.ISCOMMENT;
                    }
                    break;
                case ISPLUS:
                    if (c == '=') {
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.PLUSEQUAL_TOKEN);
                    } else if (c == '+') {
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.PLUSPLUS_TOKEN);
                    } else {
                        ungetNextChar();
                        state = StateType.DONE;
                    }
                    break;
                case ISMINUS:
                    if (c == '=') {
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.MINUSEQUAL_TOKEN);
                    } else if (c == '-') {
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.MINUSMINUS_TOKEN);
                    } else {
                        ungetNextChar();
                        state = StateType.DONE;
                    }
                    break;
                case ISDOUBLE:
                    if (c == '=') {
                        state = StateType.DONE;
                        currentToken.setTokenType(TokenType.EQUALEQUAL_TOKEN);
                    } else {
                        ungetNextChar();
                        state = StateType.DONE;
                    }
                    break;
                case ISKEYWORD:
                    keywordLookup(tokenString);
                    state = StateType.DONE;
                    break;
                case DONE:
                default:
                    currentToken.setTokenType(TokenType.ERROR_TOKEN);
                    break;
            }
            //build tokenstring
            if (save){
                tokenString[stringIndex] = c;
                stringIndex++;
            }

        }
        
        // Set Token Data
        currentToken.setTokenData(tokenString);
        
        // Reset Char array Index counter
        stringIndex = 0;
        
        //return object of the class token with data filled in
        return currentToken;
    }

    //main method
    public void main(String args[]) throws IOException {
         BufferedReader br = null;
        // Read input file 
         br = new BufferedReader(new FileReader("/input1.txt"));
         
        //Call Scanner
        
       CMinusScanner(br);
        
        Token token = getNextToken();
        BufferedWriter writer = new BufferedWriter(new FileWriter("/outputfile.txt"));

        //While current-token (or next token?) is not equal to end of file token
        while (token.getTokenType() != TokenType.EOF_TOKEN) {
            
            //Print tokens (print end of file token)
            writer.write(token.getTokenType().toString() + " " + token.getTokenData()+ "\n");

        }
        
        writer.close();

    }
}
