# HR Usage

With the background of HR understood, what follows are the instructions for using the HR software. Note that this is ***not*** HRLPlus or HRL, but HR, the software upon which HRL and HRLPlus were developed. As with the background instructions, this information has been resurrected from the archives of the Internet, written by Simon Colton:

<https://web.archive.org/web/20071009225148/http://www.dai.ed.ac.uk/homes/simonco/research/hr/>

## Using HR: A Brief Guide

To best use HR properly, the Otter, MACE, LaTeX, Dot, and xgraph programs are needed.

### Giving Input to HR

The more initial information about a theory the user can give, the more HR can do. Here are some of the things a user can specify:

* Axioms.  These are necessary if any theorem proving is to be done.
* How to Generate Models.  This will enable HR to perform its own counterexample finding.
* Initial Concepts.  If the user is interested in any particular concepts, they can provide them in advance.
* Search Strategy Parameters.  See below
* Tasks to Achieve.  For example, if the user is looking for a concept which provides a particular categorisation, then the categorisation must be given.
* Output Choices  ie. how to present the output.

There are many ways to influence the search HR performs. Some of the many parameters that the user can set are:

* Which production rules to use.
* A complexity cap (usually less than 10).
* When to sort the concepts and conjectures (usually after every 10th new concept).
* When to attempt to settle conjectures (usually when first seen).
* How to sort the concepts and conjectures, ie. how to weight the measures.
* How to approach theorem proving (usually: HR first, Otter second).
* How long to spend finding proofs and counterexamples.
* How many counterexamples to find for each conjecture (usually just one).

At present, setting parameters has to be done on the command line, with commands such as: `?- cycle::set(sort_when(30)).` which tells HR to start sorting its concepts after the 30th has been introduced. Some more complicated sets of commands can be put into a 'modes' file which HR can read.

### Running HR

Once the user has set all the necessary parameters, usually HR is run completely autonomously. Users can, if they wish, interrupt the process and re-set the parameters, but this is not usual. To run HR, it needs to be given a target, something to achieve. For example, it could be asked to produce 100 conjectures, or 200 concepts. The list of things to ask for includes:

* Time, eg. to construct a theory for a given number of minutes.
* Concepts
* Conjectures
* Theorems
* Models
* Categorisations
* Classifications (ie. the gold standard, given by the user).
* Prime implicants

To start HR running, the user simply types construct(30,minutes)., etc. (if they want HR to work for half an hour). It is sometimes a good idea to change strategies half way through a session, and I often find myself asking HR to run for a certain time in one mode and then to complete the session in another mode. For example, if looking for a concept which classifies up to order 6, the classifying mode often specialises too early. Therefore, I usually run HR for 10 minutes in exhaustive mode (to cover the basics), then 10 minutes in novelty mode (to produce many different categorisations), then I finally change to classifying mode. In this case, the commands to run HR would be:

```
?- set_mode(exhaustive), construct(10,minutes),
   set_mode(novelty), construct(10,minutes),
   set_mode(classifying), construct(1,classification).
```

### Understanding HR's Output

Once HR has finished forming a theory, the user can employ some tools to understand and choose between the concepts that HR has come up with. Firstly, the user can look for concepts of a particular type. For example, in number theory, they may want to look for concepts which are number types. These will have tables with only one column, so the user looks for concepts with arity 1. They can use this in conjunction with one of many print commands, which will print to screen. Eg.

```
?- concept(A)::arity(1), A>20, print::fol(A).
[I] s.t. 2 = |{(a) : a|I}|
A = 21 ? ;
[I] s.t. 3 = |{(a) : a|I}|
A = 22 ? ;
.
.
.
[I] s.t. 2 = |{(a) : a|I}| & -(I = |{(b) : b|I}|)
A = 43 ?

yes
?- print::table(43).

| 3  || 5  || 7  || 11 || 13 || 17 || 19 |
yes
```

There are also some other, more graphical, ways to explore HR's concepts. For example, if the user were interested in how the average complexity of the concepts had changed over the course of the theory, they could type:

`?- view::concept_statistics(average,complexity).`

and they would be presented with this graph:

[Missing image!]

which shows how the given concepts have complexity 1, but as the theory progresses, the average complexity increases to nearly 5.5. Note that graphs of this type are produced using the xgraph package.

There are other graphs which HR can draw, using the DOT package from AT&T.; If the user is interested in the construction history of a concept or concepts, they can ask for a graphical representation of how the concepts were constructed. For example, if interested in concepts 77,88 and 99, the user could type:

`?- view::graph([77,88,99]).`

and they would be presented with this graph:

[Missing image!]

Finally, the user can ask HR to write and mark up a LaTeX script in a human-readable format. This aspect of HR's functionality is in need of an overhaul, and at present doesn't work correctly.

## HR Working In Group Theory

Please note that the results here are out of date, and we are planning another evaluation of HR in group theory.

### Classification Tasks

We wanted HR to invent a calculation which is an invariant (so it gives the same results for any pair of isomorphic groups), and which classifies the groups up to six (so it gives different results for any pair of non-isomorphic groups). HR has achieved this goal. The following concepts (translated from HR's original output for the sake of brevity) have been found by HR to classify the groups up to order 6.

* The function:

*f(G) = |{(a,b) in G x G : bab = a}|*

* The set:

*f(G) = {(m,n) : m = |{a in G : n = |{b in G : b=a2}|}|}*

* The function:

*f(G) = |{(a,(ab)n) in G x G : n > 0, not(a=b=(ab)n)}|*

* The function:

*f(G) = |G|x|{a in G : a = a-1}|*

* The set:

*f(G) = {(m,n) in N x N : m = |{(a,b) in G x G : n = |{c in G : c = bab}|}|}*

* The function:

*f(G) = |{(a,anb) in G x G : n >= 0, a =\= ann+1b}|*

* The set:

*f(G) = {(|G|,n) : exists a,b in G x G s.t. n = |{ (a,amb) in G x G : m >= 0 }|}*

(and there are more complicated ones than these). The first of these came as a surprise, as we had not expected such an easy to state concept to classify the groups up to order 6.

The five classifying concepts given above are distinct core classifying concepts. HR is very good at turning one classifying concept into many others, by concentrating its efforts on the classifying concepts that it already has, or on a parent concept which has produced a classifying concept. For example, the concept which was an ancestor of the the last two classifying concepts above was concentrated on, and produced two more classifying concepts. HR can show graphically where these concepts came from, and their relation to each other.

[Missing image!]

We have also given HR other categorisations for it to classify groups into. As a tricky example, we wanted HR to find a property of the groups D(2) and D(3) which is not shared by any other group up to order six.

HR spotted that they are the only ones with this property:

`for all a in G, there is an element b s.t. a =\= anb for some n > 0.`

(They are of course, also the only non-cyclic groups up to order 6 - it's left as an exercise to see whether the definition of non-cyclic and that given above are equivalent).

### Reinventing Classically Interesting Concepts

For a paper delivered at the Machine Discovery workshop at ECAI, we looked at twenty concepts from standard group theory, and asked whether HR had re-invented them. We concluded that:

HR re-invented the classical definition of these concepts:

* Elements of a group
* Order of a group
* Cyclic groups
* The orders of elements
* The centre of a group
* Abelian groups
* The identity of a group
* Inverses of Elements
* Orders of Elements

HR needs to be able to work with two theories at once (namely group theory and number theory), to be able to re-invent these concepts:

* p-Groups
* Elementary Abelian groups

HR needs more production rules to be able re-invent these concepts:

* Left cosets
* Quotient groups
* Normal subgroups
* Simple groups
* The index of subgroups
* Commutators
* Derived Series
* Central Series

It is unlikely that HR will re-invent these concepts as they are taken from other theories (geometry and number theory):

* Dihedral groups
* Quarternion groups

As HR develops, the search space it uses will enlarge, so that more of the above concepts can be found. However, this will mean that better heuristics are required.

## HR Working In Number Theory

There are many ways to break an integer into a finite set. HR can work in number theory by using a finite decomposition of integers into one or more of the following:

1. Triples *(a,b,c)* for which *a=bxc*. Eg. (10,5,2)
2. Triples *(a,b,c)* for which *a=b+c*. Eg. (10,4,6)
3. Pairs *(a,b)* for which *b* is a digit in the base 10 representation of *a*. Eg. (12,1), (12,2)

In practice, the decomposition of the integers up to 50 are given to HR by the user. HR then uses exactly the same production rules that were developed in group theory to produce number theory concepts.

### The Encyclopedia of Integer Sequences

HR works closely with its own copy of the encyclopedia of integer sequences, the home page for which is here:

<https://oeis.org/>

The encyclopedia contains over 47,000 integer sequences, all of which detail a different number theory concept. Each sequence has a unique "A-number", for example the sequence of prime numbers has number A000040. HR can turn its number theory concepts into integer sequences by either:

1. Writing down the integers of a particular type, eg. 2,3,5,7,11,... (primes)
2. Taking the integers 1,2,3, etc. and writing out the output of a function for them, eg. 1,2,2,3,2,... (tau function: the number of divisors).
3. Writing down the integers which output a larger number than all smaller numbers for a particular function, eg. 1,2,4,6,12,... (highly composite numbers: they have more divisors than any smaller positive integer). [This will become a mainstream production rule in the near future].

When prompted to do so, HR can generate sequences from its concepts and check whether they match any in the encyclopedia. It does this by first using the empirical evidence it has for the integers in its database, to suggest possible candidate sequences from the encyclopedia and then it uses the prolog representation of the concept to check membership of each encyclopedia entry. For example, if working with the integers up to 20, if HR's 17th concept was actually prime numbers, it would take each sequence in turn and check that 2,3,5,...,19 were in the sequence, but 1,4,6,...,20 were not. If a sequence passed this test, HR would check that predicate(17,[a1]), predicate(17,[a2]), etc. returned true for all the integers ai stored in the encyclopedia for that sequence. It would of course spot that sequence A000040 passed both tests for predicate 17.

### Re-Invented Integer Sequences

So far, there are 58 re-invented integer sequences that HR can claim to have re-invented.

1. Trivial sequences:
    * the sequence of 1s. [A000012].
    * the natural numbers. [A000027].
    * the non-negative numbers. [A001477].
    * a simple periodic sequence. [A000035].
    * 1 followed by zeros. [A000007].
    * two 1s followed by zeros (n such that Fermat's Last Theorem has a solution). [A019590].
2. Sequences resulting from factorisation:
    * the square numbers. [A000290].
    * the non-squares. [A000037].
    * the prime numbers. [A000040].
    * the prime numbers at the beginning of the 20th century. [A008578].
    * the squares of primes. [A001248].
    * the primes and squares of primes [A000430].
    * 1 and the odd primes [A006005].
    * 0 if prime, 1 otherwise. [A005171].
    * the composite numbers. [A002808].
    * the highly composite numbers [A002182].
    * tau(n) - the number of divisors. [A000005].
    * the number of proper divisors. [A032741].
    * integers with 4 divisors [A030513].
    * integers with 5 divisors [A030514]
    * integers with 6 divisors [A030515]
    * integers with 7 divisors [A030516]
    * integers with 8 divisors [A030626]
    * integers with 9 divisors [A030627]
    * integer square roots or zero [A037213].
    * writing out the divisors [A027750].
    * writing out the proper divisors [A027751].
    * the square-free integers. [A005117].
    * the powers of 2. [A000079].
    * integers with at most 2 prime factors. [A037143].
    * integers not divisible by 3. [A001651].
3. Sequences resulting from addition:
    * the even numbers. [A005843].
    * the odd numbers. [A005408].
    * writing out the numbers less than n. [A005408].
    * 1 together with the even numbers. [A004277].
    * 2 together with the odd numbers. [A004280].
    * 2,4 and the odd numbers. [A004281].
4. Sequences resulting from looking at the digits:
    * the repunits. [A000042].
    * integers with only twos as digits. [A002276].
    * each digit is a divisor [A034838].
    * integers with a 1 as a digit. [A011531].
    * integers with a 2 as a digit. [A011532].
    * integers with a 3 as a digit. [A011533].
    * integers with a 4 as a digit. [A011534].
    * integers with a 5 as a digit. [A011535].
    * integers with a 6 as a digit. [A011536].
    * integers with a 7 as a digit. [A011537].
    * integers with a 8 as a digit. [A011538].
    * integers with a 9 as a digit. [A011539].
    * the repdigits. [A010785].
    * divisible by each non-zero digit. [A002796].
    * each digit is prime. [A046034].
    * the numbers with two distinct digits. [A031955].
    * the number of distinct digits. [A043537].
    * number with distinct digits. [A010784].
    * no base 2 digit is a base 10 digit. [A037344].
    * the natural numbers in base 3. [A007089].

More interesting than the fact that HR reinvented these sequences are the ways in which HR defines them. For example, even numbers are defined as integers, n for which there is a natural number, m, such that m+m=n. Also, the natural numbers in base 3 were defined as integers in base 10 which have no digits which can be written as a+b where a>0, b>0 and a=/= b. Also, it defines the powers of two as those integers with no odd divisors except 1.

### New Integer Sequences

There are literally thousands of integer sequences invented by HR which do not appear in the encyclopedia. To submit a new sequence to the encyclopedia, it must pass three criteria: (i) it must be positive (ii) it must be infinite (iii) it must be interesting. Note that often some of these conditions are waived. The following sequences were invented by HR and submitted and accepted into the encyclopedia:

(a) 1,2,14,23,29,34,46,63,68,74,76,78,88,94,...[A036433].

those integers where the number of divisors is a digit of the number, which contains all primes with a two in them, and those primes starting with a two: [A045708].

(b) 1,4,9,11,14,19,41,44,49,91,94,99,... [A036435].

those integers where all the digits are non-zero square numbers.

(c) 0,1,0,1,1,0,2,0,1,1,0,2,1,1,1,0,0,... [A036431].

`f(n)=|{a < n:a+tau(a)=n}|`

which gives:

(d) 1,3,6,8,11,16,17,20,22,23,27,29,... [A036434].

those integers which cannot be written as *a+tau(a)* for some *a*, (ie. those *n* for which *f(n)=0*), and

(e) 1,2,7,38,122,2766,64686,... [A036432].

which are the integers that set a record for *f(n)*.

(f) 1,4,6,10,12,14,22,24,26,27,...[A036438].

those integers which can be written as *m x tau(m)* for some *m*, (which of course include the primes times 2, [A001747]).

(g) 1,6,8,10,14,15,22,26,27,...[A036436].

those integers where *tau(n)* is a square number. These are a superset of the multiplicatively perfect numbers [A007422] - those numbers where the product of the divisors = *n<sup>2</sup>*.

(h) 1,10,102,1023,10234,102345,...[A038378]

those integers which have more distinct digits than any smaller number. Note that this sequence is finite, with final term: 1023456789.

(i) 1,1,1,2,1,1,1,2,2,1,1,2,1,1,1,3,1,...[A046951]

the function *g(n) = |{(a,b):a\*b=n & a|b}|*. It turns out that if you calculate the number of divisors [A000005] of the square root of the largest square dividing *n*,[A000188] then this gives the same values.

(j) 1,4,16,36,144,576,1296,2304,3600,14400,32400,...[A046952]

those integers setting a record for *g(n)* above. It turns out that these are the squares of the highly composite numbers, [A002182].

(k) 0,0,1,0,2,0,3,1,1,2,4,0,5,3,4,0,6,1,7,2,5,6,8,0,2,7,8,3,9,1,10,4,9,10,... .[A047983]

These is given by the function: *h(n)=|{a < m : tau(a) = tau(n)}|*. ie. it is the number of integers less than n but with the same number of divisors. The positions of the zeros in this sequence form the "minimal numbers": [A007416].

We will keep adding to these lists as we find time to look at HR's output, extract the truly interesting sequences and submit them to the encyclopedia.

### Refactorable Numbers (Tau Numbers) - A Brief History

In May 1998, in literally its first session in number theory, HR outputted this sequence of integers:

1,2,8,9,12,18,24,36,40,56,60,72,80,84,88,96, ..

These have the appealing definition of being those integers for which the number of divisors is itself a divisor. We were surprised to see that no sequence, or similar sequence was present in the encyclopedia, and so we gave them a name, `refactorables', and submitted them to the encyclopedia. They were accepted and given A-number [A033950].

After submitting them to the encyclopedia, we looked through the mathematical literature to find discussion of them, but we could find no reference. Therefore, we claimed them as a machine invention, and discussed them in talks about HR. After discussing HR at the ECAI'98 machine discovery workshop, a reporter for the New Scientist magazine wrote a couple of paragraphs about HR's achievements: (5th September, page 17), Further, we began to develop refactorable numbers, and found that they have many interesting properties, for example, that all odd refactorables are prime, perfect numbers are not refactorable, etc. All these results were presented in a paper published in the Journal of Integer Sequences (JIS):

<https://cs.uwaterloo.ca/journals/JIS/colton/joisol.html>

Some of the results in the above paper were conjectured by HR, and we thought that publication of the paper would be the final chapter in the story of refactorables. However, two mathematicians, Robert Kennedy and Curtis Cooper, from the Central Missouri State University, read the above paper and emailed in March 1999 to say that the concept of refactorables had been defined already, and that they were actually called "tau numbers". In fact they had written a paper in 1990 which discussed tau numbers.

The fact that HR only re-invented tau numbers shouldn't detract from its achievement, as these integers are clearly interesting and despite having a very simple definition, they have only recently been introduced to number theory, and have rarely been discussed. I've written a addenda to the JIS paper, which discusses the implications of the news.

Various people, including myself, have added further integer sequences based on refactorable numbers:

* [A036898] Pairs of consecutive refactorable numbers.
* [A036899] Product of pairs of consecutive refactorables (must also be refactorable).
* [A036897] A way of generating refactorables.
* [A036878] Integers of the form pp-1 (which must be refactorable).
* [A036907] Square refactorable numbers.
* [A036761] The number of refactorable integers of binary order n.
* [A036762] The integer values of x/d[x] in order of magnitude of x in A033950..
* [A039819] Number of divisors of A033950[n]
* [A046526] Numbers common to A033950 and A046524.
* [A046525] Numbers common to A033950 and A034884.
* [A046754] Square of d(n) (number of divisors) divides n.
* [A047727]Average divisor is an integer and the number is refactorable.
* [A047728] Multiply perfect, refactorable numbers with integer average divisor dividing the number.

### The CRCM (Datamining with the Encyclopedia)

A sub-project of the HR project is called the CRCM program, which stands for "concept-rich conjecture making". This is designed not to find conjectures as a theory is being constructed, but rather to find conjectures when there are many concepts already invented. The example we use here is the encyclopedia of integer sequences, which contains over 45,000 different number theory concepts. The CRCM is charged with the task of making conjectures about the integer sequences. One way to do this is to look for sequences which are subsequences of a given sequences. For example, when running the CRCM, we could ask for subsequences of the prime numbers:

```
?- subsequences_of('A000040').

Looking for subsequences of: A000040 The prime numbers.

A000040 The prime numbers.
A000043 Mersenne primes (p such that 2^p - 1 is prime).
A000057 Primes dividing all Fibonacci sequences.
A000058 Sylvester s sequence: a(n+1) = a(n)^2 - a(n) + 1.
.
.
A046493 Primes expressible as the sum of 3 consecutive palindromic primes.
A046494 Primes expressible as the sum of 2 consecutive palindromes.
A046495 Primes expressible as the sum of 3 consecutive palindromes.
A046496 Primes expressible as the sum of 4 consecutive palindromes.

3600 matches found.
yes
```

3600 matches is clearly too many. To reduce the number of sequences output, we need to understand how HR suggests subsequences.

**Definition 10.1:** An integer, *n*, is a *member* of sequence, s, if it appears in the encyclopedia entry for that sequence. Note that, although 10007 is a prime number, it does not appear in the encyclopedia for sequence 'A000040' (prime numbers), hence it is not a member of the sequence for our purposes.

**Definition 10.2:** The *range* of an integer sequence is the set of integers between the smallest and largest entries in the encyclopedia for the sequence. For example, the encyclopedia entry for prime numbers is: 2,3,5,...,271, so the range of this sequence is the set {2,3,4,5,...,271}.

Sequence *s<sub>1</sub>* is then suggested as a possible subsequence of sequence *s<sub>2</sub>* if there is no member of *s<sub>1</sub>* which is not a member of *s<sub>2</sub>*.

Note that this way of suggesting subsequences will suggest any sequence whose range is disjoint with the one in question, and often these suggestions should be avoided. This prompted the following definition:

**Definition 10.3**: The term overlap of sequence *s<sub>1</sub>* and sequence *s<sub>2</sub>* is the number of integers which are members of both *s<sub>1</sub>* and *s<sub>2</sub>*. Note that this is not counted multiplicatively.

If we set a term overlap minimum of, say, 5 integers, then this reduces the number of suggestions produced by HR from 3600 to 1129, which is an improvement. We can also impose the requirement that the suggested sequence be increasing. This will identify subsequences that are possibly integer types. This reduces the number of HR's suggestions from 1129 to 1018, which is again an improvement. Finally, we can use keywords provided by the encyclopedia about the type of sequence HR suggests. Certain sequences have keywords associated, such as "nice" or "core" or "base" (involving integers written in a base other than 10). If we impose the condition that HR's suggestions have to be categorised as "nice", then we narrow down the output to just 15:

```
?- set(term_overlap_min,5), set(increasing,yes), set(keyword,nice).

yes
?- subsequences_of('A000040').

Looking for subsequences of: A000040 The prime numbers.

A002231 Primitive roots that go with the primes in A029932.
A002267 Primes dividing order of Monster simple group.
A002385 Palindromic primes.
A005478 Prime Fibonacci numbers.
A006883 Long primes: 1/n has period n-1.
A007459 Higgs primes: a(n+1) = next prime such that a(n+1)-1 | (a(1)...a(n))^2 .
A007500 "Palindromic" primes: reversal is prime 
A014556 Euler s "Lucky" numbers: n such that m^2-m+n is prime for m=0..n-1.
A016114 Circular primes (remains prime under cyclic shifts of digits).
A028864 Primes with digits in ascending order.
A030079 Primes such that digits of p appear in p^2.
A030096 Primes whose digits are all odd.
A030284 Primes whose digits do not appear in previous term.
A033274 Primes that don t contain any other primes as a substring.
A033875 Skipping from prime to prime by least powers of 2.

15 matches found
yes
```

There are other conjectures we can ask HR to make using the encyclopedia. For example, we can ask for supersequences to a given sequence. Also, we can ask for sequences where the nth term is always less than (or greater than) the term in the sequence we are interested in. Also, HR can compose two functions, and spot when the composition always gives a constant (eg. tau(prime_number) = 2). Finally, HR can point out which sequences are completely disjoint to a particular sequence.

To keep the number of suggestions output by HR for these conjecture types down to a minimum, there are many other measures HR can use, including:

* Density of the suggested sequence.

This is: *(number of distinct terms)/(number of terms)*.

* Range overlap of two sequences.

This is the proportion of terms from the second sequence that are in the range of the first sequence.

* Range size of the suggested sequence.

This is the difference between the smallest and largest terms in the sequence.

* Difference of two sequences.

This is the average of *|a(n) - b(n)|* where *a(n)* is the *n*th term of the first sequence, and *a(n)* is the *n*th term of the second sequence.

* Variety of the suggested sequence.

This is the number of distinct terms in the sequence.

None of the results above about prime numbers are particularly interesting, but HR has suggested some conjectures which were unknown to us and were subsequently proved. These four were presented and proved in the JIS paper about refactorables:

1. A033950 is a subsequence of A047466
    * In English: Tau numbers are congruent to 0,1,2 or 4 mod 8. This was fairly easy to prove.
2. A033950 is a subsequence of A009230
    * In English: Tau numbers can be written as *lcm(n,tau(n))* for some *n*. This was obvious from the definition of tau numbers.
3. A000396 is a subsequence of A009242.
    * In English: Even perfect numbers can be written as *lcm(n,sigma(n))* for some *n*. [Where *sigma(n)* is the sum of the divisors of *n*]. This took a little more proving.
4. A000396 is a subsequence of A007517.
    * In English: Even perfect numbers can be written as *lcm(n,phi(n))*. [Where *phi(n)* is the number of positive integers less than and coprime to *n*]. This also took a little proving.

Here are some more recent results, some of which have not been settled yet.
1. A002301 is a subsequence of A033950
    * In English: Numbers of the form *(n!)/3* are tau numbers (ie. the number of divisors is a divisor itself). This is true up to 100!, but we have not proved or disproved it yet.

## HR Working In Graph Theory

This is under construction, please bear with me.

At present, HR works only with connected graphs, but there is no reason why it shouldn't work with graphs in general. HR uses three tables to represent connected graphs, one for the nodes, one for the edges and one for the relation: node a is on edge e. For example, these graphs:

[Missing image!]

are represented by these tables:

[Missing image!]

This representation allows HR to re-invent many graph theory concepts, including types of graph and numerical invariants, as discussed below. HR works with the connected graphs with four or less nodes, but can access the connected graphs with up to six nodes for disproving conjectures, etc.

### Diagrams of Graphs

To help us understand the concepts HR introduces in graph theory, HR can invoke the DOT program from AT&T.; The graphs produced are not particularly eye-catching (it would be better to use the neato program, but I can't get this to work at the moment), but they serve the purpose. HR can identify graphs of a given type: eg, in a recent session, HR's 31st concept was a type of graph known as a star (for obvious reasons). When asked to view this concept, we see that HR puts those graphs in boxes and we get the following:

`?- view::gt_concept(31).`

[Missing image!]

Also, HR can identify nodes, or edges of a particular type, eg. concept 12 was the centre node of stars, and when asked to show this concept, HR puts square boxes around the nodes in question:

`?- view::gt_concept(12).`

[Missing image!]

Also, HR can display all the graphs of a given type from its store for counterexample finding. For example, concept 86 was the re-invented concept of complete graphs (ie. every pair of nodes joined by an edge). Asked to display all these, we see:

`?- view::all_graphs_of_type(86).`

[Missing image!]

### Types of Connected Graph Found by HR
| Name                | HR's Definition (ni=nodes, ei=edges, M,N=numbers)                            | Example | Sequence  |
| ------------------- | ---------------------------------------------------------------------------- | ------- | --------- |
| Star                | exists n1 (all e2 (n1 is on e2))                                             |         |           |
| Cycle               | all n1 (2=&#124;{(e1) : n2 is on e1}&#124;)                                  |         |           |
| Regular             | exists M (all n1 (M=&#124;{(e1) : n2 is on e1}&#124;))                       |         | [A005177] |
| Complete            | all n1 n2 (exists e1 (n1 is on e1 & n2 is on e1))                            |         |           |
| Only 1 cycle        | exists M (M=&#124;{(n1) : node(n1)}&#124; & M=&#124;{(e1) : edge(e1)}&#124;) |         | [A001429] |
| No node of degree 2 | all n1 (-(2=&#124;{(e1) : n1 is on e1}&#124;))                               |         | [A005636] |
| Without endpoints   | all n1 (-(1=&#124;{(e1) : n1 is on e1}&#124;))                               |         | [A024528] |

More to come.

### Graph Theory Numerical Invariants

Watch this space.

## Related Work

There is a lot of work being done in this space. Below lists some related programs.

### Graffiti

The Graffiti program, by Siemion Fajtlowicz, is a very successful conjecture making application developed to work primarily in graph theory. Over 60 papers and articles have been written about conjectures produced by Graffiti, and some of the finest graphs theorists of the day have looked at the conjectures.

Graffiti makes very simple conjectures by finding that one graph theory invariant is less than another invariants. Sometimes the invariants are actually sums of two or (rarely) three other well known invariants. For example, this is a typical conjecture output by Graffiti: For all graphs, G,

chromatic_number(G) + radius(G) =< max_degree(G) + frequency_of_max_degree(G)

Such conjectures are (i) useful, because they provide a bound for invariants, which may improve the time taken to calculate them and (ii) often difficult to prove or disprove. Before Graffiti states a conjecture, it must first pass two heuristic tests: (a) it must provide a useful bound (ie. provide a stronger bound for at least one graph than all previous conjectures), and (b) the left hand side must not be semantically similar to the right hand side (which cuts down on trivial conjectures such as diameter(G) =< diameter(G) + 1). If the conjecture passes these tests, then it is tested empirically on a fairly large set of graphs which Graffiti updates, and if it is empirically true, it is output to screen. The database of graphs is only added to when a counterexample of one of Graffiti's conjectures is found.

Please visit:

<https://web.archive.org/web/20090302092953/https://www.math.uh.edu/~clarson/graffiti.html>

For further details on the Graffiti program.
