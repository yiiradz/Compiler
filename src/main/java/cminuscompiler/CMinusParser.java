/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import static cminuscompiler.CMinusScanner.CMinusScanner;
import cminuscompiler.Token.TokenType;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

/**
 *
 * @author yiradz
 */
public class CMinusParser implements Parser {

    private static CMinusScanner scan = new CMinusScanner();
    private Token currentToken = new Token();

    public CMinusParser(BufferedReader file) {
        
        scan.CMinusScanner(file);

    }

    // Addop
    private boolean isAddop(TokenType tt) {
        if (tt == Token.TokenType.PLUS_TOKEN || tt == Token.TokenType.MINUS_TOKEN) {
            return true;
        } else {
            return false;
        }
    }

    // Mulop
    private boolean isMulop(TokenType tt) {
        if (tt == Token.TokenType.MULTIPLY_TOKEN || tt == Token.TokenType.DIVIDE_TOKEN) {
            return true;
        } else {
            return false;
        }
    }

    // Relop
    private boolean isRelop(TokenType tt) {
        if (tt == Token.TokenType.LESSTHANEQUAL_TOKEN
                || tt == Token.TokenType.GREATERTHANEQUAL_TOKEN
                || tt == Token.TokenType.EQUALEQUAL_TOKEN
                || tt == Token.TokenType.PLUSEQUAL_TOKEN
                || tt == Token.TokenType.PLUSPLUS_TOKEN
                || tt == Token.TokenType.MINUSEQUAL_TOKEN
                || tt == Token.TokenType.MINUSMINUS_TOKEN
                || tt == Token.TokenType.NOTEQUAL_TOKEN) {
            return true;
        } else {
            return false;
        }
    }

    public Program parse() {
        //Create a program that populates the arraylist of decls with the tokens from the scanner
        
        return new Program();
    }
/*
    public void parseError() {
        //syntax error *here* expecting *this* because of *this* 
    }

    public void matchToken(TokenType T) {
        //if token == token yay
    }

    private Statement parseStmt() {
        switch (currentToken.getTokenType()) {
            case BRACEOPEN_TOKEN:
                //create new compound stmt
                Statement cmpStmt = new CompoundStmt();
                return cmpStmt;

            case IF_TOKEN:
                //create new if stmt
                Statement ifStmt = new IfStmt();
                return ifStmt;

            case WHILE_TOKEN:
                //create new while stmt
                Statement whileStmt = new WhileStmt();
                return whileStmt;

            case RETURN_TOKEN:
                //create new return stmt
                Statement returnStmt = new ReturnStmt();
                return returnStmt;

            case NUM_TOKEN:
                //create new num expr
                Token thisToken = currentToken;
                return createNumExpr(thisToken);

            case PARANOPEN_TOKEN:
                //create new expression
                Expression e = parseExpression();
                return null;

            case ID_TOKEN:
                //create new expression
                e = parseExpression();
                return null;

            default:
                parseError();
                return null;
        }

    }

    private Statement parseIfStmt() {

        matchToken(Token.TokenType.IF_TOKEN);

        matchToken(Token.TokenType.PARANOPEN_TOKEN);

        Expression ifExpr = parseExpression();

        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        Statement thenStmt = parseStmt();

        Statement elseStmt = null;
        //check for else
        if (currentToken.getTokenType() == Token.TokenType.ELSE_TOKEN) {
            //nextToken
            elseStmt = parseStmt();
        }
        Statement ifStmt = new IfStmt(ifExpr, thenStmt, elseStmt);
        return ifStmt;
    }

    private Statement parseWhileStmt() {
        matchToken(Token.TokenType.WHILE_TOKEN);

        matchToken(Token.TokenType.PARANOPEN_TOKEN);

        Expression whExpr = parseExpression();

        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        Statement thenStmt = parseStmt();

        Statement whStmt = new WhileStmt(whExpr, thenStmt);

        return whStmt;
    }

    private Statement parseReturnStmt() {
        matchToken(Token.TokenType.RETURN_TOKEN);
        Statement returnStmt = new ReturnStmt();
        return returnStmt;
    }

    private Statement parseCompoundStmt() {
        matchToken(Token.TokenType.BRACEOPEN_TOKEN);
        //something for a loc-decl
        //something for a stmt
        matchToken(Token.TokenType.BRACECLOSE_TOKEN);

    }

    private Expression parseFactor() {
        switch (currentToken.getTokenType()) {
            case PARANOPEN_TOKEN:
                //nextToken
                Expression returnE = parseExpression();
                matchToken(Token.TokenType.PARANOPEN_TOKEN);
                return returnE;

            case NUM_TOKEN:
                Token thisToken = currentToken;
                NumExpression n = new NumExpression();
                return n.createNumExpr(thisToken);

            case VARCALL_TOKEN:
                Expression varE = parseVarCall();
                return varE;

            // First + Follow Sets
            default:
                parseError();
                return null;
        }

    }

    private Declaration parseDecl() {

    }

    private Declaration parseDeclPrime() {
        switch (currentToken.getTokenType()) {
            case SEMICOLON_TOKEN: // this is a variable declaration
                //match semicolon
                matchToken(Token.TokenType.SEMICOLON_TOKEN);
            // parse var decl?

            case BRACEOPEN_TOKEN: // this is an array declaration
                // match open brace
                matchToken(Token.TokenType.BRACEOPEN_TOKEN);
            //parse arraydecl

            case PARANOPEN_TOKEN: // this is a function declaration 
                // match open paran 
                matchToken(Token.TokenType.PARANOPEN_TOKEN);
                // parse fundecl
                Declaration fdecl = parseFunDecl();
                return fdecl;

            // Follow Sets
            default:
                parseError();
                return null;
        }
    }

    private Declaration parseFunDecl() {
        //match ( token
        matchToken(Token.TokenType.PARANOPEN_TOKEN);
        //new params
        Params p = parseParams();
        //match ) token
        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        Statement cmpStmt = parseCompoundStmt();

        Declaration fdecl = new FunctionDecl(p, cmpStmt);

        return fdecl;
    }

    private Params parseParams() {
        Param p = parseParam();
    }

    private Param parseParam() {
        //INT

        //ID
        // if [
    }

    private Expression parseTerm() {
        Expression lhs = parseFactor();

        while (isMulop(currentToken.getTokenType())) {
            Token t = currentToken;
            Expression rhs = parseFactor();
            lhs = createBinoExpr(t.getTokenType(), lhs, rhs);
        }
        return lhs;
    }

    private Expression parseTerm(Expression e) {

    }

    private Expression parseTermPrime() {

    }

    private Expression parseExpression() {
        switch (currentToken.getTokenType()) {
            case NUM_TOKEN:
                Token thisToken = currentToken;
                matchToken(Token.TokenType.NUM_TOKEN);
                NumExpression n = new NumExpression();
                Expression se = parseSimpleExpressionPrime(); // or should this be create new se?
                return n.createNumExpr(thisToken);

            case PARANOPEN_TOKEN:
                matchToken(Token.TokenType.PARANOPEN_TOKEN);
                Expression e = parseExpression(); // !!! this recursion doesn't make sense...
                se = parseSimpleExpressionPrime(); // or should this be create new se?
                matchToken(Token.TokenType.PARANCLOSE_TOKEN);
                return e; // e needs to be a combination stmt of e and se

            case ID_TOKEN:
                matchToken(Token.TokenType.ID_TOKEN);
                Expression ep = parseExpressionPrime();
                return ep;

            //Follow Sets
            case SEMICOLON_TOKEN:
            case PARANCLOSE_TOKEN:
            case BRACECLOSE_TOKEN:
            case COMMA_TOKEN:
            default:
                parseError();
                return null;
        }
    }

    private Expression parseExpressionPrime() {
        switch (currentToken.getTokenType()) {
            //First Sets
            case NUM_TOKEN:
            case PARANOPEN_TOKEN:
                matchToken(Token.TokenType.PARANOPEN_TOKEN);
                // new args
                matchToken(Token.TokenType.PARANCLOSE_TOKEN);
                return null;
            /* new args 
            case ID_TOKEN:
            case BRACEOPEN_TOKEN:
                matchToken(Token.TokenType.BRACEOPEN_TOKEN);
                Expression e = parseExpression();
                matchToken(Token.TokenType.BRACECLOSE_TOKEN);
                Expression ep = parseExpressionPrime();
                return e;

            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
            //From Grammar
            //Follow Sets
            case EQUAL_TOKEN:
                matchToken(Token.TokenType.EQUAL_TOKEN);
                e = parseExpression();
                return e;
            //Follow Sets
            case SEMICOLON_TOKEN:
            case PARANCLOSE_TOKEN:
            case BRACECLOSE_TOKEN:
            case COMMA_TOKEN:
            default:
                parseError();
                return null;
        }

    }

    private Expression parseExpressionPrimePrime() {
        if (currentToken.getTokenType() == Token.TokenType.EQUAL_TOKEN) {
            matchToken(Token.TokenType.EQUAL_TOKEN);
            Expression e = parseExpression();
            return e;

            /*  parseError();
                 return null; 
        } else {
            // simple expression
        }
        // First + Follow Sets

    }

    private Expression parseSimpleExpressionPrime() {

    }

    private Expression parseAdditiveE() {
        Expression lhs = parseTerm();
        //parseFactor
        Expression rhs = parseAdditiveEPrime(lhs);
        return rhs;

    }

    private Expression parseAdditiveEPrime(Expression e) {
        Expression lhs = parseTerm(e);

    }

    private Expression parseVarCall() {
        Expression lhs = parseTerm();

    }

    private Expression parseVarCall(Expression e) {
        Expression lhs = parseTerm(e);

    }

    private Expression parseArgs(Expression e) {
        Expression lhs = parseTerm(e);

    } */

    //main method
    public static void main(String args[]) throws IOException {
        BufferedReader br = null;
        // Read c file into scanner (need to adjust this path name)
         br = new BufferedReader(new FileReader("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/test.c"));
        // read in scanner output to parser          
        Parser myParser = new CMinusParser(br);
        Program myProgram = myParser.parse();
        myProgram.printTree();

    }
}
