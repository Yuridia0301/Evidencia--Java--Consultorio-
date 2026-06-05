import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Doctor> listaDoctores = new ArrayList<>();
    private static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    private static ArrayList<Cita> listaCitas = new ArrayList<>();

    private static Scanner teclado = new Scanner(System.in);

    // RUTAS EXIGIDAS: Guardado obligatorio dentro de la carpeta 'db'
    private static final String CARPETA_DB = "db";
    private static final String ARCHIVO_DOCTORES = CARPETA_DB + File.separator + "doctores.csv";
    private static final String ARCHIVO_PACIENTES = CARPETA_DB + File.separator + "pacientes.csv";

    public static void main(String[] args) {
        // VERIFICACIÓN OBLIGATORIA: Asegurar que la carpeta 'db' exista antes de que el programa intente leer o escribir
        File carpeta = new File(CARPETA_DB);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crea la carpeta 'db' automáticamente si no existe
        }

        // Cargar los datos guardados en la carpeta db inmediatamente al abrir el programa
        cargarDatosDeArchivos();

        System.out.println("=== SISTEMA DE ADMINISTRACIÓN CLÍNICA ===");

        boolean accesoConcedido = false;
        while (!accesoConcedido) {
            System.out.print("\nIntroduce el usuario administrador: ");
            String usuario = teclado.nextLine();

            System.out.print("Introduce la contraseña: ");
            String password = teclado.nextLine();

            if (usuario.equals("admin") && password.equals("12345")) {
                System.out.println("\n¡Login correcto! Bienvenido al sistema.");
                accesoConcedido = true;
            } else {
                System.out.println("Error: Credenciales incorrectas. Intenta de nuevo.");
            }
        }

        int opcion = 0;
        do {
            try {
                mostrarMenu();
                System.out.print("Selecciona una opción (1-4): ");
                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1:
                        registrarMedico();
                        break;
                    case 2:
                        registrarPaciente();
                        break;
                    case 3:
                        crearCita();
                        break;
                    case 4:
                        System.out.println("\nCerrando el sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Debe ser un número entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingresa un número válido.");
            }
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("\n------------------------------------");
        System.out.println("           MENÚ PRINCIPAL           ");
        System.out.println("------------------------------------");
        System.out.println("1. Registrar Médico");
        System.out.println("2. Registrar Paciente");
        System.out.println("3. Crear Cita Médica");
        System.out.println("4. Salir del programa");
        System.out.println("------------------------------------");
    }

    private static void registrarMedico() {
        System.out.println("\n--- REGISTRAR NUEVO MÉDICO ---");
        System.out.print("Introduce el ID del médico (ej. DOC01): ");
        String id = teclado.nextLine();

        System.out.print("Introduce el nombre completo del médico: ");
        String nombre = teclado.nextLine();

        System.out.print("Introduce la especialidad médica: ");
        String especialidad = teclado.nextLine();

        Doctor nuevoDoctor = new Doctor(id, nombre, especialidad);
        listaDoctores.add(nuevoDoctor);

        // Guarda el cambio al instante en db/doctores.csv
        guardarDoctoresEnArchivo();

        System.out.println("\n¡Médico registrado y guardado con éxito en la carpeta db!");
        nuevoDoctor.mostrarDatos();
    }

    private static void registrarPaciente() {
        System.out.println("\n--- REGISTRAR NUEVO PACIENTE ---");
        System.out.print("Introduce el ID del paciente (ej. PAC01): ");
        String id = teclado.nextLine();

        System.out.print("Introduce el nombre completo del paciente: ");
        String nombre = teclado.nextLine();

        System.out.print("Introduce el motivo de historial médico: ");
        String historial = teclado.nextLine();

        Paciente nuevoPaciente = new Paciente(id, nombre, historial);
        listaPacientes.add(nuevoPaciente);

        // Guarda el cambio al instante en db/pacientes.csv
        guardarPacientesEnArchivo();

        System.out.println("\n¡Paciente registrado y guardado con éxito en la carpeta db!");
        nuevoPaciente.mostrarDatos();
    }

    private static void crearCita() {
        System.out.println("\n--- CREAR CITA MÉDICA ---");

        if (listaDoctores.isEmpty() || listaPacientes.isEmpty()) {
            System.out.println("Error: Primero debes registrar al menos un Médico y un Paciente.");
            return;
        }

        System.out.print("Introduce el ID de la cita (ej. CIT01): ");
        String idCita = teclado.nextLine();

        System.out.print("Introduce la fecha y hora (ej. 25/May 4:00 PM): ");
        String fechaHora = teclado.nextLine();

        System.out.print("Introduce el motivo de la consulta: ");
        String motivo = teclado.nextLine();

        // SELECCIÓN DE MÉDICO PROTEGIDA
        int indiceDoc = -1;
        while (indiceDoc < 0 || indiceDoc >= listaDoctores.size()) {
            try {
                System.out.println("\n--- Médicos Disponibles ---");
                for (int i = 0; i < listaDoctores.size(); i++) {
                    System.out.print("[" + i + "] ");
                    listaDoctores.get(i).mostrarDatos();
                }
                System.out.print("Selecciona el número de índice del médico asignado: ");
                indiceDoc = Integer.parseInt(teclado.nextLine().trim());

                if (indiceDoc < 0 || indiceDoc >= listaDoctores.size()) {
                    System.out.println("Error: El número de índice no existe.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce solo el número del corchete (ej. 0).");
                indiceDoc = -1;
            }
        }

        // SELECCIÓN DE PACIENTE PROTEGIDA
        int indicePac = -1;
        while (indicePac < 0 || indicePac >= listaPacientes.size()) {
            try {
                System.out.println("\n--- Pacientes Disponibles ---");
                for (int i = 0; i < listaPacientes.size(); i++) {
                    System.out.print("[" + i + "] ");
                    listaPacientes.get(i).mostrarDatos();
                }
                System.out.print("Selecciona el número de índice del paciente asignado: ");
                indicePac = Integer.parseInt(teclado.nextLine().trim());

                if (indicePac < 0 || indicePac >= listaPacientes.size()) {
                    System.out.println("Error: El número de índice no existe.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce solo el número del corchete (ej. 0).");
                indicePac = -1;
            }
        }

        Doctor docAsignado = listaDoctores.get(indiceDoc);
        Paciente pacAsignado = listaPacientes.get(indicePac);

        Cita nuevaCita = new Cita(idCita, fechaHora, motivo, docAsignado, pacAsignado);
        listaCitas.add(nuevaCita);

        System.out.println("\n--- RESUMEN DE LA CITA GENERADA ---");
        nuevaCita.agendarCita();
        System.out.println("Médico: " + nuevaCita.getDoctorAsignado().getNombreCompleto());
        System.out.println("Paciente: " + nuevaCita.getPacienteAsignado().getNombreCompleto());
    }

    // -----------------------------------------------------------------
    // MÉTODOS DE PERSISTENCIA (GUARDADO Y CARGA EN CARPETA DB)
    // -----------------------------------------------------------------

    private static void guardarDoctoresEnArchivo() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_DOCTORES))) {
            for (Doctor d : listaDoctores) {
                escritor.println(d.getId() + "," + d.getNombreCompleto() + "," + d.getEspecialidad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar médicos: " + e.getMessage());
        }
    }

    private static void guardarPacientesEnArchivo() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_PACIENTES))) {
            for (Paciente p : listaPacientes) {
                escritor.println(p.getId() + "," + p.getNombreCompleto() + "," + p.getHistorialMedico());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    private static void cargarDatosDeArchivos() {
        File archivoDoc = new File(ARCHIVO_DOCTORES);
        if (archivoDoc.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivoDoc))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length == 3) {
                        listaDoctores.add(new Doctor(datos[0], datos[1], datos[2]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar médicos: " + e.getMessage());
            }
        }

        File archivoPac = new File(ARCHIVO_PACIENTES);
        if (archivoPac.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivoPac))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length == 3) {
                        listaPacientes.add(new Paciente(datos[0], datos[1], datos[2]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar pacientes: " + e.getMessage());
            }
        }
    }
}