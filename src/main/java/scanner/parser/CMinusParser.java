/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner.parser;

import java.io.IOException;
import scanner.Scanner;
import scanner.Token.TokenType;
import static scanner.Token.TokenType.DIVIDE_TOKEN;
import static scanner.Token.TokenType.MINUS_TOKEN;
import static scanner.Token.TokenType.MULTIPLY_TOKEN;
import static scanner.Token.TokenType.PLUS_TOKEN;

/**
 *
 * @author yiradz
 */
public class CMinusParser implements Parser {
    private Scanner scan;
    
    // Addop
    private boolean isAddop (TokenType tt){
        if (tt == PLUS_TOKEN || tt == MINUS_TOKEN){
            return true;
        }
        else return false;
    }
    
    // Mulop
    private boolean isMulop (TokenType tt){
        if (tt == MULTIPLY_TOKEN || tt == DIVIDE_TOKEN){
            return true;
        }
        else return false;
    }
    
    public CMinusParser(String file){
        scan = new Scanner(file);
        
    }
    
    public Program parse(){
        //is this where all the magic happens?
        return new Program();
    }
    
    private Statement parseStmt(){
        
    }
    private Statement parseIfStmt(){
        Statement returnStmt = new IfStmt();
        return returnStmt;
    }
    private Statement parseWhileStmt(){
        
    }
     private Statement parseReturnStmt(){
        
    }
      private Statement parseCompoundStmt(){
        
    }
     private Expression parseFactor(){
        
    }
     private Expression parseTerm(){
        
    }
     private Expression parseExpression(){
        Expression lhs = parseTerm();
        
    }
     private Expression parseAdditiveExpr(){
        
    }
    
     //main method
    public static void main(String args[]) throws IOException {
    String fileName = "test";
    String sourceFile = fileName + ".c";
    Parser myParser = new CMinusParser(sourceFile);
    Program myProgram = myParser.parse();
    myProgram.printTree();
    
    }
}
