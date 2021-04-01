/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

//import static cminuscompiler.CMinusScanner.CMinusScanner;
import cminuscompiler.Token.TokenType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;

/**
 *
 * @author yiradz
 */
public class CMinusParser implements Parser {

    private static CMinusScanner scan = new CMinusScanner();
    private Token currentToken = new Token();
    private String name;
    private Object size;
    private Object operator;
    private static Program myProgram = new Program();
    TokenType errorTokenType = TokenType.ERROR_TOKEN;

    public CMinusParser(BufferedReader file) {

        scan.CMinusScanner(file);
        currentToken = scan.getNextToken();
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
        //System.out.println("ERROR: Syntax Error.  Received: " + curToken + ", expected: " + matchedToken + ".  Parsing failed.");
        //syntax error *here* expecting *this* because of *this* 
    }

    public boolean matchToken(TokenType T) {

        //Add to AST?
        //if token == token yay
        if (currentToken.getTokenType() == T) {
            // save T to AL?
            currentToken = scan.getNextToken();
            return true;
        } else {
            parseError(/*currentToken.getTokenType(), T*/);
        }
        // match token should increment
        return false;
    }

    @Override
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
                matchToken(Token.TokenType.VOID_TOKEN);
                //currentToken = scan.getNextToken();
                name = currentToken.getTokenData().toString();
                matchToken(Token.TokenType.ID_TOKEN);
                //currentToken = scan.getNextToken();
                Declaration funDecl = parseFunDecl(0, name);

                return funDecl;

            case INT_TOKEN:
                
                currentToken = scan.getNextToken();
                name = currentToken.getTokenData().toString();
                matchToken(Token.TokenType.ID_TOKEN);
                //currentToken = scan.getNextToken();
               Declaration declPrime = parseDeclPrime(name);
                return declPrime;

            default:
                parseError();
                return null;
        }

    }

    private Declaration parseDeclPrime(String name) { // type is int

        switch (currentToken.getTokenType()) {
            case SEMICOLON_TOKEN: // this is a variable declaration
                Declaration decl = new LocalDecl(name, 0);// size = 0
                return decl;

            case BRACKETOPEN_TOKEN: // this is an array declaration
                
                Declaration dec = parseLocalDecl();
                return dec;

            case PARANOPEN_TOKEN: // this is a function declaration 
                currentToken = scan.getNextToken();
                Declaration funDecl = parseFunDecl(0, name);
                return funDecl;

            default:
                parseError();
                return null;
        }
    }

    private Declaration parseFunDecl(int type, String name) {
        //match ( token
        matchToken(Token.TokenType.PARANOPEN_TOKEN);
        //currentToken = scan.getNextToken();
        //new params
        Params p = parseParams();
        currentToken = scan.getNextToken();
        //match ) token
        matchToken(Token.TokenType.PARANCLOSE_TOKEN);
        //currentToken = scan.getNextToken();
        Statement cmpStmt = parseCompoundStmt();

        Declaration fdecl = new FunctionDecl(name, type, p, cmpStmt);

        return fdecl;
    }

    private Params parseParams() {

        switch (currentToken.getTokenType()) {
            case INT_TOKEN:
                matchToken(TokenType.INT_TOKEN);
                //currentToken = scan.getNextToken();
                Param lhs = parseParam();
                Params params = new Params(lhs);

                while (scan.viewNextToken().getTokenType() == TokenType.COMMA_TOKEN) {
                    currentToken = scan.getNextToken();

                    Param rhs = parseParam();

                    //store param in arraylist
                    params.add(rhs);
                }

                return params;

            case VOID_TOKEN:
                // Save Void
                Params p = new Params(currentToken.getTokenData().toString());
                return p;

            default:
                parseError();
                return null;
        }
    }

    private Param parseParam() {

        Param p = new Param();
        currentToken = scan.getNextToken();
        matchToken(TokenType.INT_TOKEN);
        //currentToken = scan.getNextToken();
        matchToken(TokenType.ID_TOKEN);

        if (/*scan.viewNextToken()*/currentToken.getTokenType() == TokenType.BRACKETOPEN_TOKEN) {
            //currentToken = scan.getNextToken();
            matchToken(TokenType.BRACKETOPEN_TOKEN);
            //currentToken = scan.getNextToken();
            matchToken(TokenType.BRACKETCLOSE_TOKEN);
        }

        //Add it to the param before returning it
        return p;
    }

    private Statement parseCompoundStmt() {
        Declaration dec = null;
        Statement stmt = null;
        CompoundStmt c = new CompoundStmt(dec, stmt);
        //currentToken = scan.getNextToken();
        matchToken(TokenType.BRACEOPEN_TOKEN);
        size = 0;
        //something for a loc-decl
        while (/*scan.viewNextToken()*/currentToken.getTokenType() == TokenType.INT_TOKEN) {
            dec = parseLocalDecl();
            c.add(dec);
        }

        //something for a stmt
        while (currentToken.getTokenType() == TokenType.BRACEOPEN_TOKEN
                || currentToken.getTokenType() == TokenType.IF_TOKEN
                || currentToken.getTokenType() == TokenType.WHILE_TOKEN
                || currentToken.getTokenType() == TokenType.RETURN_TOKEN
                || currentToken.getTokenType() == TokenType.NUM_TOKEN
                || currentToken.getTokenType() == TokenType.PARANOPEN_TOKEN
                || currentToken.getTokenType() == TokenType.ID_TOKEN) {

            stmt = parseStmt();
            c.add(stmt);

        }

        currentToken = scan.getNextToken();
        matchToken(TokenType.BRACECLOSE_TOKEN);
        Statement cmpdStmt = new CompoundStmt(dec, stmt);
       

        return c;
    }

    private Declaration parseLocalDecl() {
        Declaration ld = null;
        matchToken(TokenType.INT_TOKEN);
        //currentToken = scan.getNextToken();
        //Add ID to the local decl
        name = currentToken.getTokenData().toString();
        matchToken(TokenType.ID_TOKEN);

        if (currentToken.getTokenType() == TokenType.BRACKETOPEN_TOKEN) {
            currentToken = scan.getNextToken();
            matchToken(TokenType.BRACKETOPEN_TOKEN);
            //Add NUM to the local decl
            size = currentToken.getTokenData().toString();
            matchToken(Token.TokenType.NUM_TOKEN);
            matchToken(TokenType.BRACKETCLOSE_TOKEN);
            matchToken(TokenType.SEMICOLON_TOKEN);
        }
        else if (currentToken.getTokenType() == TokenType.SEMICOLON_TOKEN){
            matchToken(TokenType.SEMICOLON_TOKEN);
        }
        ld = new LocalDecl(name,size);
        return ld;
    }

    private Statement parseStmt() {
        switch (currentToken.getTokenType()) {
            case BRACEOPEN_TOKEN:
                currentToken = scan.getNextToken();
                //create new compound stmt
                Statement cmpStmt = parseCompoundStmt();
                return cmpStmt;

            case IF_TOKEN:
                //create new if stmt
                Statement ifStmt = parseIfStmt();
                return ifStmt;

            case WHILE_TOKEN:
                currentToken = scan.getNextToken();
                //create new while stmt
                Statement whileStmt = parseWhileStmt();
                return whileStmt;

            case RETURN_TOKEN:
                //create new return stmt
                Statement returnStmt = parseReturnStmt();
                return returnStmt;

            case NUM_TOKEN:
                currentToken = scan.getNextToken();
                //create new num expr
                Token thisToken = currentToken;
                Expression n = null;
                return /*n.createNumExpr(thisToken)*/ null;

            case PARANOPEN_TOKEN:
                currentToken = scan.getNextToken();
                //create new expression
                Expression e = parseExpression();
                return null;

            case ID_TOKEN:
                //currentToken = scan.getNextToken();
                //create new expression stmt
                Statement stmt = parseEStmt();
                return stmt;

            default:
                parseError();
                return null;
        }

    }

    private Statement parseEStmt() {

        Statement eStmt = new ExpressionStmt();

        if (currentToken.getTokenType() == Token.TokenType.NUM_TOKEN
                || currentToken.getTokenType() == Token.TokenType.PARANOPEN_TOKEN
                || currentToken.getTokenType() == Token.TokenType.ID_TOKEN) {
            Expression e = parseExpression();
            eStmt = new ExpressionStmt(e);
            return eStmt;
        }

        currentToken = scan.getNextToken();
        matchToken(TokenType.SEMICOLON_TOKEN);

        return eStmt;
    }

    private Statement parseIfStmt() {

        matchToken(Token.TokenType.IF_TOKEN);
        //currentToken = scan.getNextToken();
        matchToken(Token.TokenType.PARANOPEN_TOKEN);
        //currentToken = scan.getNextToken();
        Expression ifExpr = parseExpression();

        //currentToken = scan.getNextToken();
        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        //currentToken = scan.getNextToken();
        Statement thenStmt = parseStmt();

        Statement elseStmt = null;
        //check for else
        if (scan.viewNextToken().getTokenType() == Token.TokenType.ELSE_TOKEN) {
            //nextToken
            currentToken = scan.getNextToken();
            elseStmt = parseStmt();
        }
        Statement ifStmt = new IfStmt(ifExpr, thenStmt, elseStmt);
        return ifStmt;
    }

    private Statement parseWhileStmt() {
        matchToken(Token.TokenType.WHILE_TOKEN);
        //currentToken = scan.getNextToken();
        matchToken(Token.TokenType.PARANOPEN_TOKEN);

        Expression whExpr = parseExpression();

        currentToken = scan.getNextToken();
        matchToken(Token.TokenType.PARANCLOSE_TOKEN);

        Statement thenStmt = parseStmt();

        Statement whStmt = new WhileStmt(whExpr, thenStmt);

        return whStmt;
    }

    private Statement parseReturnStmt() {
        matchToken(Token.TokenType.RETURN_TOKEN);
        
        Expression rExpr = parseExpression();
        Statement returnStmt = new ReturnStmt(rExpr);
        return returnStmt;
    }

    private Expression parseExpression() {
        switch (currentToken.getTokenType()) {
            case NUM_TOKEN:
                Token thisToken = currentToken;
                NumExpression n = new NumExpression(thisToken.getTokenData());
                Expression se = parseSimpleExpressionPrime(n);
                return se;

            case PARANOPEN_TOKEN:
                currentToken = scan.getNextToken();
                Expression e = parseExpression();
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.PARANCLOSE_TOKEN);
                se = parseSimpleExpressionPrime(e);
                //create Expr

                return se; // e needs to be a combination expr of e and se

            case ID_TOKEN:
                IDExpression i = new IDExpression(currentToken.getTokenData());
                currentToken = scan.getNextToken();
                Expression ep = parseExpressionPrime(i);
                // return Assign Expr
                return ep;

            default:
                parseError();
                return null;
        }
    }

    private Expression parseExpressionPrime(Expression ex) {
        Expression se = null;
        switch (currentToken.getTokenType()) {
            case EQUAL_TOKEN:
                // this an assign expr 
                currentToken = scan.getNextToken();
                //need to save this operator
                operator = 1;
                Expression e = parseExpression();
                AssignExpression a = new AssignExpression(ex, e);
                return a;

            case BRACKETOPEN_TOKEN:
                currentToken = scan.getNextToken();
                e = parseExpression();
                currentToken = scan.getNextToken();
                matchToken(Token.TokenType.BRACKETCLOSE_TOKEN);
                Expression ep = parseExpressionPrimePrime(e);
                //combine ep and ee
                return ep;

            case PARANOPEN_TOKEN:
                // new args
                Expression args = parseArgs();
                matchToken(Token.TokenType.PARANCLOSE_TOKEN);
                return args;
                
            case PLUS_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case MINUS_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case MULTIPLY_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case DIVIDE_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case LESSTHANEQUAL_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case LESSTHAN_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case GREATERTHANEQUAL_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case GREATERTHAN_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case EQUALEQUAL_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;
            case NOTEQUAL_TOKEN:
                se = parseSimpleExpressionPrime(ex);
                return se;

            default:
                parseError();
                return null;
        }

    }

    private Expression parseExpressionPrimePrime(Expression ee) {
        if (scan.viewNextToken().getTokenType() == Token.TokenType.EQUAL_TOKEN) {
            matchToken(Token.TokenType.EQUAL_TOKEN);
            Expression e = parseExpression();
            //combine ee and e
            return e;
        } else {
            Expression se = parseSimpleExpressionPrime(ee);
            return se;
        }

    }

    private Expression parseSimpleExpressionPrime(Expression ex) {

        Expression aepExpr = parseAdditiveEPrime(ex);

        currentToken = scan.getNextToken();
        //check for AE
        if (isRelop(currentToken.getTokenType())
                || isAddop(currentToken.getTokenType())
                || isMulop(currentToken.getTokenType())) {
            operator = currentToken.getTokenData();
            currentToken = scan.getNextToken();
            Expression aeExpr = parseAdditiveE(ex);
            
            aepExpr = new BinaryExpression(operator, aepExpr, aeExpr);
        }
        //SimpleExpressionPrime
        return aepExpr;

    }

    private Expression parseAdditiveE(Expression ex) {
        Expression lhs = parseTerm(ex);
        currentToken = scan.getNextToken();
        while (isAddop((currentToken.getTokenType()))) {
            operator = currentToken.getTokenData();
            currentToken = scan.getNextToken();
            Expression rhs = parseTerm(ex);

            lhs = new BinaryExpression(operator, lhs, rhs);
            
        }
        return lhs;

    }

    private Expression parseAdditiveEPrime(Expression ex) {
        Expression lhs = parseTermPrime(ex);
        currentToken = scan.getNextToken();
        while (isAddop((currentToken.getTokenType()))) {
            operator = currentToken.getTokenData();
            currentToken = scan.getNextToken();
            Expression rhs = parseTerm(ex);

            lhs = new BinaryExpression(operator, lhs, rhs);
            
        }
        return lhs;

    }

    private Expression parseTerm(Expression ex) {
        Expression lhs = parseFactor(ex);

        while (isMulop(currentToken.getTokenType())) {
            operator = currentToken.getTokenData();
            Expression rhs = parseFactor(ex);
            lhs = new BinaryExpression(operator, lhs, rhs);
        }

        return lhs;
    }

    private Expression parseTermPrime(Expression ex) {
        while (isMulop(currentToken.getTokenType())) {
            operator = currentToken.getTokenData();
            currentToken = scan.getNextToken();
            Expression rhs = parseFactor(ex); // parse factor is returning a instead of finding b
            BinaryExpression b = new BinaryExpression(operator, ex, rhs);
            return b;
        } 
            
        return ex;
        

    }

    private Expression parseFactor(Expression ex) {
        switch (currentToken.getTokenType()) {
            case PARANOPEN_TOKEN:
                currentToken = scan.getNextToken();
                Expression returnE = parseExpression();
                matchToken(TokenType.PARANCLOSE_TOKEN);
                return returnE;

            case NUM_TOKEN:
                currentToken = scan.getNextToken();
                Token thisToken = currentToken;
                Expression n = null;
                return n.createNumExpr(thisToken);

            case BRACKETOPEN_TOKEN:
                currentToken = scan.getNextToken();
                Expression varE = parseVarCall(ex);
                return varE;
            case ID_TOKEN:
                IDExpression i = new IDExpression(currentToken.getTokenData());
                Expression vc = parseVarCall(i);
                return vc;

            default:
                parseError();
                return null;
        }

    }

    private Expression parseVarCall(Expression ex) {
        if (currentToken.getTokenType() == Token.TokenType.BRACKETOPEN_TOKEN) {
            currentToken = scan.getNextToken();
            Expression e = parseExpression();

            currentToken = scan.getNextToken();
            matchToken(Token.TokenType.BRACKETCLOSE_TOKEN);
            return e;
        } else if (currentToken.getTokenType() == Token.TokenType.PARANOPEN_TOKEN) {
            currentToken = scan.getNextToken();
            Expression arg = parseArgs();
            return arg;
        } else if (currentToken.getTokenType() != Token.TokenType.BRACKETOPEN_TOKEN
                || currentToken.getTokenType() != Token.TokenType.PARANOPEN_TOKEN) {
            return ex;
        } // Check for Follow Sets due to Epsilon
        //TODO: set up follow set enums for varcall, or structure this as a big if statement
        else {
            parseError();
            return null;
        }

    }

    private Expression parseArgs() {
        // Expression arg = new AssignExpression();

        if (scan.viewNextToken().getTokenType() == Token.TokenType.NUM_TOKEN
                || scan.viewNextToken().getTokenType() == Token.TokenType.PARANOPEN_TOKEN
                || scan.viewNextToken().getTokenType() == Token.TokenType.ID_TOKEN //|| or epsilon?
                ) {
            Expression e = parseExpression();
            // add to AST/ argE
            while (scan.viewNextToken().getTokenType() == Token.TokenType.COMMA_TOKEN) {
                currentToken = scan.getNextToken();
                e = parseExpression();
                // add to AST/argE
            }

        } //Follow Set
        else if (scan.viewNextToken().getTokenType() == Token.TokenType.PARANCLOSE_TOKEN) {
            //add to ast/argE
        } else {
            parseError();
            return null;
        }
        return arg;
    }

    //main method
    public static void main(String args[]) throws IOException {
        BufferedReader br = null;
        // Read c file into scanner (need to adjust this path name)
        br = new BufferedReader(new FileReader("/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/test.c"));
        String filename = "/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/output.ast";
        BufferedWriter w = new BufferedWriter(new FileWriter(filename));

        // read in scanner output to parser          
        Parser myParser = new CMinusParser(br);
        myProgram = myParser.parse();
        myProgram.printTree(w);
        w.close();

    }
}
