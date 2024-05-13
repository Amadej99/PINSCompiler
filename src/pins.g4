grammar pins;

KW_ARR : 'arr' ;
KW_ELSE : 'else' ;
KW_FOR : 'for' ;
KW_FUN : 'fun' ;
KW_IF : 'if' ;
KW_THEN : 'then' ;
KW_TYP : 'typ' ;
KW_VAR : 'var' ;
KW_WHERE : 'where' ;
KW_WHILE : 'while' ;
KW_DECLARE : 'declare' ;

OP_ADD : '+' ;
OP_SUB : '-' ;
OP_MUL : '*' ;
OP_DIV : '/' ;
OP_MOD : '%' ;

OP_AND : '&' ;
OP_OR : '|' ;
OP_NOT : '!' ;

OP_EQ : '==' ;
OP_NEQ : '!=' ;
OP_LT : '<' ;
OP_GT : '>' ;
OP_LEQ : '<=' ;
OP_GEQ : '>=' ;

OP_LPARENT : '(' ;
OP_RPARENT : ')' ;
OP_LBRACKET : '[' ;
OP_RBRACKET : ']' ;
OP_LBRACE : '{' ;
OP_RBRACE : '}' ;

OP_COLON : ':' ;
OP_SEMICOLON : ';' ;
OP_COMMA : ',' ;
OP_ASSIGN : '=' ;
OP_VARARG : '...' ;

C_INTEGER : [0-9]+ ;
C_LOGICAL : 'true' | 'false';
C_STRING : '\'' (~'\'' | '\'\'' | ' ')* '\'';

AT_LOGICAL : 'logical';
AT_INTEGER : 'integer';
AT_STRING : 'string';

IDENTIFIER : [a-zA-Z_]+[a-zA-Z0-9_]* ;
COMMENT : '#' ~[\r\n]* -> skip ;
WS : [ \t\n\r\f]+ -> skip ;

program : definitions EOF;

definitions : definition definitions2;

definitions2 : ';' definitions
             |;

definition : function_definition
           | function_declaration
           | type_definition
           | variable_definition;

type_definition : typ identifier ':' type;

variable_definition : var identifier ':' type;

function_definition : fun identifier '(' parameters ')' ':' type '=' expression;

function_declaration : declare identifier '(' parameters ')' ':' type;

type : identifier
     | logical
     | integer
     | string
     | arr '[' int_constant ']' type;

parameters : parameter parameters2;

parameters2 : ',' parameters
            |;

parameter : identifier ':' type
          | '...'
          |;

expression : logical_ior_expression expression2;

expression2 : '{' 'WHERE' definitions '}'
            |;

logical_ior_expression : logical_and_expression logical_ior_expression2;

logical_ior_expression2 : '|' logical_and_expression logical_ior_expression2
                        |;

logical_and_expression : compare_expression logical_and_expression2;

logical_and_expression2 : '&' compare_expression logical_and_expression2
                        |;

compare_expression : additive_expression compare_expression2;

compare_expression2 : '==' additive_expression
                    | '!=' additive_expression
                    | '<=' additive_expression
                    | '>=' additive_expression
                    | '<' additive_expression
                    | '>' additive_expression
                    |;

additive_expression : multiplicative_expression additive_expression2;

additive_expression2 : '+' multiplicative_expression additive_expression2
                     | '-' multiplicative_expression additive_expression2
                     |;

multiplicative_expression : prefix_expression multiplicative_expression2;

multiplicative_expression2 : '*' prefix_expression multiplicative_expression2
                            | '/' prefix_expression multiplicative_expression2
                            | '%' prefix_expression multiplicative_expression2
                            |;

prefix_expression : '+' prefix_expression
                  | '-' prefix_expression
                  | '!' prefix_expression
                  | postfix_expression;

postfix_expression : atom_expression postfix_expression2;

postfix_expression2 : '[' expression ']' postfix_expression2
                    |;

atom_expression : log_constant
                | int_constant
                | str_constant
                | identifier atom_expression2
                | '(' expressions ')'
                | '{' atom_expression4;

atom_expression2 : '('')'
                 | '(' expressions ')'
                 |;

atom_expression3 : 'else' expression '}'
                 |;

atom_expression4 : expression '=' expression '}'
                 | 'if' expression 'then' expression atom_expression3
                 | 'while' expression ':' expression '}'
                 | 'for' identifier '=' expression ',' expression ',' expression ':' expression '}'
                 ;

expressions : expression expressions2;

expressions2 : ',' expressions
             |;

integer : AT_INTEGER;
logical : AT_LOGICAL;
string : AT_STRING;

int_constant : C_INTEGER;
log_constant : C_LOGICAL;
str_constant : C_STRING;

identifier : IDENTIFIER;

fun : KW_FUN;
typ : KW_TYP;
var : KW_VAR;
arr : KW_ARR;
declare : KW_DECLARE;
