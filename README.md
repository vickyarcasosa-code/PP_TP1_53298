# TP1_Ejercicio3_Eventos

Trabajo Práctico 1 - Programación Orientada a Objetos en Java
Paradigmas de Programación - UTN FRM

Sistema de Gestión de Eventos Universitarios. Versión final y acumulativa
del TP1: incluye lo pedido en los Ejercicios 1, 2 y 3 del enunciado.

## Estructura del proyecto
TP1_Ejercicio3_Eventos/
├── pom.xml
├── .gitignore
├── captura-consola.png
└── src/
└── main/
└── java/
├── App.java
├── EventoUniversitario.java
├── Sala.java
├── Actividad.java
├── Charla.java
├── Taller.java
├── Estudiante.java
└── Inscripcion.java

## Conceptos aplicados y dónde están

| Concepto | Dónde se ve en el código |
|---|---|
| Encapsulamiento | Atributos `private` + getters/setters con validación (`setTitulo`, `setNombre`) |
| Constructores y sobrecarga | `EventoUniversitario(id, ...)` y `EventoUniversitario(otroEvento)`, delegado con `this(...)` |
| Miembros estáticos y bloque estático | `cantidadEventos`, `getCantidadEventos()`, bloques `static { }` en `EventoUniversitario` y `Actividad` |
| Atributo final | `Id` en `EventoUniversitario` (una vez asignado, no cambia) |
| Constante de clase | `CUPO_MINIMO` (`static final`) en `Actividad` |
| Dependencia | `App` crea y usa `EventoUniversitario`, `Sala`, `Estudiante`, pero no los retiene como atributos propios |
| Agregación | `asignarSala(Sala)`: la sala se crea afuera del evento y sobrevive a su destrucción |
| Composición | `crearActividad(...)`: la actividad se crea adentro del evento y muere con él; `getActividades()` devuelve una lista inmodificable |
| Clase asociativa | `Inscripcion`, con fecha y estado propios del vínculo actividad-estudiante |
| Colecciones | `List<Actividad>` y `List<Inscripcion>`, instanciadas como `ArrayList<>` |
| Herencia y clase abstracta | `Actividad` es `abstract`; `Charla` y `Taller` heredan con `extends` y `super(...)` |
| Polimorfismo / ligado dinámico | `List<Actividad>` almacena objetos `Charla`/`Taller`; `calcularCostoMateriales()` y `getTipo()` se resuelven según el tipo real del objeto en tiempo de ejecución |
| Método final | `mostrarIdentificacion()` en `Actividad`: no puede redefinirse en las subclases |

## Decisiones de diseño

- **Constructor de copia:** `EventoUniversitario(EventoUniversitario otroEvento)` delega en el constructor principal con `this(...)` y le agrega el sufijo `-COPIA` al id, porque es un duplicado del mismo evento, no uno nuevo. También incrementa `cantidadEventos`, por lo que el contador final refleja originales y copias.
- **`crearActividad(id, titulo, cupo, tipoActividad)`:** recibe el tipo como `String` (`"charla"` o `"taller"`) y decide con un `switch` qué subclase instanciar. Es el único punto del sistema que conoce los tipos concretos: `calcularCostoEstimado()` y `mostrarDatos()` siguen trabajando exclusivamente contra el tipo `Actividad`, sin cambios, gracias al polimorfismo.
- **`getTipo()`:** en `Charla` y `Taller` se implementa con `this.getClass().getSimpleName()`, que consulta en tiempo de ejecución la clase real del objeto en vez de escribir el nombre a mano.
- **Regla de costos:** si el evento es gratuito, el costo total es 0. Si no, es `(costoBase + costo de materiales de cada actividad) * 1.21` (21% de impuestos). Las charlas no tienen costo de materiales; los talleres cuestan $5000 si requieren notebook y $2000 si no.
- **`CUPO_MINIMO`:** constante de clase (`static final int`, valor 5) inicializada en un bloque estático. Si el cupo ingresado es menor al mínimo, se usa el mínimo.

## Cómo ejecutar

1. Abrir la carpeta del proyecto en IntelliJ IDEA (`File → Open`, seleccionar la carpeta con el `pom.xml`).
2. Esperar a que Maven sincronice las dependencias.
3. Abrir `App.java` y ejecutar `App.main()`.
4. Completar los datos que va pidiendo la consola (estudiantes, evento, sala, actividades, inscripciones).