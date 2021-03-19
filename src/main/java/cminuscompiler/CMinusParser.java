/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import cminuscompiler.Token.TokenType;
import static cminuscompiler.Token.TokenType.DIVIDE_TOKEN;
import static cminuscompiler.Token.TokenType.EQUALEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.GREATERTHANEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.LESSTHANEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.MINUSEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.MINUSMINUS_TOKEN;
import static cminuscompiler.Token.TokenType.MINUS_TOKEN;
import static cminuscompiler.Token.TokenType.MULTIPLY_TOKEN;
import static cminuscompiler.Token.TokenType.NOTEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.PLUSEQUAL_TOKEN;
import static cminuscompiler.Token.TokenType.PLUSPLUS_TOKEN;
import static cminuscompiler.Token.TokenType.PLUS_TOKEN;
import java.io.IOException;

/**
 *
 * @author yiradz
 */
public class CMinusParser implements Parser {
    private Scanner scan;
    private Token currentToken = new Token();
    
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
    
    // Relop
        private boolean isRelop (TokenType tt){
        if (tt == LESSTHANEQUAL_TOKEN || 
         tt == GREATERTHANEQUAL_TOKEN ||
         tt == EQUALEQUAL_TOKEN ||
         tt ==PLUSEQUAL_TOKEN ||
         tt ==PLUSPLUS_TOKEN ||
         tt == MINUSEQUAL_TOKEN ||
         tt == MINUSMINUS_TOKEN ||
         tt == NOTEQUAL_TOKEN){
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
    
    public void parseError(){
        //syntax error *here* expecting *this* because of *this* 
    }
    
    //matchToken(Token T)
    
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
         switch(currentToken.getTokenType()){
             case PARANOPEN_TOKEN:
                 //nextToken
                 Expression returnE = parseExpression();
                 //matchToken
                 return returnE;
                 break;
             case NUM_TOKEN:
                 Token thisToken;
                 return createNumExpr(thisToken);
                 break;
             case VARCALL_TOKEN:
                 Expression varE = parseVarCall();
                 return varE;
                 break;            
            // First + Follow Sets
             default:
                 parseError();
                 return null;
         }
        
    }
      private Expression parseDecl(){
        
    }
       private Expression parseDeclPrime(){
        
    }
        private Expression parseFunDecl(){
        
    }
     private Expression parseTerm(){
        
    }
      private Expression parseTerm(Expression e){
        
    }
    private Expression parseTermPrime(){
        
    }
     private Expression parseExpression(){
       
        
    }
     private Expression parseExpressionPrime(){
       
        
    }
      private Expression parseExpressionPrimePrime(){
        if (currentToken.getTokenType() == EQUAL_TOKEN) {
                 // match = token
                 Expression e = parseExpression();
                 return e;
                 
                 /*  parseError();
                 return null; */
                 }     
        else {
            // simple expression
        }
            // First + Follow Sets
    
        
    }
      private Expression parseSimpleExpressionPrime(){
       
        
    }
     private Expression parseAdditiveE(){
         Expression lhs = parseTerm();
        
    }
     private Expression parseAdditiveEPrime(Expression e){
         Expression lhs = parseTerm(e);
        
    }
     
     private Expression parseVarCall(Expression e){
         Expression lhs = parseTerm(e);
        
    }
     
     private Expression parseArgs(Expression e){
         Expression lhs = parseTerm(e);
        
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
