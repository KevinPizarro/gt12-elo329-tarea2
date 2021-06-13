JFLAGS = -g
JAVAC = $(JAVA_HOME)/bin/javac
JAVA = $(JAVA_HOME)/bin/java
PATH = D:\JDK\FX\lib
JC = $(JAVAC) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JVM= $(JAVA) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
FILE= configurationFile.txt

MAIN = Stage4

default:
	$(JC) *.java

run: 
	$(JVM) Stage4 $(FILE)

clean:
	$(RM) Stage4/*.class
