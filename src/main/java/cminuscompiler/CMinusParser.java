/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

//import static cminuscompiler.CMinusScanner.CMinusScanner;
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
    private static Program myProgram = new Program();
    TokenType errorTokenType = TokenType.ERROR_TOKEN;

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

    public void parseError(/*TokenType curToken, TokenType matchedToken*/) {
        //System.out.println("ERROR: Syntax Error.  Received: " + curToken + ", expected: " + matchedToken + ".  Parsing failed.");//syntax error *here* expecting *this* because of *this* 
    }

    public boolean matchToken(TokenType T) {

        //Add to AST?
        //if token == token yay
        if (currentToken.getTokenType() == T) {
            return true;
        } else {
            parseError(/*currentToken.getTokenType(), T*/);
        }
        return false;
    }

    public Program parse() {
        //Start parsing
        Declaration lhs = parseDecl();
        myProgram.DeclList.add(lhs);

        //loop for optional rhs
        while (scan.viewNextToken().getTokenType() == TokenType.VOID_TOKEN || scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN) {
            currentToken = scan.getNextToken();
            Declaration rhs = parseDecl();
            myProgram.DeclList.add(rhs);
        }

        return myProgram;
    }

    private Declaration parseDecl() {
        switch (currentToken.getTokenType()) {
            case VOID_TOKEN:
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.ID_TOKEN);
                Declaration funDecl = parseFunDecl();
                return funDecl;

            case INT_TOKEN:
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.ID_TOKEN);
                Declaration declPrime = parseDeclPrime();
                return declPrime;

            default:
                parseError();
                return null;
        }

    }

    private Declaration parseDeclPrime() {
        switch (currentToken.getTokenType()) {
            case SEMICOLON_TOKEN: // this is a variable declaration
            //add to AST

            case BRACKETOPEN_TOKEN: // this is an array declaration
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.NUM_TOKEN);
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.BRACKETCLOSE_TOKEN);
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.SEMICOLON_TOKEN);
                currentToken = scan.getNextToken();
            // add to AST

            case PARANOPEN_TOKEN: // this is a function declaration 
                currentToken = scan.getNextToken();
                Declaration funDecl = parseFunDecl();
                return funDecl;

            // Follow Sets
            default:
                parseError();
                return null;
        }
    }

    private Declaration parseFunDecl() {
        //match ( token
        matchToken(Token.TokenType.PARANOPEN_TOKEN);
        currentToken = scan.getNextToken();
        //new params
        Param p = parseParams();
        //match ) token
        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        Statement cmpStmt = parseCompoundStmt();

        Declaration fdecl = new FunctionDecl(p, cmpStmt);

        return fdecl;
    }

    private Param parseParams() {

        switch (currentToken.getTokenType()) {
            case INT_TOKEN:
                Param lhs = parseParam();

                while (scan.viewNextToken().getTokenType() == TokenType.COMMA_TOKEN) {
                    currentToken = scan.getNextToken();

                    Param rhs = parseParam();

                    //store param before we loop
                }
                
                return lhs;
                
            case VOID_TOKEN:
                //store in AST
                
            default:
                parseError();
                return null;
        }
    }

    private Param parseParam() {
        
        Param p = new Param();
        
        matchToken(TokenType.INT_TOKEN);
        currentToken = scan.getNextToken();
        matchToken(TokenType.ID_TOKEN);
        
        if (scan.viewNextToken().getTokenType() == TokenType.BRACKETOPEN_TOKEN) {
            currentToken = scan.getNextToken();
            matchToken(TokenType.BRACKETOPEN_TOKEN);
            currentToken = scan.getNextToken();
            matchToken(TokenType.BRACKETCLOSE_TOKEN);
        }
        
        //Add it to the param before returning it
        
        return p;
    }

    private Statement parseCompoundStmt() {
        
        Statement cmpdStmt = new CompoundStmt();
        
        matchToken(TokenType.BRACEOPEN_TOKEN);
        //something for a loc-decl
        while (scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN) {
            matchToken(TokenType.INT_TOKEN);
            currentToken = scan.getNextToken();
            matchToken(TokenType.ID_TOKEN);
            
            if (scan.viewNextToken().getTokenType() == TokenType.BRACKETOPEN_TOKEN) {
                currentToken = scan.getNextToken();
                matchToken(TokenType.BRACKETOPEN_TOKEN);
                currentToken = scan.getNextToken();
                matchToken(TokenType.NUM_TOKEN);
                currentToken = scan.getNextToken();
                matchToken(TokenType.BRACKETCLOSE_TOKEN);
            }
        }
        
        //something for a stmt
        while (scan.viewNextToken().getTokenType() == TokenType.BRACEOPEN_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.IF_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.WHILE_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.RETURN_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.PARANOPEN_TOKEN ||
                scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN) {
            Statement stmt = parseStmt();
            
            return stmt;
        }

        matchToken(TokenType.BRACECLOSE_TOKEN);
        
        return cmpdStmt;
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

            case INT_TOKEN:
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

    private Expression parseFactor() {
        switch (currentToken.getTokenType()) {
            case PARANOPEN_TOKEN:
                //nextToken
                Expression returnE = parseExpression();
                matchToken(TokenType.PARANOPEN_TOKEN);
                return returnE;

            case INT_TOKEN:
                Token thisToken = currentToken;
                NumExpression n = new NumExpression();
                return n.createNumExpr(thisToken);

            case 0:
                Expression varE = parseVarCall();
                return varE;

            // First + Follow Sets
            default:
                parseError();
                return null;
        }

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
            case INT_TOKEN:
                Token thisToken = currentToken;
                matchToken(Token.TokenType.INT_TOKEN);
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
            case INT_TOKEN:
            case PARANOPEN_TOKEN:
                matchToken(Token.TokenType.PARANOPEN_TOKEN);
                // new args
                matchToken(Token.TokenType.PARANCLOSE_TOKEN);
                return null;
            // new args 
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
            //return e;

            parseError();
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

    }

    //main method
    public static void main(String args[]) throws IOException {
        BufferedReader br = null;
        // Read c file into scanner (need to adjust this path name)
        br = new BufferedReader(new FileReader("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/test.c"));
        // read in scanner output to parser          
        Parser myParser = new CMinusParser(br);
        myProgram = myParser.parse();
        myProgram.printTree();

    }
}
