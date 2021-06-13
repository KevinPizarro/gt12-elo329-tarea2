JFLAGS = -g
JAVAC = $(JAVA_HOME)/bin/javac
JAVA = $(JAVA_HOME)/bin/java
JAVADOC = $(JAVA_HOME)/bin/javadoc
PATH = D:\JDK\FX\lib #Path a la libreria de javafx
#Path a donde se encuentra la carpeta principal de la tarea
PATH2 = D:\Miguel\Documents\Codigos\Java\ELO329\Tarea_2\andrade.miguel-cruces.manuel-pizarro.kevin-troncoso.pablo-tarea2.2021.1
JC = $(JAVAC) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JVM = $(JAVA) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JD = $(JAVADOC) -d doc/ -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
FILE = configurationFile.txt
FILE2 = #Aca se agrega el nombre del sonido a reproducir
RM = del #Eliminar si se ejecuta desde linux 

MAIN = Stage4

default:
	$(JC) *.java

run: 
	$(JVM) Extra1.$(MAIN) $(FILE) $(FILE2)

doc:
	$(JD) Extra1
	
clean:
	$(RM) *.class