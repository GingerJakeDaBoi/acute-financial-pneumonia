package org.gingerpipe.afp;

import java.util.ArrayList;

public class Base {

    public static final String DIGITS = "0123456789";

    public static class Error {
        public String name;
        public String details;

        public Error(String name, String details) {
            this.name = name;
            this.details = details;
        }

        public String asString() {
            return this.name + ": " + this.details;
        }
    }

    public static class IllegalCharacterError extends Error {

        public IllegalCharacterError(char details) {
            super("Illegal Character", Character.toString(details));
        }
    }

    public static String returnError(Error error) {
        return error.asString();
    }

    public static class Token {

        public String value;

        public Token(Lexer.Token type) {
            switch(type) {
                case TT_INT:
                    value = "INT";
                case TT_FLOAT:
                    value = "FLOAT";
                case TT_PLUS:
                    value = "PLUS";
                case TT_MINUS:
                    value = "MINUS";
                case TT_MUL:
                    value = "MUL";
                case TT_DIV:
                    value = "DIV";
                case TT_LPAREN:
                    value = "LPAREN";
                case TT_RPAREN:
                    value = "RPAREN";
                default:
                    value = null;
            }
        }

        public Token(Lexer.Token type, int value) {
            if (type == Lexer.Token.TT_INT) {
                this.value = String.valueOf(value);
            }
            this.value = null;
        }

        public Token(Lexer.Token type, float value) {
            if (type == Lexer.Token.TT_FLOAT) {
                this.value = String.valueOf(value);
            }
            this.value = null;
        }
    }

    public static class Lexer {

        public enum Token {
            TT_INT, TT_FLOAT, TT_PLUS, TT_MINUS, TT_MUL, TT_DIV, TT_LPAREN, TT_RPAREN
        }

        public int pos;
        public String text;
        public Character currentChar;

        public Lexer(String text) {
            this.text = text;
            this.pos = -1;
            advance();
        }

        public void advance() {
            this.pos++;
            this.currentChar = this.pos < this.text.length() ? this.text.charAt(this.pos) : null;
        }

        public ArrayList<Base.Token> makeTokens() {
            ArrayList<Base.Token> tokens = new ArrayList<>();

            while(this.currentChar != null) {
                if(Character.isWhitespace(this.currentChar)) {
                    advance();
                } else if(DIGITS.contains(this.currentChar.toString())) {
                    makeNumber();
                } else if(this.currentChar.equals('+')) {
                    tokens.add(new Base.Token(Token.TT_PLUS));
                    advance();
                } else if(this.currentChar.equals('-')) {
                    tokens.add(new Base.Token(Token.TT_MINUS));
                    advance();
                } else if(this.currentChar.equals('*')) {
                    tokens.add(new Base.Token(Token.TT_MUL));
                    advance();
                } else if(this.currentChar.equals('/')) {
                    tokens.add(new Base.Token(Token.TT_DIV));
                    advance();
                } else if(this.currentChar.equals('(')) {
                    tokens.add(new Base.Token(Token.TT_LPAREN));
                    advance();
                } else if(this.currentChar.equals(')')) {
                    tokens.add(new Base.Token(Token.TT_RPAREN));
                    advance();
                } else {
                    char Char = this.currentChar;
                    advance();
                    System.out.println(returnError(new IllegalCharacterError(Char)));
                    return null;
                }
            }

            return tokens;
        }

        public Base.Token makeNumber() {
            String number = "";
            int dots = 0;
            while(this.currentChar != null && (DIGITS.contains(this.currentChar.toString()) || this.currentChar.equals('.'))) {
                if(this.currentChar.equals('.')) {
                    if(dots == 1) break;
                    dots++;
                    number += '.';
                } else {
                    number += this.currentChar;
                }
                this.advance();

                if(dots == 0) {
                    return new Base.Token(Token.TT_INT, Integer.parseInt(number));
                } else {
                    return new Base.Token(Token.TT_FLOAT, Float.parseFloat(number));
                }
            }
            return null;
        }
    }

    public static ArrayList<Base.Token> run(String text) {
        Lexer lexer = new Lexer(text);
        ArrayList<Base.Token> tokens = lexer.makeTokens();

        return tokens;
    }
}
