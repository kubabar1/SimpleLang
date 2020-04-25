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

assignment: VARIABLE_NAME ASSIGN (literal | expression);

expression
    : multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*
    ;

multiplyingExpression
    : powExpression ((MULTIPLY | DIVIDE) powExpression)*
    ;

powExpression
   : signedAtom (POW signedAtom)*
   ;

signedAtom
   : atom
   | functionInvocation
   | (PLUS | MINUS) signedAtom
   ;

atom
    : numberLiteral
    | variable
    | constant
    | TOINT expression
    | TOFLOAT expression
    | LROUNDBRACKET expression RROUNDBRACKET
    ;

constant
    : PI
    ;

numberLiteral
   : FloatingPointLiteral
   | IntegerLiteral
   | ScientificNumberLiteral
   ;

variable: VARIABLE_NAME;

functionInvocation: buildInFunction LROUNDBRACKET (literal | expression) (COMMA (literal | expression))* RROUNDBRACKET;

buildInFunction
    : PRINT
    | READ
    | COS
    | SIN
    ;

literal
	: IntegerLiteral
	| FloatingPointLiteral
	| ScientificNumberLiteral
	| BooleanLiteral
	| StringLiteral
	| NullLiteral
	;

// toIntCasting: TOINT;

// toFloatCasting: TOFLOAT;

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


// Foundation



fragment PlusMinus: (PLUS | MINUS);

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

TOINT: LROUNDBRACKET 'int' RROUNDBRACKET;

TOFLOAT: LROUNDBRACKET 'float' RROUNDBRACKET;

E: ('e' | 'E');

WHITESPACE : [ \t] -> skip;