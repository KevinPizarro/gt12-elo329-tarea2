JFLAGS = -g
export PATH = /home/kevinpizarro/.m2/repository/org/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib
JC = javac --module-path $(PATH) --add-modules javafx.controls,javafx.fxml
JVM= java --module-path $(PATH) --add-modules javafx.controls,javafx.fxml
FILE= configurationFile.txt

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
    ComunaView.java \
	Comuna.java \
	Pedestrian.java \
	PedestrianView.java \
	Simulator.java \
	SimulatorConfig.java \
	SimulatorMenuBar.java \
	Stage1.java

MAIN = Stage1

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class
