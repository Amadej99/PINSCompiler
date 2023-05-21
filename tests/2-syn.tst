!compiler_flags: --dump SYN --exec SYN

!code:
typ ; : integer
!failure:
99
!end

!code:
typ eh eh integer
!failure:
99
!end

!code:
typ id : arr h 15 ] integer
!failure:
99
!end

!code:
typ id : arr [ moja_mami ] integer
!failure:
99
!end

!code:
typ id : arr [ 15 h integer
!failure:
99
!end

!code:
typ id : 123
!failure:
99
!end

!code:
fun 15 (a:integer, b:integer) : integer = a + b
!failure:
99
!end

!code:
fun id lparent a:integer, b:integer) : integer = a + b
!failure:
99
!end

!code:
fun id (a:integer, b:integer rparent : integer = a + b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) colon integer = a + b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer assign a + b
!failure:
99
!end

!code:
fun id (123:integer, b:integer) : integer = a + b
!failure:
99
!end

!code:
fun id (a colon integer, b:integer) : integer = a + b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = 123 { wwhheerree typ id : integer }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = 123 { where typ id : integer rbrace
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = a + 10 [ 10 rbracket * b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = a + 10 [ { 1 = 1 rbrace ] * b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = a + 10 [ for ] * b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = a + 10 [ id ( 1 = 1 rparent ] * b
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { if a > b tthheenn a else b}
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { while a < b colon a = a + 1 }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { for 123 = 0, i < a, i = i + 1 : c = c + i * a * b }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { for i assign 0, i < a, i = i + 1 : c = c + i * a * b }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { for i = 0 comma i < a, i = i + 1 : c = c + i * a * b }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { for i = 0, i < a comma i = i + 1 : c = c + i * a * b }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { for i = 0, i < a, i = i + 1 colon c = c + i * a * b }
!failure:
99
!end

!code:
fun id (a:integer, b:integer) : integer = { 1 assign 1 }
!failure:
99
!end

!code:
var 123 : integer
!failure:
99
!end

!code:
var id colon integer
!failure:
99
!end