import java.time.LocalDate;

/**
 * Clase asociativa entre Actividad y Estudiante.
 * La inscripción existe porque un estudiante se inscribe a una actividad concreta.
 * Esta relación tiene atributos propios (fecha, estado) que no pertenecen ni a la
 * actividad ni al estudiante: pertenecen al vínculo. Por eso se modela como una
 * clase independiente, y no como una simple lista de estudiantes dentro de Actividad.
 */
public class Inscripcion {
    private Actividad actividad;
    private Estudiante estudiante;
    private LocalDate fecha;
    private String estado;

    public Inscripcion(Actividad actividad, Estudiante estudiante, LocalDate fecha, String estado) {
        this.actividad = actividad;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }
}
