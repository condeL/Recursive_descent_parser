# Recursive descent parser

A recursive descent parser in Java to recognize a MINI language.  
Run the syntax analyser in the command line specifying a text file to analyze.

### TOKENS

#### Reserved words:
if then else end repeat until read write

#### Special symbols:
\+ \- \* \/ \= \< ( ) ; :=

#### Other tokens:
number (this token consists of one or more digits)

identifier (this token consists of one or more letters)

### GRAMMAR

program → stmt-sequence

stmt-sequence → stmt-sequence; statement | statement

statement → if-stmt | repeat-stmt | assign-stmt | read-stmt | write stmt

if-stmt → if exp then stmt-sequence end |

if exp then stmt-sequence else stmt-sequence end

repeat-stmt → repeat stmt-sequence until exp

assign-stmt → identifier := exp

read-stmt → read identifier

write-stmt → write exp

exp → simple-exp comparison-op simple-exp | simple-exp

comparison-op → < | =

simple-exp → simple-exp addop term | term

addop → + | -

term → term mulop factor | factor

mulop → * | /

factor → ( exp ) | number | identifier
