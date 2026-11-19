# Simulador de la Fase de Batalla · Yu-Gi-Oh!

Examen final de Programación Orientada a Objetos (Universidad Autónoma de Occidente).

Aplicación de escritorio en Java que simula la Fase de Batalla del juego de cartas
Yu-Gi-Oh!: los duelistas tienen Puntos de Vida (LP), invocan monstruos en posición
de ataque o defensa, declaran ataques con la tabla oficial de cálculo de daño y
cada ataque queda guardado en `historial_combate.txt`.

## Cómo ejecutarlo

1. Abrir en Apache NetBeans: **File → Open Project** y elegir esta carpeta (es un proyecto Maven).
2. **Run Project (F6)**. La clase principal es `co.edu.uao.yugioh.Main`.
   Se necesita JDK 17 o superior; la primera vez Maven descarga FlatLaf y JUnit.
3. Al iniciar, la consola muestra los dos reportes de `Main` y luego se abre la ventana.
4. Pruebas unitarias: **Test Project (Alt + F6)** ejecuta las 64 pruebas JUnit 5 del paquete `modelo`.

## Uso

| Pestaña | Requerimiento | Qué se hace |
|---|---|---|
| 1 · Duelistas | RF1 | Nombres de los dos duelistas y LP iniciales (8000 por defecto). El botón *Duelo de ejemplo* carga a Yugi y Kaiba con monstruos clásicos. |
| 2 · Monstruos | RF2 | Crear monstruos con nombre, ATQ, DEF, posición (Ataque/Defensa) y tipo (Normal/Efecto) e invocarlos al campo (máximo 5 por duelista). |
| 3 · Batalla | RF3 | Elegir atacante y objetivo; el simulador calcula el daño, reduce los LP y manda al cementerio los monstruos destruidos. También se cambia de posición y se pasa el turno. |
| 4 · Historial | RF4 | Tabla con todos los ataques leídos de `historial_combate.txt`; se puede recargar o vaciar el archivo. |

## Reglas de daño implementadas

| Enfrentamiento | Resultado |
|---|---|
| Ataque vs Ataque | El monstruo con menos ATQ se destruye y su dueño recibe la diferencia. Si empatan se destruyen ambos y nadie recibe daño (si ambos tienen 0 de ATQ no se destruye ninguno). |
| Ataque vs Defensa | Si ATQ > DEF el defensor se destruye y nadie recibe daño; si ATQ < DEF el atacante recibe la diferencia; si son iguales no pasa nada. |
| Ataque directo | Solo si el rival no tiene monstruos: recibe todo el ATQ del atacante. |

Además: solo ataca el duelista en turno, cada monstruo ataca una vez por turno, un
monstruo en defensa no puede atacar, la posición se cambia una vez por turno (no
después de atacar) y los LP nunca bajan de 0; cuando un duelista llega a 0 el duelo termina.

## Arquitectura (MVC)

```
src/main/java/co/edu/uao/yugioh
├── Main.java                  arranque MVC y 2 reportes sobre estructuras dinámicas
├── modelo/                    reglas del juego (sin dependencias de Swing)
│   ├── Carta, Monstruo        clases abstractas
│   ├── MonstruoNormal, MonstruoEfecto
│   ├── Posicion, TipoEnfrentamiento (enum)
│   ├── Jugador, Duelo
│   ├── CalculadoraDanio, ResultadoAtaque
│   ├── RegistroCombate, HistorialCombate (interfaz), ArchivoHistorial
│   ├── CatalogoMonstruos
│   └── ReglaDeBatallaException, PersistenciaException
├── vista/                     VentanaDuelo (JFrame Form: VentanaDuelo.form)
└── controlador/               ControladorDuelo (eventos) y FiltroNumerico
```

- La interfaz se hizo con el diseñador visual de NetBeans (JFrame Form). La vista no tiene
  lógica: el controlador registra los eventos de los botones y actualiza los componentes.
- Los campos numéricos solo aceptan dígitos (`FiltroNumerico`) y los errores se manejan con
  excepciones propias que se muestran en cuadros de diálogo.
- Diagrama de clases: [`docs/diagrama_clases.pdf`](docs/diagrama_clases.pdf) (también en PNG).

## Persistencia

`historial_combate.txt` se crea en la carpeta del proyecto. Cada ataque es una línea con los
campos separados por ` | `:

```
fecha y hora | turno | duelista atacante | monstruo atacante | duelista defensor | monstruo defensor | enfrentamiento | daño | duelista afectado | resultado
```

Al abrir la aplicación el archivo se lee y se muestra en la pestaña Historial; las líneas
dañadas se ignoran. El repositorio incluye un historial de ejemplo generado por la aplicación.

## Control de versiones

El desarrollo se hizo en la rama personal `simonsati`, con commits por funcionalidad
(RF1 a RF4), y se integró a `main` mediante un Pull Request.
