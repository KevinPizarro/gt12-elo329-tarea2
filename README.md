# Tarea 2

	Tarea 2 - ELO329: Simulador Gráfico de la evolución de una pandemia.

### Estructura General

El repositorio cuenta con X carpetas, un readme.md y un archivo documentacion.pdf para una información más detallada sobre la solución y el código escrito. Cada una de las carpetas resuelve una etapa de ésta y todas poseen los siguientes archivos:

- StageX.java
- Simulador.java
- Comuna.java
- Individuo.java
- Configuracion.txt
- makefile

El archivo principal de ejecución (main) es StageX.java, donde X varía según la etapa en la que se desee ejecutar, para la etapa extra se renombra a Extra.java. Además, el archivo Configuracion.txt contiene los parámetros de la simulación de la forma:

		<Tiempo de Simulación [s]> <N. de Individuos> <N. de Infectados> <Tiempo que dura la infección [s]> 
		<Ancho de la comuna [m]> <Alto de la comuna [m]>
		<Velocidad de los individuos [m/s]> <∆t [s]> <∆θ [rad]>
		<Distancia de contagio [m]> <Razón de uso de mascarilla> <p0> <p1> <p2> //Probabilidades de Contagio
		<Cant. de Vacunatorios> <Tamaño de los Vacunatorios> <Tiempo para empezar a Vacunar [s]>

### Ejecución

Dentro del servidor Aragorn, se debe subir la carpeta de la etapa (Stage) que se desee simular, luego hay que asegurarse de estar dentro del directorio con el comando *> cd \<Nombre del directorio\>*. Finalmente, para ejecutar cada etapa del programa (con el seteo descrito anteriormente) se escriben en consola los siguientes comandos en los servidores de Aragorn:

    > make
    > make run
    > make clean
    
Donde el comando **make** realiza la compilación del código, **make run** ejecuta el código y **make clean** limpia los archivos .class generados de la compilación.

---
### Otros

  Se decide optar por la etapa Extra, éste se encuentra en la carpeta Extra.

  Además se toma como referencia la Tarea 1 para poder trabajar modularmente. El repositorio se encuentra [aquí](https://gitlab.com/gt12-elo329/tarea1).