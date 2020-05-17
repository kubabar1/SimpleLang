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
    | ifStatement NEWLINE*
    ;

assignment
    : VARIABLE_NAME ASSIGN ( expression | literal | array)
    ;

expression
    : multiplyingExpression
    | expression (add | subtract) expression
    ;

multiplyingExpression
    : powExpression
    | multiplyingExpression (multiply | divide) multiplyingExpression
    ;

powExpression
   : atomExpression
   | powExpression pow powExpression
   ;

atomExpression
   : atom
   | functionInvocation
   ;
//  | (PLUS | MINUS) atom

atom
    : numberLiteral
    | variable
    | constant
    | casting expression
    | LROUNDBRACKET expression RROUNDBRACKET
    ;

block: ( statement? NEWLINE )*;

functionInvocation: buildInFunction LROUNDBRACKET (literal | variable | expression) (COMMA (literal | variable | expression))* RROUNDBRACKET;

ifStatement: IF LROUNDBRACKET condition RROUNDBRACKET NEWLINE* LCURLYBRACKET blockif RCURLYBRACKET;

blockif: block;

condition
    : BooleanLiteral
    | variable
    | leftStatement comparisonOperators rightStatement
    ;

leftStatement: comparisonStatement;

rightStatement: comparisonStatement;

comparisonStatement
    : numberLiteral
    | BooleanLiteral
    | variable
    ;

comparisonOperators
    : EQUAL
    | NOT_EQUAL
    | GREATER_OR_EQUAL_THAN
    | GREATER_THAN
    | LESS_OR_EQUAL_THAN
    | LESS_THAN
    ;

variable: VARIABLE_NAME;

array: LSQUAREBRACKET (literal (COMMA literal)*)? RSQUAREBRACKET;

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
    | SIN
    | COS
    | TAN
    | CTG
    ;

add: PLUS;

subtract: MINUS;

multiply: MULTIPLY;

divide: DIVIDE;

pow: POW;

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
IF: 'if';


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


// Comparison operators
EQUAL: '==';
NOT_EQUAL: '!=';
GREATER_THAN: '>';
GREATER_OR_EQUAL_THAN: '>=';
LESS_THAN: '<';
LESS_OR_EQUAL_THAN: '<=';


// Constants
PI: 'pi';


// Literals
FloatingPointLiteral: Digit+ DOT Digit+;

IntegerLiteral: Digit+;

ScientificNumberLiteral: ('e' | 'E') (PLUS | MINUS)? Digit+;

BooleanLiteral
    : 'true'
    | 'false'
    ;

StringLiteral: '"' StringText '"';

NullLiteral: 'null';


// Base
fragment Digit: [0-9];

fragment Letter: [a-zA-Z];

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment StringText: ( ~('\\'|'"') )*;

VARIABLE_NAME: [_]*[A-Za-z][A-Za-z0-9_]*;

NEWLINE
    : '\r'? '\n'
    | '\r'
    ;

WHITESPACE : [ \t] -> skip;
