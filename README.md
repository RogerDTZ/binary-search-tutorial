# Binary Search Tutorial

## Problem Description

For problems that hold some kind of monotonicity, we can use **binary search** to solve them.

There are 2 essential components in problems solvable by binary search:
1. A non-descending or ascending sequence $A$. It can be either discrete or continuous.
2. A judge function $J(x)$ that accepts an element $x$ from $A$ and returns either `true` or `false`. $J(x)$ must be one of the 2 types below:
    * Type 1: $J(x)$ returns `true` when $x$ is extremely small. $J(x)$ continues to return `true` as $x$ increases, until $x$ grows above a certain value.
    * Type 2: $J(x)$ returns `true` when $x$ is extremely large. $J(x)$ continues to return `true` as $x$ decreases, until $x$ shrinks below a certain value.

For example, let $A$ be a non-descending integer array $[x_1, x_2, \dots, x_N]$ such that $x_1 \le x_2 \le \dots \le x_N$. Let $J_1(x)$ be a judge function of type 1, and $J_2(x)$ be a judge function of type 2.

If we feed all elements of $A$ into $J_1(x)$, the results will be like:
```
J_1(x_1)
|
|   J_1(x_3)    J_1(x_k)          J_1(x_N)
|   |           |                 |
v   v           v                 v
T T T T T ... T T F F ... F F F F F
  ^   ^           ^
  |   |           |
  |   J_1(x_4)    J_1(x_{k+1})
  |
  J_1(x_2)
```
We can see that $J_1$'s result flips from `true` to `false` as $x$ grows.

Likewise, if we feed all elements of $A$ into $J_2(x)$, the results will be like:
```
J_1(x_1)                          J_1(x_N)
|                                 |
|                 J_1(x_k)        |
|                 |               |
v                 v               V
F F F F F ... F F T T ... T T T T T
                ^               ^
                |               |
                J_1(x_{k-1})    |
                                |
                                J_1(x_{N - 1})
```

<u>The goal of binary search, is to locate the rightmost `T` for type 1 judge function, or to locate the leftmost `T` for type 2 judge function.</u>

### Example problem

Given an array $A = [1,41,41,42,42,42,43,43,65,65,66,66,66,67,67,100]$.
Please find the index of the first element no less than $50$.

Components:
1. Monotonic sequence: $A$
2. Judge function: type 2

$$
J(x) = 
\begin{cases}
\text{true} & x \ge 50 \\
\text{false} & x < 50
\end{cases}
$$

```
J(1) J(41) J(41) J(42) J(42) J(42) J(43) J(43) J(65) J(65) J(66) J(66) J(66) J(67) J(67) J(100)
  F    F     F     F     F     F     F     F     T     T     T     T     T     T     T      T
```

We are to find the first element $x$ in $A$ where $J(x)$ returns `true`, i.e., the leftmost `T`. The answer is the first $65$, i.e. the $9^{\text{th}}$ element (index starts from 1).

Of course, you can scan through all elements and check their $J(x)$. But this is too slow ($O(N)$). We can utilize the monotonicity of $A$ to solve the problem in $O(\log N)$.

## Algorithm

*The example's judge funtion is of type 2, therefore the following analysis is based on type 2 judge function. However, type 1 is in the symmetric form.*

Let $l, r$ be the lower bound and upper bound of the range where the answer falls in.
In the above example, we are to find the index of the first element no less than $50$. It has to be some integer in $[1, 16]$. Therefore, we let $l=1$ and $r=16$ initially.


We iterate to find the answer. Let $mid = \frac {l+r}{2}$, then check $J(mid)$:
* If $J(mid)$ is `true`, we move $r$ to $mid - 1$
```
FFFFFFFTTTTTTTTTTTTT
   ^    ^    ^
   |    |    |
   l   mid   r
   |    |
   v    v
FFFFFFFTTTTTTTTTTTTT
       ^ 
       |
       r'
```
* If $J(mid)$ is `false`, we move $l$ to $mid + 1$
```
FFFFFFFFFFFTTTTTTTTT
   ^    ^    ^
   |    |    |
   l   mid   r
        |    |
        v    v
FFFFFFFFFFFTTTTTTTTT
         ^  
         |
         l'
```

Eventually when $l > r$, the iteration stops. At the moment, $l$ is the answer. But why?

There will be only 2 cases.

In the first case, $l=r$ in the last iteration, and they are both on the `T/F` boundary (`T` side). Since $mid = l = r$ and $J(mid)$ is `true`, $r$ will be moved to $l-1$, which incurs $l>r$. Now $l$ is the answer.
```
          r
          |
          v
FFFFFFFFFFTTTTTTTTTTTTT
          ^
          |
          l
          |
          v
FFFFFFFFFFTTTTTTTTTTTTT
         ^
         |
         r'
```

In the second case, previously in the algorithm, $mid$ somehow falls on the boundary (`true` side).
```
FFFFFFFFTTTTTTTTTTTT
   ^    ^    ^
   |    |    |
   l   mid   r
   |    |
   v    v
FFFFFFFFTTTTTTTTTTTT
       ^ 
       |
       r'
```
Now both $l$ and $r$ are on the `false` side. Don't worry, because eventually in the last iteration, we will have $l=r$ and they are 1 step away from the answer. Our lovely $l$ will jump to the correct answer.
```
       l
       |
       v
FFFFFFFFTTTTTTTTTTTT
       ^
       |
       r
       |
       v
FFFFFFFFTTTTTTTTTTTT
        ^
        |
        l'
```

**Intuitively, the pointer we move when $J(mid)$ is `false` will be the final answer when the iteration stops.**

## Pseudocode

### Type 1 Judge
```c++
l = initial_l;
r = initial_r;

while (l <= r) {
    mid = (l + r) / 2;
    if (judge_type1(mid))
        l = mid + 1;
    else
        r = mid - 1; // r will be the answer when the iteration stops
}

answer = r;
```

### Type 2 Judge
```c++
l = initial_l;
r = initial_r;

while (l <= r) {
    mid = (l + r) / 2;
    if (judge_type2(mid))
        r = mid - 1;
    else
        l = mid + 1; // l will be the answer when the iteration stops
}

answer = l;
```

## Time Complexity

In each iteration, the range marked by $l, r$ is halved. Therefore, there will be $O(\log N)$ iterations, where $N$ is the size of initial range.
