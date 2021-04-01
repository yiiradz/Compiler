/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 * @author mpoh98
 */
public class CMinusScanner {
    static ArrayList<Character> tokenString = new ArrayList<Character>();
    static int mark_position = 0;
    static int fileInput = 0;
    //static int stringIndex = 0;
    static BufferedReader inFile;
    static Token nextToken;
    //List of Keywords
    static String[] Keywords = new String[]{"if", "else", "int", "void", "while", "return"};
    static StringBuffer sb = new StringBuffer();
    
     void CMinusScanner(BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }

  Token getNextToken() {

        Token returnToken = nextToken;
        if (nextToken.getTokenType() != Token.TokenType.EOF_TOKEN) {
            nextToken = scanToken();
        }
        //returnToken = nextToken;
        return returnToken;
    }

    Token viewNextToken() {
        return nextToken;
    }

    // method to rewind to the previous character in token
    static void ungetNextChar() {
        try {
            inFile.reset();
        } catch (IOException ex) {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        mark_position -= 1;
    }

    //function to compare identifier to keywords
    static Token.TokenType keywordLookup(ArrayList tokenString) {
        Token.TokenType keywords = Token.TokenType.ID_TOKEN;
        
        //Clear the String Buffer in case anything was left over
        sb.delete(0, sb.length());
        
        //Convert the ArrayList to a String
        for (Object s : tokenString) {
            sb.append(s);
        }
        String tString = sb.toString();
        
        //Loop through and compare the tokenString to the keyword
        for (int i = 0; i < Keywords.length; i++) {
            if (tString.equals(Keywords[i])) {
                switch (tString) {
                    case "if":
                        return keywords = Token.TokenType.IF_TOKEN;
                    case "else":
                        return keywords = Token.TokenType.ELSE_TOKEN;
                    case "int":
                        return keywords = Token.TokenType.INT_TOKEN;
                    case "void":
                        return keywords = Token.TokenType.VOID_TOKEN;
                    case "while":
                        return keywords = Token.TokenType.WHILE_TOKEN;
                    case "return":
                        return keywords = Token.TokenType.RETURN_TOKEN;
                    default:
                        break;
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
        Token currentToken = new Token();
        //Flag to indicate save to tokenString
        boolean save;

        while (state != Token.StateType.DONE) {
            
          try {
            inFile.mark(mark_position);
            fileInput = inFile.read();
            mark_position += 1;
            
        } catch (IOException ex) {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
            char c = (char)fileInput;
            
            save = true;
            //SWITCH STATEMENT
            switch (state) {
                case START:
                    if (fileInput == -1){
                        save = false;
                        currentToken.setTokenType(Token.TokenType.EOF_TOKEN);
                        state = Token.StateType.DONE;
                      }
                    // Check for white space
                    else if (c == ' ' || c == '\t' || c == '\n') {
                        save = false;
                    }
                    //Check if character is a digit
                    else if (Character.isDigit(c)) {
                        state = Token.StateType.ISNUM;
                    } //Check if character is an alpha
                    else if (Character.isLetter(c)) {
                        state = Token.StateType.ISID;
                    } //Check if it is a divide or comment symbol
                    else if (c == '/') {
                        save = false;
                        state = Token.StateType.ISDIVIDE;
                    } //Check if it is a symbol other than + or -
                    else if (c == '=' || c == '>' || c == '<' || c == '!') {
                        state = Token.StateType.ISDOUBLE;
                    } else if (c == '+') {
                        state = Token.StateType.ISPLUS;
                    } else if (c == '-') {
                        state = Token.StateType.ISMINUS;
                    
                    } else {
                        state = Token.StateType.DONE;
                        switch (c) {
                            case (char) (-1):
                                save = false;
                                currentToken.setTokenType(Token.TokenType.EOF_TOKEN);
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
                                break;
                            case '[':
                                currentToken.setTokenType(Token.TokenType.BRACKETOPEN_TOKEN);
                                break;
                            case ']':
                                currentToken.setTokenType(Token.TokenType.BRACKETCLOSE_TOKEN);
                                break;
                            default:
                                currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                                break;
                        }
                    }
                    break;
                case ISNUM:
                    if (!Character.isDigit(c)) {
                        if (!Character.isLetter(c)) {
                            ungetNextChar();
                            save = false;
                            state = Token.StateType.DONE;
                            currentToken.setTokenType(Token.TokenType.NUM_TOKEN);
                        }
                        else {
                            ungetNextChar();
                            save = false;
                            state = Token.StateType.DONE;
                            currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                        }
                    }
                    break;
                case ISID:
                    if (!Character.isLetter(c)) {
                        if (!Character.isDigit(c)) {
                            ungetNextChar();
                            save = false;
                            state = Token.StateType.ISKEYWORD;
                            currentToken.setTokenType(Token.TokenType.ID_TOKEN);
                        }
                        else {
                            ungetNextChar();
                            save = false;
                            state = Token.StateType.DONE;
                            currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                        }
                    }
                    break;
                case ISDIVIDE:
                    if (c == '*') {
                        state = Token.StateType.ISCOMMENT;
                    } else {
                        save = true;
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
                    save = false;
                    if (c == '=') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.PLUSEQUAL_TOKEN);
                    } else if (c == '+') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.PLUSPLUS_TOKEN);
                    } else {
                        ungetNextChar();
                        currentToken.setTokenType(Token.TokenType.PLUS_TOKEN);
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISMINUS:
                    save = false;
                    if (c == '=') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.MINUSEQUAL_TOKEN);
                    } else if (c == '-') {
                        state = Token.StateType.DONE;
                        currentToken.setTokenType(Token.TokenType.MINUSMINUS_TOKEN);
                    } else {
                        ungetNextChar();
                        currentToken.setTokenType(Token.TokenType.MINUS_TOKEN);
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISDOUBLE:
                    if (c == '=') {
                        if (tokenString.get(0) == '>') {
                            currentToken.setTokenType(Token.TokenType.GREATERTHANEQUAL_TOKEN);
                        }
                        else if (tokenString.get(0) == '<') {
                            currentToken.setTokenType(Token.TokenType.LESSTHANEQUAL_TOKEN);
                        }
                        else if (tokenString.get(0) == '=') {
                            currentToken.setTokenType(Token.TokenType.EQUALEQUAL_TOKEN);
                        }
                        else if (tokenString.get(0) == '!') {
                            currentToken.setTokenType(Token.TokenType.NOTEQUAL_TOKEN);
                        }
                        else {
                            currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                        }
                        state = Token.StateType.DONE;
                    } else {
                        save = false;
                        ungetNextChar();
                        if (tokenString.get(0) == '>') {
                            currentToken.setTokenType(Token.TokenType.GREATERTHAN_TOKEN);
                        }
                        else if (tokenString.get(0) == '<') {
                            currentToken.setTokenType(Token.TokenType.LESSTHAN_TOKEN);
                        }
                        else if (tokenString.get(0) == '=') {
                            currentToken.setTokenType(Token.TokenType.EQUAL_TOKEN);
                        }
                        else if (tokenString.get(0) == '!') {
                            currentToken.setTokenType(Token.TokenType.NOTEQUAL_TOKEN);
                        }
                        else {
                            currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                        }
                        state = Token.StateType.DONE;
                    }
                    break;
                case ISKEYWORD:
                    save = false;
                    currentToken.setTokenType(keywordLookup(tokenString));
                    state = Token.StateType.DONE;
                    break;
                case DONE:
                default:
                    currentToken.setTokenType(Token.TokenType.ERROR_TOKEN);
                    break;
            }
            //build tokenstring
            if (save){
                tokenString.add(c);
            }

        }
        
        // Set Token Data
        sb.delete(0, sb.length());
        tokenString.forEach(s -> {
            sb.append(s);
        });
        String dataString = sb.toString();
        currentToken.setTokenData(dataString);
        
        //return object of the class token with data filled in
        tokenString.clear();
        return currentToken;
    }

    //main method
    public void main(String args[]) throws FileNotFoundException, IOException {
        
        BufferedReader br = null;
        // Read c file into scanner (need to adjust this path name)
        br = new BufferedReader(new FileReader("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/test.c"));
        
        //Call Scanner  
        CMinusScanner(br);
        
        Token token;
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/outputfile.txt"));

        //Loop through and print the tokens until you the end of file token
        while (true) {
            
            //clear the tokenString to prepare for the next token
            tokenString.clear();
            
            token = getNextToken();

            //Print tokens (print end of file token)
            if (token.getTokenType() == Token.TokenType.EOF_TOKEN) {
                writer.write(token.getTokenType().toString() + " " + token.getTokenData()+ "\n");
                break;
            }
            else {
                writer.write(token.getTokenType().toString() + " " + token.getTokenData()+ "\n");
            }
        }
        
        writer.close();

    }
}
