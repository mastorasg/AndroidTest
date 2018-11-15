# Smartphone Sensors – Android
University of Bamberg, Ubiquitous Computing Assignment #1: Android sensor / actuator

1. cd into AndroidTest Folder

2. compile AndroidTest.java via the following command with classpath referencing SensorUDPReceiver.jar:

        javac -cp lib/SensorUDPReceiver.jar -sourcepath src -d bin/classes src/de/cmlab/ubicomp/AndroidTest.java
 
   or simply

        ant compile
        
3. run AndroidTest by executing the following commands:

        jar cfm bin/jar/AndroidTest.jar myManifest -C bin/classes .
        java -cp lib/SensorUDPReceiver.jar:bin/jar/AndroidTest.jar de.cmlab.ubicomp.AndroidTest
        
   or simply

        ant run
        
   clean & 2 & 3 using ant:

        ant main
        
   or simply

        ant
        
4. check your console output
