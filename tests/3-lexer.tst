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
10+20*30==1
500%-60!=15
300<500
1>=1
1<=1
0/10
x!=y
true==false
(10)[15]{80}
00 11  15
!expected:
[1:1-1:3] C_INTEGER:10
[1:3-1:4] OP_ADD:+
[1:4-1:6] C_INTEGER:20
[1:6-1:7] OP_MUL:*
[1:7-1:9] C_INTEGER:30
[1:9-1:11] OP_EQ:==
[1:11-1:12] C_INTEGER:1
[2:1-2:4] C_INTEGER:500
[2:4-2:5] OP_MOD:%
[2:5-2:6] OP_SUB:-
[2:6-2:8] C_INTEGER:60
[2:8-2:10] OP_NEQ:!=
[2:10-2:12] C_INTEGER:15
[3:1-3:4] C_INTEGER:300
[3:4-3:5] OP_LT:<
[3:5-3:8] C_INTEGER:500
[4:1-4:2] C_INTEGER:1
[4:2-4:4] OP_GEQ:>=
[4:4-4:5] C_INTEGER:1
[5:1-5:2] C_INTEGER:1
[5:2-5:4] OP_LEQ:<=
[5:4-5:5] C_INTEGER:1
[6:1-6:2] C_INTEGER:0
[6:2-6:3] OP_DIV:/
[6:3-6:5] C_INTEGER:10
[7:1-7:2] IDENTIFIER:x
[7:2-7:4] OP_NEQ:!=
[7:4-7:5] IDENTIFIER:y
[8:1-8:5] C_LOGICAL:true
[8:5-8:7] OP_EQ:==
[8:7-8:12] C_LOGICAL:false
[9:1-9:2] OP_LPARENT:(
[9:2-9:4] C_INTEGER:10
[9:4-9:5] OP_RPARENT:)
[9:5-9:6] OP_LBRACKET:[
[9:6-9:8] C_INTEGER:15
[9:8-9:9] OP_RBRACKET:]
[9:9-9:10] OP_LBRACE:{
[9:10-9:12] C_INTEGER:80
[9:12-9:13] OP_RBRACE:}
[10:1-10:3] C_INTEGER:00
[10:4-10:6] C_INTEGER:11
[10:8-10:10] C_INTEGER:15
EOF:$
!end

!code:
#Imena
integer stevilo
logical isEmpty
string niz1
_ime1_
_____1234abc
x y z num length size
true1
1true
1 + 0
1ime
ime1
1string
string1
_string
!expected:
[2:1-2:8] AT_INTEGER:integer
[2:9-2:16] IDENTIFIER:stevilo
[3:1-3:8] AT_LOGICAL:logical
[3:9-3:16] IDENTIFIER:isEmpty
[4:1-4:7] AT_STRING:string
[4:8-4:12] IDENTIFIER:niz1
[5:1-5:7] IDENTIFIER:_ime1_
[6:1-6:13] IDENTIFIER:_____1234abc
[7:1-7:2] IDENTIFIER:x
[7:3-7:4] IDENTIFIER:y
[7:5-7:6] IDENTIFIER:z
[7:7-7:10] IDENTIFIER:num
[7:11-7:17] IDENTIFIER:length
[7:18-7:22] IDENTIFIER:size
[8:1-8:6] IDENTIFIER:true1
[9:1-9:2] C_INTEGER:1
[9:2-9:6] C_LOGICAL:true
[10:1-10:2] C_INTEGER:1
[10:3-10:4] OP_ADD:+
[10:5-10:6] C_INTEGER:0
[11:1-11:2] C_INTEGER:1
[11:2-11:5] IDENTIFIER:ime
[12:1-12:5] IDENTIFIER:ime1
[13:1-13:2] C_INTEGER:1
[13:2-13:8] AT_STRING:string
[14:1-14:8] IDENTIFIER:string1
[15:1-15:8] IDENTIFIER:_string
EOF:$
!end

!code:
#Test for string constants
'string'
'string with spaces'
'string with ''quotes'''
'string containg operators + - * / & % .. ,.'
'string containg keywords arr typ fun if for while'
''
'string containing double "quotes" '
'string1' 'string2'
'string1' #Komentar
'string1'
!expected:
[2:1-2:9] C_STRING:string
[3:1-3:21] C_STRING:string with spaces
[4:1-4:25] C_STRING:string with 'quotes'
[5:1-5:46] C_STRING:string containg operators + - * / & % .. ,.
[6:1-6:52] C_STRING:string containg keywords arr typ fun if for while
[7:1-7:3] C_STRING:
[8:1-8:37] C_STRING:string containing double "quotes" 
[9:1-9:10] C_STRING:string1
[9:11-9:20] C_STRING:string2
[10:1-10:10] C_STRING:string1
[11:1-11:10] C_STRING:string1
EOF:$
!end

!code:
#Testiranje tabulatorjev
	x
	y
	'string'
		true
		false
		logical
		integer
			fun
			var
			if
			name
!expected:
[2:5-2:6] IDENTIFIER:x
[3:5-3:6] IDENTIFIER:y
[4:5-4:13] C_STRING:string
[5:9-5:13] C_LOGICAL:true
[6:9-6:14] C_LOGICAL:false
[7:9-7:16] AT_LOGICAL:logical
[8:9-8:16] AT_INTEGER:integer
[9:13-9:16] KW_FUN:fun
[10:13-10:16] KW_VAR:var
[11:13-11:15] KW_IF:if
[12:13-12:17] IDENTIFIER:name
EOF:$
!end

!code:
	x
	 x
 	x
  	 	x
!expected:
[1:5-1:6] IDENTIFIER:x
[2:6-2:7] IDENTIFIER:x
[3:6-3:7] IDENTIFIER:x
[4:12-4:13] IDENTIFIER:x
EOF:$
!end