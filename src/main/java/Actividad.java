import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Actividad forma parte de un EventoUniversitario (composición).
 * La lista de inscripciones permite materializar la clase asociativa Inscripcion;
 * esto es una decisión de implementación sobre la navegabilidad de los datos y no
 * forma parte de la definición de la clase asociativa en el modelo.
 *
 * A partir del Ejercicio 3, Actividad pasa a ser una clase abstracta: ya no se
 * instancian actividades genéricas, sino tipos concretos (Charla, Taller) que
 * heredan de ella. El manejo de inscripciones, títulos y cupos -común a cualquier
 * actividad- se mantiene en este único lugar; las subclases solo implementan lo
 * que las diferencia: cómo calculan su costo de materiales y cómo se identifican.
 */
public abstract class Actividad {
    private int id;
    private String titulo;
    private int cupoMaximo;

    private List<Inscripcion> inscripciones;

    /* Variables de clase */
    public static final int CUPO_MINIMO;

    /* Inicializador estático */
    static {
        CUPO_MINIMO = 5;
        System.out.println("Inicializador estático: se cargó la clase Actividad.");
    }

    /* Aunque Actividad es abstracta, provee constructor: las subclases lo activan
    * con super(...), encadenando la inicialización de la parte heredada. */
    public Actividad(int id, String titulo, int cupo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
        this.inscripciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return;
        }
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupo) {
        this.cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
    }

    public Inscripcion inscribir(Estudiante estudiante) {
        Inscripcion inscripcion = new Inscripcion(this, estudiante, LocalDate.now(), "REGISTRADA");
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("  Sin inscripciones registradas.");
            return;
        }
        System.out.println("   Inscripciones registradas:");
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println("   " + inscripcion.getFecha() + " - " + inscripcion.getEstado() + " - " + inscripcion.getEstudiante().getNombre() + " (Legajo: " + inscripcion.getEstudiante().getLegajo() + ")");
        }
    }

    /**
     * Método final: las subclases no pueden redefinir la forma estándar de identificar una actividad.
     */
    public final void mostrarIdentificacion() {
        /* Aquí se evidencia el polimorfismo: cada subclase implementa su propia versión de getTipo().
        * Si este método no estuviese definido en la superclase, no se podría utilizar aquí. */
        System.out.println("- " + getTipo() + ": " + titulo + " (id=" + id + ")" + " - Cupo máximo: " + cupoMaximo);
    }

    /** Método abstracto: cada tipo de actividad calcula su costo de materiales de manera diferente. */
    public abstract double calcularCostoMateriales();

    /** Método abstracto usado para evidenciar polimorfismo en la salida del programa. */
    public abstract String getTipo();
}
