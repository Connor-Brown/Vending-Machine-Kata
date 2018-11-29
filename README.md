# Vending-Machine-Kata

NOTE: This project is WIP. As such, I will be constantly updating both the project files and this README to suit the current version of the project.

NOTE: The user interface for the application is currerntly through the command line. I am planning to change this to be through the browser as a webapp in the future (hence why the pom.xml contains spring boot and jstl references)


THE first working version is completed. Reference below in order to build, test, and run the application.


How to build:
This project was built using Maven. In order to build it from the command line, you will need Maven installed and set up on your machine. If you do not know how to do this, I the following link gives a useful guide: https://maven.apache.org/install.html

Once Maven is installed, navigate to the project directory and run the command "mvn clean package". This will create a jar file in the target directory of the project.


How to run:
Note: I'm assuming you have Java installed and have the java environment variable set up, and have completed the "How to build" section above.

Open the command line and navigate to the jar file you would like to run. Type "java -jar [jar file name]".


How to test:
Assuming you have maven installed, you can navigate to the project directory and run the command "mvn clean test". If you would like to see the report in the command line with any stack traces run the command "mvn clean test -Dsurefire.useFile=false".
