This project is built using maven, so first get maven install following the instruction here http://maven.apache.org/install.html.
To verify that maven is installed, run 
~~~~
mvn -version
~~~~

To build the project, run
~~~~
mvn clean install
~~~~

To run the program, execute 
~~~~
mvn exec:java -Dexec.mainClass="com.gomoku.main.Application"
~~~~
