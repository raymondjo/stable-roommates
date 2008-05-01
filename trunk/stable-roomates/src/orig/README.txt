The Stable Roommates Program
----------------------------

David Manlove, University of Glasgow
------------------------------------


General
-------

This is a program designed to solve arbitrary instances of the stable roommates 
problem [1, 2, 3, 4]. It incorporates the algorithm appearing in [4], which is a 
variant of the algorithm first published in [2].

For a given instance of the problem, the program will find a stable matching if 
one exists, or else will report that no stable matching is possible.

The number of participants must be an even number, say 2n.

The preference lists must be complete - in other words, each participants must 
provide a strictly ordered ranking list of all 2n-1 other participants.

The participants are identified by positive integers in the range [1..2n]. 
 

Input / output
--------------

The program is entirely text-based, reading from standard input and writing to 
standard output.

The required input is as follows: 

* line 1: N - a positive (even) integer, the size of the instance (number of 
  participants) 

* lines 2 .. N+1: the preference lists, each has form j : ij,1 ij,2 ... ij,N-1, 
  where j is the owner and ij,1 ij,2 ... ij,N-1 are the entries in order 

* person identifiers are positive integers in the range [1..N], separated by one
  one or more spaces in the lists

The output consists of:

* the original preference lists 

* the so-called `Phase-1 table' 

* an indication of whether a stable matching exists 

* if so, the preference lists annotated with one such matching and a list of the
  matched pairs

Assumptions

* the size of the instance and each person's list is on a single line, with no 
  initial blanks 
* the number of lists corresponds exactly to the input value of N 
* each line is terminated by an EOL (end of line) character
 

Source code
-----------

The src folder contains the source code, written in Java. It consists of the 
following:

* a class containing the main program, SR.java

A subdirectory Roommates, containing several files belonging to the package of 
the same name, as follows:

* a class to handle Phase 1 of the algorithm, Phase1.java 
* a class to handle preference information, PersonArray.java 
* a class to handle a single person's preference information, Person.java 
* a class to handle a single preference list entry, Pelt.java 
* a class to store entries deleted from the preference lists DelStackNode.java

The source code is portable. It was compiled using JDK 1.1.7 and should be 
compatible with more recent Java compilers.
 

Executable
----------

The bin folder contains all Java class files corresponding to the above .java 
files, for execution by the Java Virtual Machine on any platform. In addition, 
two sample input files are provided.  The program may be run by issuing the 
following command at the command prompt, from within the bin directory:

* java SR < input.txt

where input.txt is the name of the file containing the input size and preference 
lists.  The user should ensure that the classpath variable is set to include the 
current working directory (i.e. .) and also the directory containing standard 
JDK library files.


DISCLAIMER: this program is not supported, and although it has been subjected to 
extensive testing, the authors cannot take responsibility for any loss etc. 
resulting from errors or unexpected behaviour.

Copies of the program may be made freely for private study or educational use, 
provided that the program headers acknowledging the authors are retained.
 

References
----------

1. D.Gale & L.S.Shapley, "College admissions and the stability of marriage", 
   American Mathematical Monthly, 69 (1962), 9 - 15.

2. D.Gusfield & R.W.Irving, "The Stable Marriage Problem: Structure and
   Algorithms", MIT Press, 1989.

3. R.W.Irving, "An efficient algorithm for the stable roommates problem", 
   Journal of Algorithms, 6 (1985), 577-595.

4. R.W. Irving and D.F. Manlove, "The stable roommates problem with ties", 
   Technical Report TR-2000-?? of the Computing Science Department of Glasgow 
   University, September 2000. Submitted for publication.
