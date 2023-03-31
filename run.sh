rm bin -rf

javac -verbose --class-path src ./src/Main.java -Xlint:unchecked -d bin

cd bin

java Main
