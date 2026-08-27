import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal desde la cual se crean y vinculan los objetos del modelo.
 * La relación de App con EventoUniversitario, Sala y Estudiante es de
 * dependencia (relación de uso): App crea y usa esos objetos, pero no los
 * incorpora como atributos propios.
 */
public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        int id = 1;

        /* Se crean estudiantes */
        List<Estudiante> estudiantes = new ArrayList<>();

        System.out.println("REGISTRO DE ESTUDIANTES: ");
        System.out.println("======================");

        while (continuar) {
            System.out.println("Ingrese legajo del estudiante: ");
            String legajo = scanner.nextLine();
            System.out.println("Ingrese nombre y apellido del estudiante: ");
            String apenomb = scanner.nextLine();
            estudiantes.add(new Estudiante(legajo, apenomb));
            System.out.println("¿Desea crear otro estudiante? S/N");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
        }

        /* Se itera construyendo eventos */
        System.out.println("\n\nREGISTRO DE EVENTOS: ");
        System.out.println("====================");
        continuar = true;
        while (continuar) {
            System.out.println("Ingrese un título para el evento: ");
            String titulo = scanner.nextLine();
            System.out.println("Ingrese el costo base: ");
            double costoBase = scanner.nextDouble();
            scanner.nextLine(); // limpia el Enter pendiente
            System.out.println("¿El evento tendrá costo para los participantes? S/N");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            boolean esGratuito = true;
            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                esGratuito = false;
            }

            EventoUniversitario evento = new EventoUniversitario(
                    "EVT-" + id, titulo, costoBase, esGratuito);

            /* Se utiliza el constructor de copia para construir una copia del evento */
            EventoUniversitario copiaEvento = new EventoUniversitario(evento);

            /* Se crea una sala y se asigna al evento (agregación) */
            System.out.println("Ingrese el nombre de la sala donde se realizará el evento: ");
            String nombreSala = scanner.nextLine();
            Sala sala = new Sala(id, nombreSala);
            evento.asignarSala(sala);

            /* Se crean las actividades del evento, de forma polimórfica (charla o taller) */
            System.out.println("\n\nREGISTRO DE ACTIVIDADES PARA EL EVENTO " + evento.getTitulo());
            System.out.println("================================================================");
            int idActividad = 1;
            while (continuar) {
                System.out.println("Ingrese el título de la actividad: ");
                String tituloActividad = scanner.nextLine();
                System.out.println("Ingrese el cupo máximo de estudiantes admitidos para la actividad: ");
                int cupo = scanner.nextInt();
                scanner.nextLine(); // se consume la línea
                System.out.println("Tipo de actividad (charla/taller): ");
                String tipoActividad = scanner.nextLine().trim().toLowerCase();

                evento.crearActividad(idActividad, tituloActividad, cupo, tipoActividad);

                System.out.println("¿Desea crear otra actividad para el evento " + evento.getTitulo() + "? S/N");
                respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
                ++idActividad;
            }

            /* Se inscriben estudiantes en actividades */
            System.out.println("\n\nINSCRIPCION DE ESTUDIANTES EN ACTIVIDADES DEL EVENTO " + evento.getTitulo());
            System.out.println("===============================================================================");
            continuar = true;
            while (continuar) {
                System.out.println("Ingrese legajo del estudiante a inscribir: ");
                String legajo = scanner.nextLine();
                System.out.println("Ingrese id de la Actividad: ");
                idActividad = scanner.nextInt();
                scanner.nextLine(); // se consume línea
                for (Estudiante estudiante : estudiantes) {
                    if (estudiante.getLegajo().equals(legajo)) {
                        evento.getActividades().get(idActividad - 1).inscribir(estudiante);
                    }
                }
                System.out.println("¿Desea generar otra inscripción? S/N");
                respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
            }

            /* Se muestran los datos del evento y de su copia */
            evento.mostrarDatos();
            copiaEvento.mostrarDatos();

            System.out.println("\n\n¿Desea crear otro evento? S/N");
            respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
            ++id;
        }

        /* Como el constructor de copia también incrementa cantidadEventos, el contador
        * refleja la totalidad de objetos construidos, originales y copias. */
        System.out.println("\n\nEventos creados: " + EventoUniversitario.getCantidadEventos());
    }
}
