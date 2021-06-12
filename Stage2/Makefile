JFLAGS = -g
PATH = D:\JDK\FX\lib
JC = javac --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
JVM= java --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media
FILE= Configuracion.txt

.SUFFIXES: .java .class

.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = \
    Stage2.java \
    ComunaView.java \
    Pedestrian.java \
    PedestrianView.java \
    Comuna.java \
    Simulator.java \
    SimulatorConfig.java \
    SimulatorMenuBar.java

MAIN = Stage2

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)

clean: 
	$(RM) *.class
