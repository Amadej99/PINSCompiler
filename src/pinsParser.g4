parser grammar pinsParser;

options {
	tokenVocab = pinsLexer;
}

program: definitions EOF;

definitions: definition definitions2;

definitions2: ';' definitions |;

definition:
	function_definition
	| function_declaration
	| type_definition
	| variable_definition;

type_definition: TYP IDENTIFIER ':' type;

variable_definition: VAR IDENTIFIER ':' type;

function_definition:
	FUN IDENTIFIER '(' parameters ')' ':' type '=' expression;

function_declaration:
	DECLARE IDENTIFIER '(' parameters ')' ':' type;

type:
	IDENTIFIER
	| AT_LOGICAL
	| AT_INTEGER
	| AT_STRING
	| ARR '[' C_INTEGER ']' type;

parameters: parameter parameters2;

parameters2: ',' parameters |;

parameter: IDENTIFIER ':' type | '...' |;

expression: logical_ior_expression expression2;

expression2: '{' WHERE definitions '}' |;

logical_ior_expression:
	logical_and_expression logical_ior_expression2;

logical_ior_expression2: '|' logical_ior_expression |;

logical_and_expression:
	compare_expression logical_and_expression2;

logical_and_expression2: '&' logical_and_expression |;

compare_expression: additive_expression compare_expression2;

compare_expression2:
	op = (OP_EQ | OP_NEQ | OP_LEQ | OP_GEQ | OP_LT | OP_GT) additive_expression
	|;

additive_expression:
	multiplicative_expression additive_expression2;

additive_expression2:
	op = (OP_ADD | OP_SUB) additive_expression
	|;

multiplicative_expression:
	prefix_expression multiplicative_expression2;

multiplicative_expression2:
	op = (OP_MUL | OP_DIV | OP_MOD) multiplicative_expression
	|;

prefix_expression:
	op = (OP_ADD | OP_SUB | OP_NOT) prefix_expression
	| postfix_expression;

postfix_expression: atom_expression postfix_expression2;

postfix_expression2: '[' expression ']' postfix_expression2 |;

atom_expression:
	C_LOGICAL
	| C_INTEGER
	| C_STRING
	| IDENTIFIER atom_expression2
	| '(' expressions ')'
	| '{' atom_expression4;

atom_expression2: '(' ')' | '(' expressions ')' |;

atom_expression3: ELSE expression '}' |;

atom_expression4:
	expression '=' expression '}'
	| IF expression THEN expression atom_expression3
	| WHILE expression ':' expression '}'
	| FOR IDENTIFIER '=' expression ',' expression ',' expression ':' expression '}';

expressions: expression expressions2;

expressions2: ',' expressions |;