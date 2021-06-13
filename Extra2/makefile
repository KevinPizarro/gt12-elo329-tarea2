JFLAGS = -g
JAVAC = $(JAVA_HOME)/bin/javac
JAVA = $(JAVA_HOME)/bin/java
JAVADOC = $(JAVA_HOME)/bin/javadoc
PATH = D:\JDK\FX\lib
PATH2 = D:\Miguel\Documents\Codigos\Java\ELO329\Tarea_2\andrade.miguel-cruces.manuel-pizarro.kevin-troncoso.pablo-tarea2.2021.1
JC = $(JAVAC) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JVM = $(JAVA) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JD = $(JAVADOC) -d doc/ -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
FILE= configurationFile.txt
RM = del 

MAIN = Stage4

default:
	$(JC) *.java

run: 
	$(JVM) Extra2.$(MAIN) $(FILE)

doc:
	$(JD) Extra2
	
clean:
	$(RM) *.class