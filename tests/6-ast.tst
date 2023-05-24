!compiler_flags: --dump AST --exec AST

!code:
fun f (x:integer, y:logical) : string = 'abcd';
var x : string;
typ t : arr [10] string
!expected:
Defs [1:1-3:24]
  FunDef [1:1-1:47]: f
    Parameter [1:8-1:17]: x
      Atom [1:10-1:17]: INT
    Parameter [1:19-1:28]: y
      Atom [1:21-1:28]: LOG
    Atom [1:32-1:38]: STR
    Literal [1:41-1:47]: STR(abcd)
  VarDef [2:1-2:15]: x
    Atom [2:9-2:15]: STR
  TypeDef [3:1-3:24]: t
    Array [3:9-3:24]
      [10]
      Atom [3:18-3:24]: STR
!end

!code:
fun f(x:logical) : logical = 
(
    x == y { where var y : logical }
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: LOG
    Atom [1:20-1:27]: LOG
    Block [2:1-4:2]
      Where [3:5-3:37]
        Defs [3:20-3:35]
          VarDef [3:20-3:35]: y
            Atom [3:28-3:35]: LOG
        Binary [3:5-3:11]: EQ
          Name [3:5-3:6]: x
          Name [3:10-3:11]: y
!end

!code:
fun f(x:logical) : logical = 
(
    { for i = 0, x < 10, { x = x + 1 } : print(i) },
    true
)
!expected:
Defs [1:1-5:2]
  FunDef [1:1-5:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: LOG
    Atom [1:20-1:27]: LOG
    Block [2:1-5:2]
      For [3:5-3:52]
        Name [3:11-3:12]: i
        Literal [3:15-3:16]: INT(0)
        Binary [3:18-3:24]: LT
          Name [3:18-3:19]: x
          Literal [3:22-3:24]: INT(10)
        Binary [3:26-3:39]: ASSIGN
          Name [3:28-3:29]: x
          Binary [3:32-3:37]: ADD
            Name [3:32-3:33]: x
            Literal [3:36-3:37]: INT(1)
        Call [3:42-3:50]: print
          Name [3:48-3:49]: i
      Literal [4:5-4:9]: LOG(true)
!end

!code:
fun f(x:integer, y:integer, z:integer) : integer = 
(
    x + y + z * 10
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Parameter [1:18-1:27]: y
      Atom [1:20-1:27]: INT
    Parameter [1:29-1:38]: z
      Atom [1:31-1:38]: INT
    Atom [1:42-1:49]: INT
    Block [2:1-4:2]
      Binary [3:5-3:19]: ADD
        Binary [3:5-3:10]: ADD
          Name [3:5-3:6]: x
          Name [3:9-3:10]: y
        Binary [3:13-3:19]: MUL
          Name [3:13-3:14]: z
          Literal [3:17-3:19]: INT(10)
!end

!code:
fun f(x:integer, y:integer, z:integer) : integer = 
(
    x + y + z + 10
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Parameter [1:18-1:27]: y
      Atom [1:20-1:27]: INT
    Parameter [1:29-1:38]: z
      Atom [1:31-1:38]: INT
    Atom [1:42-1:49]: INT
    Block [2:1-4:2]
      Binary [3:5-3:19]: ADD
        Binary [3:5-3:14]: ADD
          Binary [3:5-3:10]: ADD
            Name [3:5-3:6]: x
            Name [3:9-3:10]: y
          Name [3:13-3:14]: z
        Literal [3:17-3:19]: INT(10)
!end

!code:
fun f(x:integer, y:integer, z:integer) : integer = 
(
    { while x < 10 : { x = x + 1 } },
    { if (x % 10 == 0) then true else false }
)
!expected:
Defs [1:1-5:2]
  FunDef [1:1-5:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Parameter [1:18-1:27]: y
      Atom [1:20-1:27]: INT
    Parameter [1:29-1:38]: z
      Atom [1:31-1:38]: INT
    Atom [1:42-1:49]: INT
    Block [2:1-5:2]
      While [3:5-3:37]
        Binary [3:13-3:19]: LT
          Name [3:13-3:14]: x
          Literal [3:17-3:19]: INT(10)
        Binary [3:22-3:35]: ASSIGN
          Name [3:24-3:25]: x
          Binary [3:28-3:33]: ADD
            Name [3:28-3:29]: x
            Literal [3:32-3:33]: INT(1)
      IfThenElse [4:5-4:46]
        Block [4:10-4:23]
          Binary [4:11-4:22]: EQ
            Binary [4:11-4:17]: MOD
              Name [4:11-4:12]: x
              Literal [4:15-4:17]: INT(10)
            Literal [4:21-4:22]: INT(0)
        Literal [4:29-4:33]: LOG(true)
        Literal [4:39-4:44]: LOG(false)
!end

!code:
fun f(x:integer, y:integer, z:integer) : integer = 
(
    { while x < 10 : { x = x + 1 } },
    { if (x % 10 == 0) then true else false },
    (x + y * 10 * 15 - z * w) { where var x : integer }
)
!expected:
Defs [1:1-6:2]
  FunDef [1:1-6:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Parameter [1:18-1:27]: y
      Atom [1:20-1:27]: INT
    Parameter [1:29-1:38]: z
      Atom [1:31-1:38]: INT
    Atom [1:42-1:49]: INT
    Block [2:1-6:2]
      While [3:5-3:37]
        Binary [3:13-3:19]: LT
          Name [3:13-3:14]: x
          Literal [3:17-3:19]: INT(10)
        Binary [3:22-3:35]: ASSIGN
          Name [3:24-3:25]: x
          Binary [3:28-3:33]: ADD
            Name [3:28-3:29]: x
            Literal [3:32-3:33]: INT(1)
      IfThenElse [4:5-4:46]
        Block [4:10-4:23]
          Binary [4:11-4:22]: EQ
            Binary [4:11-4:17]: MOD
              Name [4:11-4:12]: x
              Literal [4:15-4:17]: INT(10)
            Literal [4:21-4:22]: INT(0)
        Literal [4:29-4:33]: LOG(true)
        Literal [4:39-4:44]: LOG(false)
      Where [5:5-5:56]
        Defs [5:39-5:54]
          VarDef [5:39-5:54]: x
            Atom [5:47-5:54]: INT
        Block [5:5-5:30]
          Binary [5:6-5:29]: SUB
            Binary [5:6-5:21]: ADD
              Name [5:6-5:7]: x
              Binary [5:10-5:21]: MUL
                Binary [5:10-5:16]: MUL
                  Name [5:10-5:11]: y
                  Literal [5:14-5:16]: INT(10)
                Literal [5:19-5:21]: INT(15)
            Binary [5:24-5:29]: MUL
              Name [5:24-5:25]: z
              Name [5:28-5:29]: w
!end

!code:
fun f(x:integer) : integer = g(x-1);
fun g(x:integer) : integer = f(x * 2 - 1)
!expected:
Defs [1:1-2:42]
  FunDef [1:1-1:36]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Atom [1:20-1:27]: INT
    Call [1:30-1:36]: g
      Binary [1:32-1:35]: SUB
        Name [1:32-1:33]: x
        Literal [1:34-1:35]: INT(1)
  FunDef [2:1-2:42]: g
    Parameter [2:7-2:16]: x
      Atom [2:9-2:16]: INT
    Atom [2:20-2:27]: INT
    Call [2:30-2:42]: f
      Binary [2:32-2:41]: SUB
        Binary [2:32-2:37]: MUL
          Name [2:32-2:33]: x
          Literal [2:36-2:37]: INT(2)
        Literal [2:40-2:41]: INT(1)
!end

!code:
fun f(x:integer) : integer = g(x-1) {where fun g(x:integer) : integer = f(x * 2 - 1)}
!expected:
Defs [1:1-1:86]
  FunDef [1:1-1:86]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Atom [1:20-1:27]: INT
    Where [1:30-1:86]
      Defs [1:44-1:85]
        FunDef [1:44-1:85]: g
          Parameter [1:50-1:59]: x
            Atom [1:52-1:59]: INT
          Atom [1:63-1:70]: INT
          Call [1:73-1:85]: f
            Binary [1:75-1:84]: SUB
              Binary [1:75-1:80]: MUL
                Name [1:75-1:76]: x
                Literal [1:79-1:80]: INT(2)
              Literal [1:83-1:84]: INT(1)
      Call [1:30-1:36]: g
        Binary [1:32-1:35]: SUB
          Name [1:32-1:33]: x
          Literal [1:34-1:35]: INT(1)
!end

!code:
fun f(x:integer) : integer = g(x-1) {where fun g(x:integer) : integer = f(x * y - 1){where var y : integer}}
!expected:
Defs [1:1-1:109]
  FunDef [1:1-1:109]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Atom [1:20-1:27]: INT
    Where [1:30-1:109]
      Defs [1:44-1:108]
        FunDef [1:44-1:108]: g
          Parameter [1:50-1:59]: x
            Atom [1:52-1:59]: INT
          Atom [1:63-1:70]: INT
          Where [1:73-1:108]
            Defs [1:92-1:107]
              VarDef [1:92-1:107]: y
                Atom [1:100-1:107]: INT
            Call [1:73-1:85]: f
              Binary [1:75-1:84]: SUB
                Binary [1:75-1:80]: MUL
                  Name [1:75-1:76]: x
                  Name [1:79-1:80]: y
                Literal [1:83-1:84]: INT(1)
      Call [1:30-1:36]: g
        Binary [1:32-1:35]: SUB
          Name [1:32-1:33]: x
          Literal [1:34-1:35]: INT(1)
!end

!code:
fun f(x:integer) : logical =
(
    { if x == 0 then 1 else 0 },
    { if x == 0 then 1 },
    { for x = 10, x > 0, { x = x - 1 } : printf('%d', x) } { where fun printf(str : string, x : integer) : logical = x },
    x | y & !z { where var y : logical; var z : logical }
)
!expected:
Defs [1:1-7:2]
  FunDef [1:1-7:2]: f
    Parameter [1:7-1:16]: x
      Atom [1:9-1:16]: INT
    Atom [1:20-1:27]: LOG
    Block [2:1-7:2]
      IfThenElse [3:5-3:32]
        Binary [3:10-3:16]: EQ
          Name [3:10-3:11]: x
          Literal [3:15-3:16]: INT(0)
        Literal [3:22-3:23]: INT(1)
        Literal [3:29-3:30]: INT(0)
      IfThenElse [4:5-4:25]
        Binary [4:10-4:16]: EQ
          Name [4:10-4:11]: x
          Literal [4:15-4:16]: INT(0)
        Literal [4:22-4:23]: INT(1)
      Where [5:5-5:121]
        Defs [5:68-5:119]
          FunDef [5:68-5:119]: printf
            Parameter [5:79-5:91]: str
              Atom [5:85-5:91]: STR
            Parameter [5:93-5:104]: x
              Atom [5:97-5:104]: INT
            Atom [5:108-5:115]: LOG
            Name [5:118-5:119]: x
        For [5:5-5:59]
          Name [5:11-5:12]: x
          Literal [5:15-5:17]: INT(10)
          Binary [5:19-5:24]: GT
            Name [5:19-5:20]: x
            Literal [5:23-5:24]: INT(0)
          Binary [5:26-5:39]: ASSIGN
            Name [5:28-5:29]: x
            Binary [5:32-5:37]: SUB
              Name [5:32-5:33]: x
              Literal [5:36-5:37]: INT(1)
          Call [5:42-5:57]: printf
            Literal [5:49-5:53]: STR(%d)
            Name [5:55-5:56]: x
      Where [6:5-6:58]
        Defs [6:24-6:56]
          VarDef [6:24-6:39]: y
            Atom [6:32-6:39]: LOG
          VarDef [6:41-6:56]: z
            Atom [6:49-6:56]: LOG
        Binary [6:5-6:15]: OR
          Name [6:5-6:6]: x
          Binary [6:9-6:15]: AND
            Name [6:9-6:10]: y
            Unary [6:13-6:15]: NOT
              Name [6:14-6:15]: z
!end

!code:
typ int : integer;
fun f (x:int) : int = x + 100
!expected:
Defs [1:1-2:30]
  TypeDef [1:1-1:18]: int
    Atom [1:11-1:18]: INT
  FunDef [2:1-2:30]: f
    Parameter [2:8-2:13]: x
      TypeName [2:10-2:13]: int
    TypeName [2:17-2:20]: int
    Binary [2:23-2:30]: ADD
      Name [2:23-2:24]: x
      Literal [2:27-2:30]: INT(100)
!end