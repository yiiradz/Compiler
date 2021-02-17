/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

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
 */
public class CMinusScanner {
    static char tokenString[];
    static int mark_position = 0;
    static int stringIndex = 0;
    static BufferedReader inFile;
    static Token nextToken;
    //List of Keywords
    static String Keywords[] = {
        "if", "else", "int", "void", "while", "return"
    };

    static void CMinusScanner(BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }

    static Token getNextToken() {
        Token returnToken = nextToken;
        if (nextToken.getTokenType() != Token.TokenType.EOF_TOKEN) {
            nextToken = scanToken();
        }
        return returnToken;
    }

    static Token viewNextToken() {
        return nextToken;
    }

    // method to munch the next character in token
    static char getNextChar() {
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
    static void ungetNextChar() {
        try {
            inFile.reset();
        } catch (IOException ex) {
            Logger.getLogger(MinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        mark_position -= 1;
    }

    //function to compare identifier to keywords
    static Token.TokenType keywordLookup(char tokenString[]) {
        Token.TokenType keywords = Token.TokenType.ID_TOKEN;
        String tString = new String(tokenString);
        for (int i = 0; i < Keywords.length; i++) {
            if (tString == Keywords[i]) {
                if (tString == "if") {
                    return keywords = Token.TokenType.IF_TOKEN;
                } else if (tString == "else") {
                    return keywords = Token.TokenType.ELSE_TOKEN;
                } else if (tString == "int") {
                    return keywords = Token.TokenType.INT_TOKEN;
                } else if (tString == "void") {
                    return keywords = Token.TokenType.VOID_TOKEN;
                } else if (tString == "while") {
                    return keywords = Token.TokenType.WHILE_TOKEN;
                } else if (tString == "return") {
                    return keywords = Token.TokenType.RETURN_TOKEN;
                }
            }
        }
        return keywords;
    }

    //scanToken method
    static Token scanToken() {
        //Set the starting state to START
        Token.StateType state = Token.StateType.START;
        //Create the tokenType variable
        Token currentToken = null;
        //Index for storing into tokenString
        int tokenStringIndex = 0;
        //Flag to indicate save to tokenString
        boolean save;
        while (state != Token.StateType.DONE) {
            char c = getNextChar();
            save = true;
            //SWITCH STATEMENT
            switch (state) {
                case START:
                    //Check if character is a digit
                    if (Character.isDigit(c)) {
                        state = Token.StateType.ISNUM;
                    } //Check if character is an alpha
                    else if (Character.isLetter(c)) {
                        state = Token.StateType.ISID;
                    } //Check if it is a divide or comment symbol
                    else if (c == '/') {
                        state = Token.StateType.ISDIVIDE;
                    } //Check if it is a symbol other than + or -
                    else if (c == '=' || c == '>' || c == '<' || c == '!') {
                        state = Token.StateType.ISDOUBLE;
                    } else if (c == '+') {
                        state = Token.StateType.ISPLUS;
                    } else if (c == '-') {
                        state = Token.StateType.ISMINUS;
                    } else if (c == ' ' || c == '\t' || c == '\n') {
                        save = false;
                    } else {
                        state = Token.StateType.DONE;
                        switch (c) {
                            case (char) (-1):
                                save = false;
                                currentToken.setTokenType(Token.TokenType.EOF_TOKEN);
                                break;
                            case '<':
                                currentToken.setTokenType(Token.TokenType.LESSTHAN_TOKEN);
                                break;
                            case '>':
                                currentToken.setTokenType(Token.TokenType.GREATERTHAN_TOKEN);
                                break;
                            case '=':
                                currentToken.setTokenType(Token.TokenType.EQUAL_TOKEN);
                                break;
                            case '+':
                                currentToken.setTokenType(Token.TokenType.PLUS_TOKEN);
                                break;
                            case '-':
                                currentToken.setTokenType(Token.TokenType.MINUS_TOKEN);
                                break;
                            case '{':
                                currentToken.setTokenType(Token.TokenType.BRACEOPEN_TOKEN);
                                break;
                            case '}':
                                currentToken.setTokenType(Token.TokenType.BRACECLOSE_TOKEN);
                                break;
                            case '(':
                                currentToken.setTokenType(Token.TokenType.PARANOPEN_TOKEN);
                                break;
                            case ')':
                                currentToken.setTokenType(Token.TokenType.PARANCLOSE_TOKEN);
                                break;
                            case '*':
                                currentToken.setTokenType(Token.TokenType.MULTIPLY_TOKEN);
                                break;
                            case '/':
                                currentToken.setTokenType(Token.TokenType.DIVIDE_TOKEN);
                                break;
                            case ';':
                                currentToken.setTokenType(Token.TokenType.SEMICOLON_TOKEN);
                            default:
                                currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                                break;
                        }
                    }
                    break;
                case ISNUM:
                    if (!Character.isDigit(c)) {
                        ungetNextChar();
                        save = false;
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.INT_TOKEN);
                    }
                    break;
                case ISID:
                    if (!Character.isLetter(c)) {
                        ungetNextChar();
                        save = false;
                        state = Token.StateType.ISKEYWORD;
                        currentToken.setTokenType(Token.TokenType.ID_TOKEN);
                    }
                    break;
                case ISDIVIDE:
                    if (c == '*') {
                        state = Token.StateType.ISCOMMENT;
                    } else {
                        ungetNextChar();
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISCOMMENT:
                    save = false;
                    if (c == '*') {
                        state = Token.StateType.ISFINISHCOMMENT;
                    }
                    break;
                case ISFINISHCOMMENT:
                    save = false;
                    if (c == '/') {
                        state = Token.StateType.START;
                    } else {
                        ungetNextChar();
                        state = Token.StateType.ISCOMMENT;
                    }
                    break;
                case ISPLUS:
                    if (c == '=') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.PLUSEQUAL_TOKEN);
                    } else if (c == '+') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.PLUSPLUS_TOKEN);
                    } else {
                        ungetNextChar();
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISMINUS:
                    if (c == '=') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.MINUSEQUAL_TOKEN);
                    } else if (c == '-') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.MINUSMINUS_TOKEN);
                    } else {
                        ungetNextChar();
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISDOUBLE:
                    if (c == '=') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.EQUALEQUAL_TOKEN);
                    } else {
                        ungetNextChar();
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISKEYWORD:
                    keywordLookup(tokenString);
                    state = Token.StateType.DONE;
                    break;
                case DONE:
                default:
                    currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
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
    public static void main(String args[]) throws IOException {
         BufferedReader br = null;
        // Read input file 
         br = new BufferedReader(new FileReader("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/scanner/input1.txt"));
         
        //Call Scanner
       // CMinusScanner myScanner = new CMinusScanner(); // Create an object of Main
        
        CMinusScanner(br);
        
        Token token = getNextToken();
        BufferedWriter writer = new BufferedWriter(new FileWriter("outputfile.txt"));

        //While current-token (or next token?) is not equal to end of file token
        while (token.getTokenType() != Token.TokenType.EOF_TOKEN) {
            
            //Print tokens (print end of file token)
            writer.write(token.getTokenType().toString() + " " + token.getTokenData()+ "\n");

        }
        
        writer.close();

    }
   
}
