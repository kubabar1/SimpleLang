grammar SimpleLang;

/*
 * Parser Rules
 */

program
    : NEWLINE* ( statement )* EOF
    ;

statement
    : assignment NEWLINE*
    | functionInvocation NEWLINE*
    ;

assignment
    : VARIABLE_NAME ASSIGN (literal | expression)
    ;

expression
    : multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*
    ;

multiplyingExpression
    : powExpression ((MULTIPLY | DIVIDE) powExpression)*
    ;

powExpression
   : atomExpression (POW atomExpression)*
   ;

atomExpression
   : atom
   | (PLUS | MINUS) atom
   | functionInvocation
   ;

atom
    : numberLiteral
    | variable
    | constant
    | casting expression
    | LROUNDBRACKET expression RROUNDBRACKET
    ;

functionInvocation: buildInFunction LROUNDBRACKET (literal | expression) (COMMA (literal | expression))* RROUNDBRACKET;

variable: VARIABLE_NAME;

casting
    : toIntCasting
    | toFloatCasting
    ;

literal
	: numberLiteral
	| BooleanLiteral
	| StringLiteral
	| NullLiteral
	;

numberLiteral
   : FloatingPointLiteral
   | IntegerLiteral
   | ScientificNumberLiteral
   ;

constant
    : PI
    ;

toIntCasting: LROUNDBRACKET INT RROUNDBRACKET;

toFloatCasting: LROUNDBRACKET FLOAT RROUNDBRACKET;

buildInFunction
    : PRINT
    | READ
    | COS
    | SIN
    | TAN
    | CTG
    ;

// plus: PLUS;

// multiply: MULTIPLY;

/*
 * Lexer Rules
 */

// Keywords
PRINT: 'print';
READ: 'read';
COS: 'cos';
SIN: 'sin';
INT: 'int';
FLOAT: 'float';
TAN: 'tan';
CTG: 'ctg';


// Separators
LCURLYBRACKET: '{';
RCURLYBRACKET: '}';
LSQUAREBRACKET: '[';
RSQUAREBRACKET: ']';
LROUNDBRACKET: '(';
RROUNDBRACKET: ')';
SEMICOLON : ';';
COMMA : ',';
DOT : '.';


// Operators
ASSIGN: '=';
MULTIPLY: '*';
PLUS: '+';
MINUS: '-';
DIVIDE: '/';
POW: '^';


// Constants
PI: 'pi';
E: ('e' | 'E');


// Literals
FloatingPointLiteral: Digit+ DOT Digit+;

IntegerLiteral: Digit+;

ScientificNumberLiteral: E (PLUS | MINUS)? Digit+;

BooleanLiteral
    : 'true'
    | 'false'
    ;

StringLiteral: '"' ( ~('\\'|'"') )* '"';

NullLiteral: 'null';


// Base
fragment Digit: [0-9];

fragment Letter: [a-zA-Z];

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

VARIABLE_NAME: [_]*[A-Za-z][A-Za-z0-9_]*;

NEWLINE
    : '\r'? '\n'
    | '\r'
    ;

WHITESPACE : [ \t] -> skip;
