grammar SimpleLang;

/*
 * Lexer Rules
 */

fragment LOWERCASE  : [a-z];
fragment UPPERCASE  : [A-Z];
fragment LETTER     : [A-Za-z];
fragment DIGIT      : [0-9];

PRINT               : 'print';

REAL                : DIGIT+ ([.] DIGIT+);

INT                 : DIGIT+;

VARIABLE            : (LOWERCASE | UPPERCASE)+;

WHITESPACE          : (' ' | '\t') -> skip;

NEWLINE             : ('\r'? '\n' | '\r')+  -> skip;


/*
 * Parser Rules
 */

statement           : assignment | print;

assignment          : VARIABLE '=' (INT | REAL);

print               : PRINT '(' (INT | REAL | VARIABLE)* ')';