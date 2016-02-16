This is an Accumulator which maintains a running total sum of one or more variables.
Updating of this total value is done by accumulating more variables.

To develop this below are the files/steps which should be checked/run.

Interface -> Accumulator.java
Implementation class -> AccumulatorImpl.java
Test class -> AccumulatorImplTest.java
Build is done using maven -> pom.xml

Please execute the above Testclass 
mvn clean install
mvn clean test -Dtest=AccumulatorImplTest (which covers 11 scenarios) 

There is another implementation of the same problem using LongAdder is done :
The test classes contains almost all the same test cases but with small changes.

Implementation class -> AccumulatorSecondImpl.java
Same set of Test cases  -> AccumulatorSecondImplTest.java
mvn clean test -Dtest=AccumulatorSecondImplTest


