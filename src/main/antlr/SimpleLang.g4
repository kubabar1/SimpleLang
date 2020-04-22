grammar SimpleLang;

/*
 * Parser Rules
 */

program: line* EOF;

line: statement ';'+;

statement
    : assignment
    | functionInvocation
    ;

assignment : VariableName '=' literal;

functionInvocation: buildInFunctions '(' (literal | VariableName) ')';

literal
	: IntegerLiteral
	| FloatingPointLiteral
	| BooleanLiteral
	| StringLiteral
	| NullLiteral
	;

buildInFunctions
    : PRINT
    | READ
    ;

/*
 * Lexer Rules
 */

// Keywords
PRINT: 'print';
READ: 'read';


// Separators
CBRACKETOPEN: '{';
CBRACKETCLOSE: '}';
SBRACKETOPEN: '[';
SBRACKETCLOSE: ']';
RBRACKETOPEN: '(';
RBRACKETCLOSE: ')';
SEMICOLON : ';';
COMMA : ',';
DOT : '.';


// Operators
ASSIGN: '=';
MULTIPLY: '*';
PLUS: '+';
MINUS: '-';
DIVIDE: '/';


// Literals
FloatingPointLiteral: PlusMinus? Digit+ DOT Digit+;

IntegerLiteral: PlusMinus? Digit+;

BooleanLiteral
    : 'true'
    | 'false'
    ;

StringLiteral: '"' LetterOrDigit '"'; // TODO

NullLiteral: 'null';


// Foundation
fragment PlusMinus: (PLUS | MINUS);

fragment Digit: [0-9];

fragment Letter: [a-zA-Z];

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

VariableName: Letter LetterOrDigit*; // TODO

NEWLINE : ('\r'? '\n' | '\r')+ -> skip;

WHITESPACE : [ \t\u000C] -> skip;