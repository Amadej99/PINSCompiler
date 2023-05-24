!compiler_flags: --dump LEX --exec LEX
-- prva vrstica v datoteki je namenjena
-- konfiguraciji prevajalnika

-- -------------------------------------

-- struktura 'delujočega' testa
-- !name: -- značka !name ni obvezna
-- !code:
-- 
-- !expected:
-- 
-- !end

-- struktura 'nedelujočega' testa
-- !name:
-- !code:
-- 
-- !failure:
-- 99 -- izhodna koda programa
-- !end

-- ------------------------------------

-- 'javni testi' za leksikalno analizo

!code:
10
!expected:
[1:1-1:3] C_INTEGER:10
EOF:$
!end

!code:
'string'
!expected:
[1:1-1:9] C_STRING:string
EOF:$
!end

!code:
true
!expected:
[1:1-1:5] C_LOGICAL:true
EOF:$
!end

!code:
false
!expected:
[1:1-1:6] C_LOGICAL:false
EOF:$
!end

!code:
arr
!expected:
[1:1-1:4] KW_ARR:arr
EOF:$
!end

!code:
else
!expected:
[1:1-1:5] KW_ELSE:else
EOF:$
!end

!code:
for
!expected:
[1:1-1:4] KW_FOR:for
EOF:$
!end

!code:
fun
!expected:
[1:1-1:4] KW_FUN:fun
EOF:$
!end

!code:
if
!expected:
[1:1-1:3] KW_IF:if
EOF:$
!end

!code:
then
!expected:
[1:1-1:5] KW_THEN:then
EOF:$
!end

!code:
typ
!expected:
[1:1-1:4] KW_TYP:typ
EOF:$
!end

!code:
var
!expected:
[1:1-1:4] KW_VAR:var
EOF:$
!end

!code:
while
!expected:
[1:1-1:6] KW_WHILE:while
EOF:$
!end

!code:
where
!expected:
[1:1-1:6] KW_WHERE:where
EOF:$
!end

!code:
logical
!expected:
[1:1-1:8] AT_LOGICAL:logical
EOF:$
!end

!code:
integer
!expected:
[1:1-1:8] AT_INTEGER:integer
EOF:$
!end

!code:
string
!expected:
[1:1-1:7] AT_STRING:string
EOF:$
!end

!code:
+
!expected:
[1:1-1:2] OP_ADD:+
EOF:$
!end

!code:
-
!expected:
[1:1-1:2] OP_SUB:-
EOF:$
!end

!code:
*
!expected:
[1:1-1:2] OP_MUL:*
EOF:$
!end

!code:
/
!expected:
[1:1-1:2] OP_DIV:/
EOF:$
!end

!code:
%
!expected:
[1:1-1:2] OP_MOD:%
EOF:$
!end

!code:
&
!expected:
[1:1-1:2] OP_AND:&
EOF:$
!end

!code:
|
!expected:
[1:1-1:2] OP_OR:|
EOF:$
!end

!code:
!
!expected:
[1:1-1:2] OP_NOT:!
EOF:$
!end

!code:
==
!expected:
[1:1-1:3] OP_EQ:==
EOF:$
!end

!code:
!=
!expected:
[1:1-1:3] OP_NEQ:!=
EOF:$
!end

!code:
<=
!expected:
[1:1-1:3] OP_LEQ:<=
EOF:$
!end

!code:
>=
!expected:
[1:1-1:3] OP_GEQ:>=
EOF:$
!end

!code:
<
!expected:
[1:1-1:2] OP_LT:<
EOF:$
!end

!code:
>
!expected:
[1:1-1:2] OP_GT:>
EOF:$
!end

!code:
(
!expected:
[1:1-1:2] OP_LPARENT:(
EOF:$
!end

!code:
)
!expected:
[1:1-1:2] OP_RPARENT:)
EOF:$
!end

!code:
[
!expected:
[1:1-1:2] OP_LBRACKET:[
EOF:$
!end

!code:
]
!expected:
[1:1-1:2] OP_RBRACKET:]
EOF:$
!end

!code:
{
!expected:
[1:1-1:2] OP_LBRACE:{
EOF:$
!end

!code:
}
!expected:
[1:1-1:2] OP_RBRACE:}
EOF:$
!end

!code:
:
!expected:
[1:1-1:2] OP_COLON::
EOF:$
!end

!code:
;
!expected:
[1:1-1:2] OP_SEMICOLON:;
EOF:$
!end

!code:
.
!expected:
[1:1-1:2] OP_DOT:.
EOF:$
!end

!code:
,
!expected:
[1:1-1:2] OP_COMMA:,
EOF:$
!end

!code:
=
!expected:
[1:1-1:2] OP_ASSIGN:=
EOF:$
!end