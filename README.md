# Tarea 2

	Tarea 2 - ELO329: Simulador Gráfico de la evolución de una pandemia.

### Estructura General

El repositorio cuenta con 4 carpetas, un readme.md y un archivo documentacion.pdf para una información más detallada sobre la solución y el código escrito. Cada una de las carpetas resuelve una etapa de ésta y todas poseen los siguientes archivos:

- Comuna.java
- ComunaView.java
- Pedestrian.java
- PedestrianView.java
- Simulator.java
- SimulatorConfig.java
- SimulatorMenuBar.java
- StageX.java
- configurationFile.txt
- makefile

El archivo principal de ejecución (main) es StageX.java, donde X varía según la etapa en la que se desee ejecutar. Además, el archivo configurationFile.txt contiene los parámetros de la simulación de la forma:

		<N. de Individuos> <N. de Infectados> <Tiempo que dura la infección [s]> 
		<Ancho de la comuna [m]> <Alto de la comuna [m]>
		<Velocidad de los individuos [m/s]> <∆t [s]> <∆θ [rad]>
		<Distancia de contagio [m]> <Razón de uso de mascarilla> <p0> <p1> <p2> //Probabilidades de Contagio
		<Cant. de Vacunatorios> <Tamaño de los Vacunatorios> <Tiempo para empezar a Vacunar [s]>

### Ejecución

Antes de ejecutar deberemos realizar un seteo previo, a continuación los pasos. 

- Se debe descargar la carpeta de la etapa (Stage) que se desee simular, luego hay que asegurarse de estar dentro del directorio con el comando *> cd \<Nombre del directorio\>*.
- Se debe configurar la variable PATH dentro del archivo makefile. Debe ser modificada al directorio que se encuentren los módulos de JavaFX, como por ejemplo javafx.controls. 

Finalmente, para ejecutar cada etapa del programa (con el seteo descrito anteriormente) se escriben en consola los siguientes comandos en los servidores de Aragorn:

    > make
    > make run
    > make clean
    
Donde el comando **make** realiza la compilación del código, **make run** ejecuta el código y **make clean** limpia los archivos .class generados de la compilación.

---
### Otros

  Se toma como referencia la Tarea 1 para poder trabajar modularmente, añadiendole así la interfaz gráfica y ajustando solamente lo necesario. El repositorio se encuentra [aquí](https://gitlab.com/gt12-elo329/tarea1).