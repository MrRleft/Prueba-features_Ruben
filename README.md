Versión: 1.0

La prueba consistirá en la finalización de las tareas pendientes de una app para almacenar el historial de puntuaciones de una partida de bolos. Para ello se proporciona una documentación funcional de la aplicación y un listado de tareas pendientes. En el código también se podrán encontrar *TODOs* para facilitar éstas tareas.

NOTA: puedes existir errores intencionados que no estén especificados como tareas o marcados con *TODOs*.

# Funcional

### Launcher
Al inicio de la app se deberá realizar una sincronización de datos con los servidores externos para obtener los datos iniciales. Si ya tenemos esas partidas en local no se deberán modificar, descartaremos los cambios de remoto.

### Home
Pantalla principal de la app. Tiene 3 navegaciones:
- Continuar: Solo aparece si la última partida aún no ha terminado, al pulsar nos llevará a esa partida para continuar introduciendo puntuaciones.
- Nueva Partida: Crea una nueva partida. Si existe una partida sin terminar deberemos mostrar un dialogo indicando que la creación de una nueva partida elimina la otra partida existente sin terminar.
- Puntuaciones: Navegará a un listado con el historial de puntuaciones por partida.

### Puntuaciones
Listado con todo el historíal de puntuaciones de todas las partidas completadas.

[Puntuación en los bolos](http://www.fryes4fun.com/Bowling/scoring.htm)

### Partida
Pantalla para introducir y ver las tiradas de una partida. Cuando acabe la partida se ocultará el input y el botón.
