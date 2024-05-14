lexer grammar pinsLexer;

ARR: 'arr';
ELSE: 'else';
FOR: 'for';
FUN: 'fun';
IF: 'if';
THEN: 'then';
TYP: 'typ';
VAR: 'var';
WHERE: 'where';
WHILE: 'while';
DECLARE: 'declare';

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';
OP_MOD: '%';

OP_AND: '&';
OP_OR: '|';
OP_NOT: '!';

OP_EQ: '==';
OP_NEQ: '!=';
OP_LT: '<';
OP_GT: '>';
OP_LEQ: '<=';
OP_GEQ: '>=';

OP_LPARENT: '(';
OP_RPARENT: ')';
OP_LBRACKET: '[';
OP_RBRACKET: ']';
OP_LBRACE: '{';
OP_RBRACE: '}';

OP_COLON: ':';
OP_SEMICOLON: ';';
OP_COMMA: ',';
OP_ASSIGN: '=';
OP_VARARG: '...';

C_INTEGER: [0-9]+;
C_LOGICAL: 'true' | 'false';
C_STRING: '\'' (~'\'' | '\'\'' | ' ')* '\'';

AT_LOGICAL: 'logical';
AT_INTEGER: 'integer';
AT_STRING: 'string';

IDENTIFIER: [a-zA-Z_]+ [a-zA-Z0-9_]*;
COMMENT: '#' ~[\r\n]* -> skip;
WS: [ \t\n\r\f]+ -> skip;