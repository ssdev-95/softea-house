javac -verbose --class-path src ./src/main.java -Xlint:unchecked -d bin
cd bin

java Main
