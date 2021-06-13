JFLAGS = -g #Flag correspondiente a compilación con información de debugeo
JAVAC = $(JAVA_HOME)/bin/javac #Path donde se encuentra el comando javac
JAVA = $(JAVA_HOME)/bin/java #Path donde se encuentra el comando java
PATH = /home/kevinpizarro/.m2/repository/org/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib #Path donde se encuentran los módulos de JavaFX
PATH2 = /home/kevinpizarro/IdeaProjects/Tareas-ELO329/Tarea2/ #Path de un nivel superior a la carpeta Stage1
JC = $(JAVAC) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media #Comando de ejecución para compilación del programa
JVM= $(JAVA) -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media #Comando de ejecución para la ejecución del programa
JD = $(JAVADOC) -d doc/ -cp $(PATH2) --module-path $(PATH) --add-modules javafx.controls,javafx.fxml,javafx.media #Comando para la creación de la documentación usando JavaDoc
FILE= configurationFile.txt #Archivo de configuración
FILE2 = #Aca se agrega el nombre del sonido a reproducir
#RM = del
#Descomentar línea anterior en caso de ejecutar en dispositivos con sistemas operativos Windows10

#Archivo java correspondiente al main
MAIN = Stage4

default: #Se ejecuta al llamar make.
	$(JC) *.java #Compila todos los archivos con extensión .java, generando sus .class correspondientes

run: #Se ejecuta al llamar make run.
	$(JVM) Extra2.$(MAIN) $(FILE) $(FILE2) #Ejecuta el archivo MAIN con el archivo de configuración FILE y el archivo de sonido a reproducir

doc: #Se ejecuta al llamar make doc
	$(JD) Extra2 #Genera la documentación en la carpeta Extra2
	
clean: #Se ejecuta al llamar make clean
	$(RM) *.class #Limpia todos los archivos con extensión .class