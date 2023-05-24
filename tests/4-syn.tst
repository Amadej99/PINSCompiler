!compiler_flags: --dump SYN --exec SYN

!code:
var x : integer;
!failure:
99
!end

!code:
fun f () : logical = false
!failure:
99
!end

!code:
fun f(x:integer,) : logical = false
!failure:
99
!end

!code:
fun f(x:integer, y:integer) : integer x + y;
!failure:
99
!end

!code:
typ x : arr [x] string
!failure:
99
!end

!code:
typ int : integer
fun f(y:int) : int = 100 + (10 * f())
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    { where var x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    10 { where var x : log; }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    10 { where x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    10 { where var x ; log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    { if x == 10 then 1 else }, x { where var x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    10, 
)
!failure:
99
!end

!code:
fun f(y:int) : log =
()
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    { where1 var x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    x { where var x : log 
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    { for x = 10, x < 10 , { x = x + 1 } , f(10) }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    { while x < 10 ; 5 }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    x { where var x : log }
)
var x : integer
!failure:
99
!end

!code:
fun f(y:int) ; log =
(
    x { where var x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    x { where var x : log }

!failure:
99
!end

!code:
fun f(y:int) : log =
(
    x- { where var x : log }
)
!failure:
99
!end

!code:
fun f(y:int) : log =
(
    x[] { where var x : log }
)
!failure:
99
!end

!code:
var x : integer;
typ y : arr {}
!failure:
99
!end

!code:
fun f(x:int) : logical = f1(x, )
!failure:
99
!end

!code:
fun f(x:str) : string = 
f2(a), f3(b)
!failure:
99
!end

!code:
f : integer
!failure:
99
!end

!code:
var 100 : logical
!failure:
99
!end

!code:
var 'str' : logical
!failure:
99
!end

!code:
var x : 'str'
!failure:
99
!end

!code:
fun z (z : integer) : integer = 
(
    { for x = 0, x < 10, { x = x[10] + 1 } : { z = z % x } },
    { if x == 10 then { y = 0 } else { y = 1 } },
    { while x != 0 : { x = x  1 } }
)
!failure:
99
!end

!code:
fun z (z : integer) : integer = 
(
    { for x = 0, x < 10, { x = x[10] + 1 } : { z = z % x } },
    { if x == 10 then { y = 0 } else { y < 1 } },
    { while x != 0 : { x = x / 1 } }
)
!failure:
99
!end