This project is built using maven, so first get maven install following the instruction here http://maven.apache.org/install.html.
To verify that maven is installed, run 
~~~~
mvn -version
~~~~

To build the project, run from the root directory (the directory that contains pom.xml)
~~~~
mvn clean install
~~~~

To run the program, execute (also from the root directory) 
~~~~
mvn exec:java -Dexec.mainClass="com.gomoku.main.Application"
~~~~
