JFLAGS = -g
JAVAC = $(JAVA_HOME)/bin/javac
JAVA = $(JAVA_HOME)/bin/java
PATH = D:\JDK\FX\lib
PATH2 = D:\Miguel\Documents\Codigos\Java\ELO329\Tarea_2\andrade.miguel-cruces.manuel-pizarro.kevin-troncoso.pablo-tarea2.2021.1
JC = $(JAVAC) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JVM= $(JAVA) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
FILE= configurationFile.txt
RM = del 

MAIN = Stage3

default:
	$(JC) *.java

run: 
	$(JVM) $(MAIN).$(MAIN) $(FILE)

clean:
	$(RM) *.class
