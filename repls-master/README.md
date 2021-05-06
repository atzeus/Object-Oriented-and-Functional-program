# Advanced Programming: REPL Calculators

*Required knowledge*: To make this assignment, you need to be up to speed on the lecture up to and including pattern matching.

**NOTE** This assignment is different from the previous assignments in that it **requires** you to use advanced scala techniques such as pattern matching. The consensus among the TAs is that it is _**much harder**_ than the other assignments. Start early!!

In this exercise you will be making two calculators [REPLs](https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop). The first of these is a regular calculator dealing with integers, the second operates on [MultiSets](#multisets). A lot of the techniques discussed in this course are useful to enable code reuse and this assignment gives you ample opportunity to employ these techniques since both REPLs have very similar functionality.
 
The REPLs have three types of command:

- **Expression Evaluation:** If the input is an expression (for example: `1 + 4 * 3 + 5`) then the output should be its result (18 in this example)
- **Variable Assignment:** If the input is an assignment (for example: `n = 18 * m * 2 + 5`, the REPL should store
 the binding of that variable (either as Int or Multiset) and print the new binding (in this case: `n = 77`, assuming
  m = 2). If you encounter a variable in the input, as in the example above, you need to the value associated with
   that variable. If a variable does not have a value (it is unbound), then the input for variable assignment is
    incorrect. Hence, we can only bind variables to constants (not to expressions).
- **Expression Simplification:** If the input starts with an "@" then the REPL should simplify the expression after the "@" according to the [rules below](#simplification-rules). For example, `@ ( ( n * 2 ) + ( n * 3 ) ) + a * b` should give `n * 5 + a * b`. Notice that we compute the `n * (2 + 3) = n * 5`, you should do the same. Notice that you do not have to simplify `5 * n * m * 3` to `15 * n * m`. 

Note that evaluation and simplification only differ when dealing with unbound variables. Evaluating unbound variables
 give an error, but simplifying expression with unbound variables does not.  Bound variables should be treated as
  their corresponding value both when
  simplifying and evaluating. For example:
```
n = 18 + 2
> n = 20
n * n 
> 400
@ ( n * 2) + m * 1
> 40 + m
( n * 2 ) + m * 1
Unkown variable: m
```

## MultiSets
A multiset is like a set, but allows for duplicates. The number of instances of an element, `x` in a multiset `a` is
 called
 its *multiplicity*, written   <code>m<sub>a</sub>(x) </code>. For example, in the multiset `{ a, a, a, b, a }`, `a
 ` has a multiplicity of 4, and `b
 ` has a multiplicity of 1. The multiplicity is always an integer and never negative. The multiplicity of an element which is not in the multiset is 0.

You will implement three multiset operations:

- **Summation:** a + b is the multiset c such that:
    
    <code>∀x m<sub>c</sub>(x) = m<sub>a</sub>(x) + m<sub>b</sub>(x)</code>

    where <code>m<sub>c</sub>(x)</code> is the multiplicity of x in the set c.

- **Intersection:** a * b is the multiset c such that:

    <code>∀x m<sub>c</sub>(x) = min(m<sub>a</sub>(x), m<sub>b</sub>(x))</code>

- **Subtraction:** a - b is the multiset c such that:

    <code>∀x m<sub>c</sub>(x) = max(m<sub>a</sub>(x) - m<sub>b</sub>(x), 0)</code>

**Example:**

`{a,a,b,c} + {a,b,b,c,c} * {a,c,d} = {a,a,a,b,c,c}`

## Simplification Rules:

When simplifying an expression, you should employ the following rules:

**Rules for Integer calculator REPL:**

- `0 + e → e`
- `e + 0 → e`
- `1 * e → e`
- `e * 1 → e`
- `e * 0 → 0`
- `0 * e → 0`
- `e - e -> 0`

Distributivity rules : 
- `( a * b ) + ( a * c ) → a * ( b + c )`
- `( b * a ) + ( a * c ) → a * ( b + c )`
- `( a * b ) + ( c * a ) → a * ( b + c )`
- `( b * a ) + ( c * a ) → a * ( b + c )`

**Rules for Multiset calculator REPL:**

- `e * e → e`
- `{} * e → {}`
- `e * {} → {}`
- `e + {} → e`
- `{} + e → e`
- `e - e -> {}`

**Nested application of rules**

For some expressions, such as `(x + (0 + (0 + 0)))` it is not enough to simplify the top-level and then recursively simplify the sub-parts (this is called simplifying top-down). In this case we will then end up with `x + (0 + 0))` instead of `x + 0`. To solve this, choose one of two tactics:
* Simplify *bottom-up*: first simplify the sub-parts of the expression, before simplifying the whole. 
* Simplify until you reach a *fixpoint*. Apply rules in top-down fashion until the expression does not change anymore. 


## Suggested approach:
1. Completely implement the Integer calculator Repl and make sure it passes all the tests.
2. Implement the generic and immutable multiset, and all its accommodating functions. 
3. Implement the Multiset calculator Repl by copying and modifying code from the Integer Repl and making sure it passes all the tests.
4. Your code now works, but has a lot of duplicated code, which hinders maintenance and readability. Refactor your code, into REPL base, such that the common parts of both REPLs are shared.

More detailed instructions for constructing a REPL:

![expression construction before pattern matching](expression.svg)

* Convert the infix expression to an Expression tree. Note that the expression tree is needed for simplifying the expressions, calculation on the expression could be done without the expression tree. 
    Conversion of the infix expression to an expression tree is done in 2 steps (show in the picture above):
    1. Convert Infix expression to RPN using the  Shunting yard algorithm (below).
    2. Convert RPN to a expresion(parse) tree (where each node is an object of a case class) (an example of this is [here](https://gitlab.com/vu-oofp/lecture-code/-/blob/master/OOReversePolish.scala), which is discussed in the last 3 videos of these [video lectures](https://www.youtube.com/playlist?list=PLi-VVX8q87FIzFCmzXCc_JZZJkvW80C66))
* Simplify the expression tree using pattern matching video [video lectures](https://www.youtube.com/watch?v=d-4bKM8VEDQ&list=PLi-VVX8q87FKPlg-KeezbTb5v7VLqgk30)  [example of rewriting using pattern matching](https://gitlab.com/vu-oofp/lecture-code/-/blob/master/PatternMatch.scala) and dynamic dispatch ([videos on dynamic dispatch]([video lectures](https://www.youtube.com/playlist?list=PLi-VVX8q87FIzFCmzXCc_JZZJkvW80C66)).

### Tokenization
For parsing the input strings, you need to know the meaning of the characters. [Tokenization](https://en.wikipedia.org/wiki/Lexical_analysis#Tokenization) is the processes of giving abstract parts meaning. For example, giving `+` the token of an operator. This can be used in combination with [pattern matching](#pattern-matching) for the [Shunting Yard algorithm](#shunting-yard-algorithm). You could match on the constants, variables, and operators, in an expression.

### Shunting Yard Algorithm
This assignment requires you to parse expressions such as `a + b * c` and deal with operator precedence (i.e. this expression parses to `a + ( b * c )`). One way of doing this is by converting the expression into reverse polish notation by using the [Shunting Yard algorithm](http://mathcenter.oxford.emory.edu/site/cs171/shuntingYardAlgorithm/).


### Pretty Printing
We also expect you to "pretty print" a simplified expression such as `( ( a * b ) + ( c * d ) ) as a * b + c * d`. For this, it is handy to realize when parentheses are required: if the current expression is `l op r`, where `l` is the left-sub expression, `r` is the right sub-expression and `op` is the operator, then parentheses are needed around `l` iff:
* `l` is an expression with an operator `op2` AND
* `op2` has lower precedence than `op`

Symmetrically, parentheses are needed around `r` if 
* `r` is an expression with an operator `op2` AND
* `op2` has lower precedence than `op`

Let us consider an example: 
```a * b + (1 + x) * 2```
The expression with full parentheses is: `(a * b) + ((1 + x) * 2)`

The toplevel expression is of the form `l op r` where `op=+, l = (a * b) and r = ((1+x) * 2)`. The left hand expression `l` is indeed an expression with an operators, `op2=*`, but `op2(*)` has higher precedence than `op(+)`, so no parentheses are needed around `l = (a * b)`.

In the expression `a * b` the subexpressions `l=a` and `r=b` do not involve operators themselves, and hence no parentheses are needed around `a` and `b`.

In the expression `((1 + x) * 2)` we see that `l= ( 1 + x ),r=2, op=*`. The subexpression `l=( 1 + x )` has an operator `op2=+` and `op2(+)` has lower precedence than `op`. Hence parentheses are needed around `(1+ x)`. The subexpression `2` does not involve operators and hence no parentheses are needed.

This gives us then end result `a * b + (1 + x) * 2`.

### Operators used and their precedence

| Operator | precedence level |
|:---:     |:----------------:|
| +        | 2                |
| -        | 2                |
| *        | 3                |



## Skeleton
We have given a pretty barebones skeleton. This is to give you the full freedom of implementation. We have supplied you with the minimum for this assignment to work with the tests.

### REPLs
For the REPL we have given a `REPLBase`, which is an abstract class. This can be used for code sharing, explained [below](#inheritance). `IntREPL` and `MultiSetREPL` extend this abstract class. These, and `REPLBase`, you need to modify with your own implementation. The `REPL` trait you cannot modify, as this is used for running the REPL. Also do not modify the `REPLFactory`, and `RunREPL`, these don't need to be implemented further.

### MultiSet
Implement your MultiSet here. Note that **all** the functions we have given need to be implemented!

### Splitting the input string

You can use `.split(" ")` on the input string to split the string into parts, for example `"a = 23 + b".split
(" ") -> Array("a","23","+","b")`. The downside of this is that you have to write a space between "+" and its
 arguments. If you find this annoying, you can use or adjust `SplitExpressionString`, which splits strings in a way
  that is handy for this exercise.  Usage of this function is *optional* and not required for passing the tests. Inside the file
 `SplitExpressionString` you can
 find a more detailed explanation.

## Format of the assignment and tests
You may have noticed the examples we have given in these instructions, have spaces before and after the brackets. For example in `( 2 + 3 ) * 2`. This is intentional, making parsing easier and more reliable. For this reason, pretty printing should return the same, this making it possible to use the output of the pretty printing again.

However, for ease of use, you can make use of `SplitExpressionString`, or a similar implementation, to parse the input. This is not required for the tests, it does however make it easier to use manually (no need for the spaces after every character).

### MultiSet
We have given a skeleton of a case class MultiSet. Inside you find [overloaded functions](https://docs.scala-lang.org/tour/operators.html) (`+ - * toString`), these need to be implemented, with the corresponding outcome.

`toSeq` is used in the tests, but you can also use this in your REPL implementation, same for the `toString`.

The skeleton comes with an [companion object](https://docs.scala-lang.org/overviews/scala-book/companion-objects.html). In this you have to implement the apply function (a factory method, explained in more depth in [companion object](https://docs.scala-lang.org/overviews/scala-book/companion-objects.html)). This will take a sequence of elements, and you should construct a MultiSet from this. We use this as a factory in the tests, however you should also use this in your REPL to build a MultiSet. 

## How to run your implementation

From the command line run: 
```
 ./gradlew runIntREPL 
// or 
./gradlew runMultiSetREPL
```

In IntelliJ: In the gradle tab (on the right side) there is a folder/group 'repls/Tasks/runnables'. In here there are two tasks, namely: runIntREPL and runMultiSetREPL. You can run these to get a working instance of IntREPL and MultiSetREPL, respectively.

Another method would be by running the RunREPL object file, and give the respective type as argument, be it IntREPL or MultiSetREPL. This can most easily be done by going into "Edit Configurations..." (in the drop down menu next to the run button on the top). In here you can specify the type in the "Program arguments" field.

With this now you can use your REPLs as any other simple REPL, inputting an expression and outputting, hopefully, the expected output. In combination with the test examples, you can use this to help debug your implementation, and check if it returns the correct output. 


### Techniques for code reuse
##### Inheritance
Scala, and other high level languages, offers many techniques to have good code reuse. With inheritance, you can reuse code for classes which the same structure. This allows classes to dispatch method calls dynamically, also known as [dynamic dispatching](https://en.wikipedia.org/wiki/Dynamic_dispatch). Scala will call the lowest implementation of a method. In this method you can call the method of the super class, by `super.methodName()`. (hint: This is especially handy for the reusing common simplification rules)

In the skeleton we have given you this level of inheritance: REPL(trait) <-- REPLBase <-- IntREPL, MultiSetREPL. This meaning, shared code between IntREPL and MultiSetREPL should go into REPLBase. REPLBase is for a generic REPL, while IntREPL and MultiSetREPL are specific types of REPLS.
            
        
#### Using associated type (`type Base` in the REPL base)
An abstract class can have an abstract type variable. This type should be defined in a child class. In this assignment we make use of this with `type Base`, in the REPL base file. `Base` is now an abstract variable, and by overloading this in IntREPL or MultiSetREPL, with `Int` or `MultiSet`, we can specify this type. With this you can return a type that is still unknown/abstract. For example, you can write an evaluate function that returns this `Base`:

```scala
abstract class REPLBase extends REPL {
    type Base
    
    def evaluate(expression: String): Base = _
}
```

For the IntREPL, `Base` has been specified to be `Int`, so this function would return an `Int`, and a `MultiSet` for the MultiSetREPL. This way, a function or variables can be shared while returning different types.

For more information visit the scala docs [here](https://docs.scala-lang.org/tour/abstract-type-members.html).


## Assignments

There are 2 assignments for these repls:
### 4.1 Assignment 4.1 IntREPL + MultiSet 

Implement the IntREPL and the implement the MultiSet (not the MultiSet *REPL*) To pass this assignment, you need to get at least 48 points from the ReplsTestSuite4_1 test suite (not all tests are valued equally, as indicated by the weight argument after test name)

### 4.2 Assignment 4.2 MultiSetRepl + Sharing

Implement the MultiRepl and share code between the MultiSet REPL and the Int Repl.

Grading is mostly based on how much code is reused, and is built up as follows:
* Amount of tests passed (`ReplTestSuite4_2`): 5.5 points
* Reuse of common structure of `+`,`-` and `*` in `MultiSet` 0.5 points (hint : use first-class functions)
*  Reuse of REPL code between the int repl and multiset repl
    * Sharing of the expression representation 0.25 points
    * Sharing of the code related to variable assignment 0.25 points
    * Sharing of the commonalities in parsing expressions (string -> Expression)) 0.5 points
    * Sharing of the commonalities in evaluating expressions 0.5 points

    * Sharing of the commonalities in simplifying expressions 0.5
* Code style 2 points

Total : 10 points

Note that to get full points for sharing code, the common code needs to go in `REPLBase` and need to have extension points (e.g. abstract methods) such that you can easily add repls by subclassing `REPLBase`.  The `REPLBase` code should not have any code specific to the Integer or Multiset REPL (this should be in their respective classes). It should for example be possible to add another repl which centers around `Booleans` *without* modifying the `REPLBase` code. Hence you will for example not get full points if you have in REPLBase that checks via `if` statements or something similar whether it is handling Ints or Multisets.
